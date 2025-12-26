//
// Created by lilconx on 6/26/25.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <getopt.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <fcntl.h>
#include "parse.h"
#include "workqueue.h"
#include "helpers.h"
#include <pthread.h>
#include <poll.h>
#include <sys/wait.h>
#include <arpa/inet.h>
#include <errno.h>


#define BUFFER_SIZE 8192

char *root_dir = NULL;
char *cgi_program = NULL;
int timeout_secs = 5; // default
int port = 0; // default
pthread_mutex_t parse_mutex = PTHREAD_MUTEX_INITIALIZER;

void handle_cgi_request(int client_fd, Request *req, struct sockaddr_in *client_addr) {
    if (strcmp(req->http_method, "GET") != 0 && strcmp(req->http_method, "POST") != 0 && strcmp(req->http_method, "HEAD") != 0) {
        send_error(client_fd, 501, "Not Implemented");
        return;
    }

    int stdin_pipe[2], stdout_pipe[2];
    if (pipe(stdin_pipe) == -1 || pipe(stdout_pipe) == -1) {
        send_error(client_fd, 500, "Internal Server Error");
        return;
    }

    pid_t pid = fork();
    if (pid < 0) {
        send_error(client_fd, 500, "Internal Server Error");
        return;
    }

    if (pid == 0) { // Child process

        dup2(stdin_pipe[0], STDIN_FILENO);
        dup2(stdout_pipe[1], STDOUT_FILENO);
        close(stdin_pipe[1]);
        close(stdout_pipe[0]);

        char script_name[] = "/cgi-demo/"; // Gonna use cgi-demo from repo
        char *path_info = req->http_uri + strlen(script_name);
        char *query_string = strchr(path_info, '?');
        if (query_string) {
            *query_string = '\0';
            query_string++;
        }

        char port_str[10];
        snprintf(port_str, sizeof(port_str), "%d", port);

        setenv("GATEWAY_INTERFACE", "CGI/1.1", 1);
        setenv("PATH_INFO", path_info, 1);
        setenv("QUERY_STRING", query_string ? query_string : "", 1);
        setenv("REQUEST_METHOD", req->http_method, 1);
        setenv("REQUEST_URI", req->http_uri, 1);
        setenv("SCRIPT_NAME", script_name, 1);
        setenv("SERVER_PORT", port_str, 1);
        setenv("SERVER_PROTOCOL", req->http_version, 1);
        setenv("SERVER_SOFTWARE", "ICWS/1.0", 1);

        char remote_addr[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, &(client_addr->sin_addr), remote_addr, INET_ADDRSTRLEN);
        setenv("REMOTE_ADDR", remote_addr, 1);

        // Parsing HTTP headers
        for (int i = 0; i < req->header_count; i++) {
            char *key = req->headers[i].header_name;
            char *val = req->headers[i].header_value;
            char env_key[128];

            snprintf(env_key, sizeof(env_key), "HTTP_%s", key);
            for (int j = 0; env_key[j]; j++) {
                if (env_key[j] == '-') env_key[j] = '_';
            }

            setenv(env_key, val, 1);

            if (strcasecmp(key, "Content-Type") == 0) {
                setenv("CONTENT_TYPE", val, 1);
            } else if (strcasecmp(key, "Content-Length") == 0) {
                setenv("CONTENT_LENGTH", val, 1);
            }
        }

        execl(cgi_program, cgi_program, NULL);
        exit(1);
    } else { // Parent process
        close(stdin_pipe[0]);
        close(stdout_pipe[1]);

        // If there is a body in POST, get + send to stdin
        if (strcmp(req->http_method, "POST") == 0) {
            const char *cl = get_header(req, "Content-Length");
            if (cl) {
                int content_length = atoi(cl);
                if (content_length > 0 && content_length < BUFFER_SIZE) {
                    char post_buf[BUFFER_SIZE];
                    int total_read = 0;

                    while (total_read < content_length) {
                        ssize_t r = read(client_fd, post_buf + total_read, content_length - total_read);
                        if (r <= 0) break;
                        total_read += r;
                    }

                    write(stdin_pipe[1], post_buf, total_read);
                }
            }
        }
        close(stdin_pipe[1]);

        char buf[BUFFER_SIZE];
        ssize_t bytes;
        while ((bytes = read(stdout_pipe[0], buf, sizeof(buf))) > 0) {
            write(client_fd, buf, bytes);
        }
        close(stdout_pipe[0]);

        waitpid(pid, NULL, 0);
    }

    free(req->headers);
    free(req);
}

void handle_connection(int client_fd, struct sockaddr_in *client_addr) {
    const int MAX_HEADER_SIZE = BUFFER_SIZE;
    char buffer[BUFFER_SIZE];
    int buffer_len = 0;
    int keep_alive = 1; // change for persistent connections

    struct pollfd pfd = {
        .fd = client_fd,
        .events = POLLIN
    };

    while (keep_alive) {

        buffer_len = 0;
        Request *req = NULL;

        // Reading + parsing headers w/ pipelining and partial reads in mind
        int headers_parsed = 0;
        while (!headers_parsed) {
            int poll_result = poll(&pfd, 1, timeout_secs * 1000);
            if (poll_result == 0) {
                send_error(client_fd, 408, "Request Timeout");
                return;
            } else if (poll_result < 0) {
                send_error(client_fd, 500, "Internal Server Error");
                return;
            }

            ssize_t n = read(client_fd, buffer + buffer_len, BUFFER_SIZE - buffer_len);
            if (n < 0) {
                send_error(client_fd, 500, "Internal Server Error");
                return;
            } else if (n == 0) {
                return;
            }
            buffer_len += n;

            // Rejection if header is too big
            if (buffer_len > MAX_HEADER_SIZE) {
                dprintf(client_fd, "HTTP/1.1 400 Bad Request\r\nConnection: keep-alive\r\n\r\n");
                return;
            }

            // Check for if we have a full header
            if (buffer_len >= 4) {
                for (int i = 0; i <= buffer_len - 4; i++) {
                    if (buffer[i] == '\r' && buffer[i+1] == '\n' &&
                        buffer[i+2] == '\r' && buffer[i+3] == '\n') {

                        int header_end = i + 4;

                        // mutex around parse call so one thread enters at a time
                        pthread_mutex_lock(&parse_mutex);
                        req = parse(buffer, header_end, client_fd);
                        pthread_mutex_unlock(&parse_mutex);

                        if (!req) {
                            send_error(client_fd, 400, "Bad Request");
                            return;
                        }

                        headers_parsed = 1;

                        if (strncmp(req->http_version, "HTTP/1.1", 8) != 0) {
                            send_error(client_fd, 505, "HTTP Version Not Supported");
                            free(req->headers);
                            free(req);
                            return;
                        }

                        if (strcmp(req->http_method, "POST") == 0) {
                            const char *cl = get_header(req, "Content-Length");
                            if (!cl) {
                                send_error(client_fd, 411, "Length Required");
                                free(req->headers);
                                free(req);
                                return;
                            }
                        }

                        // Reading POST body if applicable
                        if (strcmp(req->http_method, "POST") == 0) {
                            const char *cl = get_header(req, "Content-Length");
                            int content_length = atoi(cl);

                            int body_read = buffer_len - header_end;
                            char *body_buffer = malloc(content_length);
                            if (!body_buffer) {
                                send_error(client_fd, 500, "Internal Server Error");
                                free(req->headers);
                                free(req);
                                return;
                            }

                            if (body_read > 0) {
                                memcpy(body_buffer, buffer + header_end, body_read);
                            }

                            while (body_read < content_length) {
                                ssize_t r = read(client_fd, body_buffer + body_read, content_length - body_read);
                                if (r <= 0) break;
                                body_read += r;
                            }
                            free(body_buffer);
                        }

                        break;
                    }
                }
            }
        }

        // Do we still want to keep alive?
        const char *conn_hdr = get_header(req, "Connection");
        if (conn_hdr && strcasecmp(conn_hdr, "close") == 0) {
            keep_alive = 0;
        } else {
            keep_alive = 1;
        }

        // CGI request handling part
        if (strncmp(req->http_uri, "/cgi-demo/", 10) == 0) {
            handle_cgi_request(client_fd, req, client_addr);
        } else {
            if (strcmp(req->http_method, "GET") != 0 && strcmp(req->http_method, "HEAD") != 0 && strcmp(req->http_method, "POST") != 0) {
                send_error(client_fd, 501, "Not Implemented");
                free(req->headers);
                free(req);
                if (!keep_alive) return;
                else continue;
            }

            // Security check for path traversal
            char path[4096];
            snprintf(path, sizeof(path), "%s%s", root_dir, req->http_uri);
            if (strstr(path, "..")) {
                send_error(client_fd, 403, "Forbidden");
                free(req->headers);
                free(req);
                if (!keep_alive) return;
                else continue;
            }

            FILE *fp = fopen(path, "rb");
            if (!fp) {
                send_error(client_fd, 404, "Not Found");
                free(req->headers);
                free(req);
                if (!keep_alive) return;
                else continue;
            }

            fseek(fp, 0, SEEK_END);
            long size = ftell(fp);
            fseek(fp, 0, SEEK_SET);

            // Make headers
            char date[128], last_modified[128];
            get_date_header(date, sizeof(date));
            if (get_last_modified(path, last_modified, sizeof(last_modified)) < 0) {
                last_modified[0] = '\0';
            }

            dprintf(client_fd, "HTTP/1.1 200 OK\r\n");
            dprintf(client_fd, "Date: %s\r\n", date);
            dprintf(client_fd, "Server: ICWS/1.0\r\n");
            dprintf(client_fd, "Content-Length: %ld\r\n", size);
            dprintf(client_fd, "Content-Type: %s\r\n", get_mime_type(path));
            if (last_modified[0]) {
                dprintf(client_fd, "Last-Modified: %s\r\n", last_modified);
            }
            if (keep_alive) {
                dprintf(client_fd, "Connection: keep-alive\r\n");
            } else {
                dprintf(client_fd, "Connection: close\r\n");
            }
            dprintf(client_fd, "\r\n");

            // Send body for GET only
            if (strcmp(req->http_method, "GET") == 0) {
                char buf[1024];
                size_t bytes;
                while ((bytes = fread(buf, 1, sizeof(buf), fp)) > 0) {
                    write(client_fd, buf, bytes);
                }
            }

            fclose(fp);
            free(req->headers);
            free(req);
        }

        if (!keep_alive) {
            break;
        }
    }
}


void *worker_thread(void *arg) {
    while (1) {
        client_connection *conn = workqueue_pop();
        handle_connection(conn->fd, &conn->addr);
        close(conn->fd);
        free(conn);
    }
    return NULL;
}

int main(int argc, char **argv) {
    int num_threads = 4;

    struct option opts[] = {
        {"port", required_argument, NULL, 'p'},
        {"root", required_argument, NULL, 'r'},
        {"numThreads", required_argument, NULL, 'n'},
        {"timeout", required_argument, NULL, 't'},
        {"cgiHandler", required_argument, NULL, 'c'},
        {0, 0, 0, 0}
    };

    int opt;
    while ((opt = getopt_long(argc, argv, "p:r:", opts, NULL)) != -1) {
        switch (opt) {
            case 'p': port = atoi(optarg); break;
            case 'r': root_dir = strdup(optarg); break;
            case 'n': num_threads = atoi(optarg); break;
            case 't': timeout_secs = atoi(optarg); break;
            case 'c': cgi_program = strdup(optarg); break;
            default:
                fprintf(stderr, "Invalid arguments.\n", argv[0]);
                exit(1);
        }
    }

    if (!port || !root_dir || !num_threads || !timeout_secs || !cgi_program) {
        fprintf(stderr, "Not enough arguments.\n");
        exit(1);
    }

    int server_fd = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in addr = {0};
    addr.sin_family = AF_INET;
    addr.sin_port = htons(port);
    addr.sin_addr.s_addr = INADDR_ANY;

    // keeping for debuggin
    printf("Port: %d\n", port);
    printf("Root dir: %s\n", root_dir);
    printf("Number of threads: %d\n", num_threads);
    printf("Timeout: %d seconds\n", timeout_secs);
    printf("CGI dir: %s\n", cgi_program);

    bind(server_fd, (struct sockaddr*)&addr, sizeof(addr));
    listen(server_fd, 10);

    workqueue_init(500); // m2

    pthread_t *threads = malloc(sizeof(pthread_t) * num_threads);
    for (int i = 0; i < num_threads; i++) {
        pthread_create(&threads[i], NULL, worker_thread, NULL);
    }

    while (1) {
        struct sockaddr_in client_addr;
        socklen_t client_len = sizeof(client_addr);
        int client_fd = accept(server_fd, (struct sockaddr*)&client_addr, &client_len);

        if (client_fd >= 0) {
            client_connection *conn = malloc(sizeof(client_connection));
            conn->fd = client_fd;
            conn->addr = client_addr;
            workqueue_push(conn);
        }
    }

    // just in case, but it will probably never be reached
    free(threads);
    free(root_dir);
    return 0;

}

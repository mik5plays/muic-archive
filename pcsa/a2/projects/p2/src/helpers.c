#include <time.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include "parse.h"

const char *get_header(Request *req, const char *key) {
    for (int i = 0; i < req->header_count; i++) {
        if (strcasecmp(req->headers[i].header_name, key) == 0) {
            return req->headers[i].header_value;
        }
    }
    return NULL;
}

void get_date_header(char *buf, size_t size) {
    time_t now = time(NULL);
    struct tm tm;
    gmtime_r(&now, &tm);
    strftime(buf, size, "%a, %d %b %Y %H:%M:%S GMT", &tm);
}

const char *get_mime_type(const char *path) {
    const char *ext = strrchr(path, '.');
    if (!ext) return "application/octet-stream";

    if (strcasecmp(ext, ".html") == 0 || strcasecmp(ext, ".htm") == 0) return "text/html";
    if (strcasecmp(ext, ".css") == 0) return "text/css";
    if (strcasecmp(ext, ".js") == 0) return "text/javascript";
    if (strcasecmp(ext, ".txt") == 0) return "text/plain";
    if (strcasecmp(ext, ".jpg") == 0 || strcasecmp(ext, ".jpeg") == 0) return "image/jpeg";
    if (strcasecmp(ext, ".png") == 0) return "image/png";
    if (strcasecmp(ext, ".gif") == 0) return "image/gif";

    return "application/octet-stream";
}

int get_last_modified(const char *path, char *buf, size_t size) {
    struct stat st;
    if (stat(path, &st) == -1) return -1;

    struct tm tm;
    gmtime_r(&st.st_mtime, &tm);
    strftime(buf, size, "%a, %d %b %Y %H:%M:%S GMT", &tm);
    return 0;
}

void send_error(int fd, int code, const char *msg) {
    char date[128];
    get_date_header(date, sizeof(date));

    dprintf(fd, "HTTP/1.1 %d %s\r\n", code, msg);
    dprintf(fd, "Date: %s\r\n", date);
    dprintf(fd, "Server: ICWS/1.0\r\n");
    dprintf(fd, "Content-Type: text/html\r\n");
    dprintf(fd, "Connection: close\r\n\r\n");
    dprintf(fd, "<html><body><h1>%d %s</h1></body></html>\r\n", code, msg);
}

#ifndef WORKQUEUE_H
#define WORKQUEUE_H
#include <netinet/in.h>

typedef struct {
    int fd;
    struct sockaddr_in addr;
} client_connection;

void workqueue_init(int capacity);
void workqueue_push(client_connection *conn);
client_connection* workqueue_pop();

#endif

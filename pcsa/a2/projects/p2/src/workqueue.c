#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "workqueue.h"

#include <unistd.h>

typedef struct {
    client_connection **connections;
    int capacity;
    int front;
    int rear;
    int count;

    pthread_mutex_t lock;
    pthread_cond_t not_empty;
} WorkQueue;

static WorkQueue queue;

void workqueue_init(int capacity) {
    queue.connections = malloc(sizeof(client_connection*) * capacity);
    queue.capacity = capacity;
    queue.front = 0;
    queue.rear = 0;
    queue.count = 0;

    pthread_mutex_init(&queue.lock, NULL);
    pthread_cond_init(&queue.not_empty, NULL);
}

void workqueue_push(client_connection *conn) {
    pthread_mutex_lock(&queue.lock);

    if (queue.count == queue.capacity) {
        fprintf(stderr, "Queue is full.\n");
        close(conn->fd);
        free(conn);
    } else {
        queue.connections[queue.rear] = conn;
        queue.rear = (queue.rear + 1) % queue.capacity;
        queue.count++;

        pthread_cond_signal(&queue.not_empty);
    }

    pthread_mutex_unlock(&queue.lock);
}


client_connection* workqueue_pop() {
    pthread_mutex_lock(&queue.lock);

    while (queue.count == 0) {
        pthread_cond_wait(&queue.not_empty, &queue.lock);
    }

    client_connection *conn = queue.connections[queue.front];
    queue.front = (queue.front + 1) % queue.capacity;
    queue.count--;

    pthread_mutex_unlock(&queue.lock);
    return conn;
}


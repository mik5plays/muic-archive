#ifndef HTTP_HELPERS_H
#define HTTP_HELPERS_H

#include <stddef.h>

const char *get_header(Request *req, const char *key);
void get_date_header(char *buf, size_t size);
const char *get_mime_type(const char *path);
int get_last_modified(const char *path, char *buf, size_t size);
void send_error(int fd, int code, const char *msg);

#endif // HTTP_HELPERS_H

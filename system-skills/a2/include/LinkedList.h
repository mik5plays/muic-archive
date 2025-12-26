//
// Created by lilconx on 3/4/25.
//

#ifndef LINKEDLIST_H
#define LINKEDLIST_H

typedef struct { // define the structure for Node
    char* transaction;
    struct Node* next;
    int mark;
} Node;

struct Node* create(char* value); // create a new node via memory allocation
void insertFirst(struct Node** head, char* value, int mark); // insert at first position
void insert(struct Node** head, char* value, int mark, int position); // insert a node at some index
void insertLast(struct Node** head, char* value, int mark); // insert at last position

void deleteFirst(struct Node** head); // delete the first node
void delete(struct Node** head, int position); // delete a node at some index
void deleteLast(struct Node** head); // delete the last node

#endif //LINKEDLIST_H

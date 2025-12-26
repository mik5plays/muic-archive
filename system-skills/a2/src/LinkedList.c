//
// Created by lilconx on 3/4/25.
//

#include "LinkedList.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct Node {
  char* transaction;
  struct Node* next;
  int mark;
};

/*
Implementing every function inside the LinkedList.h header. Everything is already explained there, so
I won't repeat myself (except for some cases).
 */

struct Node* create(char* value) {
  struct Node* newNode = malloc(sizeof(struct Node));
  newNode->transaction = strdup(value);
  newNode->next = NULL;
  newNode->mark = 0; // do nothing with it yet
  return newNode;
}

void insertFirst(struct Node** head, char* value, int mark) {
  struct Node* newNode = create(value);
  newNode->next = *head;
  newNode->mark = mark;
  *head = newNode;
}

void insert(struct Node** head, char* value, int mark, int position) {
  struct Node* newNode = create(value);
  newNode->mark = mark;

  if (position == 0) {
    insertFirst(head, value, mark);
    return;
  }

  struct Node* temp = *head;
  int current = 0;
  while (temp != NULL && current < position - 1) { temp = temp->next; current++; }

  if (temp == NULL) {
    printf("Invalid range. Try again.\n");
    return;
  }

  newNode->next = temp->next;
  temp->next = newNode;
}

void insertLast(struct Node** head, char* value, int mark) {
  struct Node* newNode = create(value);
  newNode->mark = mark;

  if (*head == NULL) { // if list is NULL or empty,
    *head = newNode;
    return;
  }

  struct Node* temp = *head;

  while (temp->next != NULL) {
    temp = temp->next;
  }
  temp->next = newNode;
}

void deleteFirst(struct Node** head) {
  if (*head == NULL) { // Check if the list is empty
    printf("List is empty, nothing to delete.\n");
    return;
  }

  struct Node* temp = *head;
  *head = temp->next;
  free(temp);
}

void delete(struct Node** head, int position) {
  if (*head == NULL) { // error handling for if the linked list is invalid
    printf("Invalid linked list.\n");
    return;
  }
  struct Node* temp = *head;

  if (position == 0) {
    *head = temp->next;
    free(temp);
    return;
  }

  int current = 0;
  while (temp != NULL && current < position - 1) { // traverse to the given position
    temp = temp->next;
    current++;
  }
  if (temp == NULL || temp->next == NULL) {
    printf("Unable to delete node. Position may be invalid\n"); // error message for index error
    return;
  }

  struct Node* next = temp->next->next; // remove the node at position + point previous node to next node
  free(temp->next);
  temp->next = next;
}

void deleteLast(struct Node** head) {
  struct Node* temp = *head;
  if (temp->next == NULL) { // the only element in the linked list
    free(temp);
    *head = NULL;
    return;
  }
  while (temp->next->next != NULL) {
    temp = temp->next;
  }
  free(temp->next);
  temp->next = NULL;
}
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

void getTotal(struct Node** transactions, int LIST_TRANSACTIONS) {
  if (*transactions == NULL) { return; } // error handling
  struct Node* current = *transactions;
  float TOTAL = 0;
  int CURRENT = 0;

  if (LIST_TRANSACTIONS == 1) {
    printf("\n[ Transactions ]\n");
  }

  while (current != NULL) {
	char* temp = strdup(current->transaction);

    char* token = strtok(temp, "|"); // split value entry into three parts based on the delimiter '|'
    char EXP_OR_INC[100];
	strcpy(EXP_OR_INC, token);

	token = strtok(NULL, "|");
	char NAME[256];
	strcpy(NAME, token);

	token = strtok(NULL, "|");
	float AMOUNT = atof(token);

	TOTAL += AMOUNT; // add to total costs

	if (LIST_TRANSACTIONS == 1) { // special case to list all the transactions in print
          printf("%-20d", CURRENT);
          printf("%-20s ", NAME);
          printf("%-20.2f ", AMOUNT);
          if (current->mark == -1) {
            printf("%s", "--- d\n");
          } else if (current->mark == 0) {
            printf("%s", "(saved)\n");
          } else if (current->mark == 1) {
            printf("%s", "(new)\n");
          } else if (current->mark == 2) {
            printf("%s", "+++ i\n");
          }
	}

	current = current->next;
	CURRENT++;

  }

  printf("Current Balance: $%.2f\n", TOTAL); // round to two decimal places

  if (TOTAL > 0) {
    printf("Budget Status: Within Budget\n");
  } else {
    printf("Budget Status: Over Budget !\n");
  }

}

void add_transaction(struct Node** transactions, char* INC_OR_EXP) {
  	printf("Enter %s description: ", INC_OR_EXP);
	char DESCRIPTION[50]; // buffer of 50 characters, that we should not exceed

	if (fgets(DESCRIPTION, sizeof(DESCRIPTION), stdin) != NULL) { // do some error handling
    	ssize_t len = strlen(DESCRIPTION);
    	if (len > 0 && DESCRIPTION[len - 1] == '\n') {
        	DESCRIPTION[len - 1] = '\0';  // remove newline, if exists
    	}
	}

	printf("Enter position of %s. Enter -1 to append to the last position\nNote that 0 is the first position for this: ", INC_OR_EXP);
	int position;
	if (fscanf(stdin, "%d", &position) != 1) {
		printf("Invalid input. Please try again.\n");
		return;
	}

	while (getchar() != '\n'); // clear input buffer for scanf type stuff

	printf("Enter amount of %s: ", INC_OR_EXP);
	char AMOUNT_AS_STR[50];
	char* STR_PTR; // pointer for error handling.

	if (fgets(AMOUNT_AS_STR, sizeof(AMOUNT_AS_STR), stdin) != NULL) { // do some error handling
    	ssize_t len = strlen(AMOUNT_AS_STR);
    	if (len > 0 && AMOUNT_AS_STR[len - 1] == '\n') {
        	AMOUNT_AS_STR[len - 1] = '\0';  // remove newline, if exists
    	}
	}

	float AMOUNT = strtof(AMOUNT_AS_STR, &STR_PTR);

	if (*STR_PTR != '\0') {
		printf("Rejected. Invalid amount.\n");
		return;
	}

	if (AMOUNT < 0) { // Reject negative values.
		printf("Rejected. Amount must be greater than 0.\n");
		return;
	}

	char result[256]; // create our entry that we will put inside the linked list.
	result[0] = '\0';
	strcat(result, "INC|");
	strcat(result, DESCRIPTION);
	strcat(result, "|");
	if (strcmp(INC_OR_EXP, "expense") == 0) {
		strcat(result, "-"); // for an expense, we include the negative sign.
	}
	strcat(result, AMOUNT_AS_STR);
	if (strchr(AMOUNT_AS_STR, '.') == NULL) { // add .00 manually if it's an integer
              strcat(result, ".00");
	}

	if (position == -1) {
		insertLast(transactions, result, 1); // append to linked list, mark as an append
	} else {
		insert(transactions, result, 2, position); // append to linked list, mark as insert at
	}

	printf("\n");
	getTotal(transactions, 0); // run the total to see whether we're still on budget or not.

}

int main() {
  // (1) Initial Setup
  char answer[10];
  printf("Welcome to your Personal Finance Tracker!\n\nDo you want to resume a previous session? (y/n): ");
  scanf("%s", answer);

  /*
    It'd be too much of an eyesore to implement case recognition for user input, so
    I'd like to assume the input is lowercase.
   */

  struct Node* Transactions = NULL;

  if (strcmp(answer, "y") == 0) {

    printf("Resuming from last session...\n");
    FILE *file = fopen("./logs/transaction_log.txt", "a+");

    char line[256]; // buffer
    while (fgets(line, sizeof(line), file)) {
      insertLast(&Transactions, line, 0);
    }
    fclose(file); // done with file so we close it
    printf("Previous transactions loaded.\n");

    getTotal(&Transactions, 0);

  } else if (strcmp(answer, "n") == 0) {

    printf("Created a new transaction log. You may begin to run commands.\n");

  } else {

    printf("Invalid input. Please restart the program.\n");
    return 0; // easier to just restart program than make them do a lot of inputs

  }

  int hasQuit = 0; // checker for if the user has quit the program, so we terminate the while loop

  /*
	MARK basically stores what we'd do with each element at the index of the array.
	This is for displaying what to do with print, as well as after quit.
		-1 is to delete - will appear as --- d
		0 is do nothing - will appear as (saved)
		1 is to add at last - will appear as (new)
		2 is to add at position - will appear as +++ i
   */

  while (getchar() != '\n'); // clear input buffer

  while (hasQuit == 0) { // gonna keep looping commands until user says quit

	printf("\nEnter command: ");

	char input[50]; // buffer of 50 characters, that we should not exceed
    if (fgets(input, sizeof(input), stdin) != NULL) { // do some error handling
        ssize_t len = strlen(input);
        if (len > 0 && input[len - 1] == '\n') {
            input[len - 1] = '\0';  // remove newline, if exists
        }
    }

    // time to do the commands

    if (strcmp(input, "add income") == 0) {

		add_transaction(&Transactions, "income");

    } else if (strcmp(input, "add expense") == 0) {

		add_transaction(&Transactions, "expense");

    } else if (strcmp(input, "delete") == 0) { // not gonna exactly match the example output since this is better for me to implement

		printf("Delete position: ");
		int position;

		if (fscanf(stdin, "%d", &position) != 1) {
			printf("Invalid input. Please try again.\n");
			continue;
		} else {
			struct Node* temp = Transactions;
			for (int i = 0; i < position; i++) {
				temp = temp->next;
			}
			if (temp == NULL) {
				printf("Invalid position. Please try again.\n");
				while (getchar() != '\n'); // clear input buffer
				continue;
			}

			temp->mark = -1; // marked for deletion, but we don't delete just yet.

			printf("Transaction at position %d is marked for deletion.\n", position);

			while (getchar() != '\n'); // clear input buffer
		}

    } else if (strcmp(input, "print") == 0) {

		getTotal(&Transactions, 1);

    } else if (strcmp(input, "quit") == 0) {

		FILE *file = fopen("./logs/transaction_log.txt", "w"); // we are overwriting the file.

		if (file == NULL) {
			printf("Unable to open file. Program must be restarted!\n");
			return 0;
		}

		struct Node* TRANSACTION_PTR = Transactions;
		while (TRANSACTION_PTR != NULL) {
			if (TRANSACTION_PTR->mark != -1) {
				char *string = TRANSACTION_PTR->transaction;
        		ssize_t len = strlen(string);
        		if (len > 0 && string[len - 1] == '\n') { // get rid of newlines (then add my own to prevent confusion)
            		string[len - 1] = '\0';
        		}
				if (TRANSACTION_PTR->next != NULL) { fprintf(file, "%s\n", string); }
				else { fprintf(file, "%s", string); }
			}
			TRANSACTION_PTR = TRANSACTION_PTR->next;
		}
		fclose(file); // done with file so we close it

		printf("Saving transactions to file...\n");
		hasQuit = 1; // we have quit.

    } else {

      	printf("\nInvalid command! Please try again.\n");

    }
  }
  printf("Done. Exiting program.\n");
  free(Transactions); // we have finished our work. so we free memory and finish the program.
  return 0;
}
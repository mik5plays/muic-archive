CC = gcc
CFLAGS = -Wall -Wextra -I./include

SRC_DIR = src

SRC_FILES = $(SRC_DIR)/main.c $(SRC_DIR)/LinkedList.c
OBJ_FILES = $(SRC_FILES:.c=.o)

EXEC = release/fintrack

RELEASE_DIR = release

DIST_ZIP = dist/fintrack.zip

all: clean $(EXEC) dist

$(EXEC): $(OBJ_FILES)
	@mkdir -p release
	$(CC) $(OBJ_FILES) -o $(EXEC)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJ_FILES) $(EXEC)

dist: $(EXEC)
	@mkdir -p dist
	zip -r $(DIST_ZIP) src include logs/transaction_log.txt report Makefile

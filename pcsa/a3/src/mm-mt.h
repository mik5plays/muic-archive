#define SIZEX 22000
#define SIZEY 22000
#define BLOCK_SIZE 32 // for BMM
#define NUM_THREADS 8 // for multithreading

long * huge_matrixA;
long * huge_matrixB;
long * huge_matrixC;

FILE *fin1, *fin2, *fout, *ftest;

// A thread
typedef struct {
    long start_row;
    long end_row;
} ThreadArg;

void flush_all_caches();
void load_matrix_base();
void free_all();
void multiply_base();
void compare_results();

// Your job is to finish these three functions in the way that it is fast
void write_results();
void load_matrix();
void multiply();

// Multithreading-specific functions
void multiply_mt();
void* mt_block_multiply();


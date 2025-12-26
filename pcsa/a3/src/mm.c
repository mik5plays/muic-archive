#include<stdio.h>
#include<stdlib.h>
#include<time.h>
#include "mm.h"

// my system's cache size
// Caches (sum of all):
//   L1d:                    256 KiB (8 instances)
//   L1i:                    256 KiB (8 instances)
//   L2:                     4 MiB (8 instances)
//   L3:                     16 MiB (1 instance)


// Task 1: Flush the cache so that we can do our measurement :)
// (technically task 2 based on the pdf)
void flush_all_caches() {
	const size_t flush_size = 32 * 1024 * 1024;
	char *flush_buffer = malloc(flush_size);

	if (!flush_buffer) {
		perror("malloc failed in flush_all_caches");
		exit(EXIT_FAILURE);
	}

	// write then read every line
	for (size_t i = 0; i < flush_size; i += 64) {
		flush_buffer[i] = (char)i;
	}

	volatile char sink = 0;
	for (size_t i = 0; i < flush_size; i += 4096) {
		sink ^= flush_buffer[i];
	}

	free(flush_buffer);
}

void load_matrix_base()
{
	long i;
	huge_matrixA = malloc(sizeof(long)*(long)SIZEX*(long)SIZEY);
	huge_matrixB = malloc(sizeof(long)*(long)SIZEX*(long)SIZEY);
	huge_matrixC = malloc(sizeof(long)*(long)SIZEX*(long)SIZEY);
	// Load the input
	// Note: This is suboptimal because each of these loads can be done in parallel.
	for(i=0;i<((long)SIZEX*(long)SIZEY);i++)
	{
		fscanf(fin1,"%ld", (huge_matrixA+i)); 		
		fscanf(fin2,"%ld", (huge_matrixB+i)); 		
		huge_matrixC[i] = 0;		
	}
}

void free_all()
{
	if (huge_matrixA) free(huge_matrixA);
	if (huge_matrixB) free(huge_matrixB);
	if (huge_matrixC) free(huge_matrixC);
	huge_matrixA = huge_matrixB = huge_matrixC = NULL;
}

// Task 1, row major
void multiply_base()
{
	for (long i = 0; i < SIZEX; i++) {
		for (long j = 0; j < SIZEY; j++) {
			long sum = 0;
			for (long k = 0; k < SIZEY; k++) {
				sum += huge_matrixA[i * SIZEY + k] * huge_matrixB[k * SIZEY + j];
			}
			huge_matrixC[i * SIZEY + j] = sum;
		}
	}
}

void compare_results()
{
	FILE *fcompare = fopen("./out.in", "r");

	rewind(ftest);
	// check for mismatches, wrong answers,
	long i, temp1, temp2;
	for (i = 0; i < ((long)SIZEX * SIZEY); i++) {
		if (fscanf(fcompare, "%ld", &temp1) != 1 ||
			fscanf(ftest, "%ld", &temp2) != 1) {
			fprintf(stderr, "Error reading at index %ld\n", i);
			exit(1);
			}

		if (temp1 != temp2) {
			printf("Mismatch at index %ld: got %ld, expected %ld\n", i, temp1, temp2);
			exit(1);
		}
	}
	// reach here, then solution is correct
	printf("Everything is correct.");
	fclose(fcompare);
	fclose(ftest);
}

void write_results()
{
	rewind(fout);

	for (long i = 0; i < SIZEX; i++) {
		for (long j = 0; j < SIZEY; j++) {
			fprintf(fout, "%ld ", huge_matrixC[i * SIZEY + j]);
		}
		fprintf(fout, "\n");
	}
	fflush(fout);
}

void load_matrix()
{
	// allocate memory for the matrices
	if (!huge_matrixA) {
		huge_matrixA = malloc(sizeof(long) * (long)SIZEX * (long)SIZEY);
		if (!huge_matrixA) { perror("malloc huge_matrixA failed"); exit(1); }
	}
	if (!huge_matrixB) {
		huge_matrixB = malloc(sizeof(long) * (long)SIZEX * (long)SIZEY);
		if (!huge_matrixB) { perror("malloc huge_matrixB failed"); exit(1); }
	}
	if (!huge_matrixC) {
		huge_matrixC = malloc(sizeof(long) * (long)SIZEX * (long)SIZEY);
		if (!huge_matrixC) { perror("malloc huge_matrixC failed"); exit(1); }
	}

	rewind(fin1);
	rewind(fin2);

	for (long i = 0; i < (long)SIZEX * SIZEY; i++) {
		if (fscanf(fin1, "%ld", &huge_matrixA[i]) != 1) {
			fprintf(stderr, "Error reading huge_matrixA at index %ld\n", i);
			exit(1);
		}
		if (fscanf(fin2, "%ld", &huge_matrixB[i]) != 1) {
			fprintf(stderr, "Error reading huge_matrixB at index %ld\n", i);
			exit(1);
		}
		huge_matrixC[i] = 0;
	}
}

// Task 3
void multiply()
{
	long i, j, k, i_block, j_block, k_block;
	for (i_block = 0; i_block < SIZEX; i_block += BLOCK_SIZE) {
		for (j_block = 0; j_block < SIZEY; j_block += BLOCK_SIZE) {
			for (k_block = 0; k_block < SIZEY; k_block += BLOCK_SIZE) {
				// BMM,
				for (i = i_block; i < i_block + BLOCK_SIZE && i < SIZEX; i++) {
					for (j = j_block; j < j_block + BLOCK_SIZE && j < SIZEY; j++) {
						long sum = huge_matrixC[i * SIZEY + j];
						for (k = k_block; k < k_block + BLOCK_SIZE && k < SIZEY; k += 4) {
							sum += huge_matrixA[i * SIZEY + k] * huge_matrixB[k * SIZEY + j];

							if (k + 1 < k_block + BLOCK_SIZE && k + 1 < SIZEY)
								sum += huge_matrixA[i * SIZEY + k+1] * huge_matrixB[(k+1) * SIZEY + j];
							if (k + 2 < k_block + BLOCK_SIZE && k + 2 < SIZEY)
								sum += huge_matrixA[i * SIZEY + k+2] * huge_matrixB[(k+2) * SIZEY + j];
							if (k + 3 < k_block + BLOCK_SIZE && k + 3 < SIZEY)
								sum += huge_matrixA[i * SIZEY + k+3] * huge_matrixB[(k+3) * SIZEY + j];
						}
						huge_matrixC[i * SIZEY + j] = sum;
					}
				}
			}
		}
	}
}

int main()
{
	
	clock_t s,t;
	double total_in_base = 0.0;
	double total_in_your = 0.0;
	double total_mul_base = 0.0;
	double total_mul_your = 0.0;
	fin1 = fopen("./input1.in","r");
	fin2 = fopen("./input2.in","r");
	fout = fopen("./out.in","w");
	ftest = fopen("./reference.in","r");

	flush_all_caches();

	s = clock();
	load_matrix_base();
	t = clock();
	total_in_base += ((double)t-(double)s) / CLOCKS_PER_SEC;
	printf("[Baseline] Total time taken during the load = %f seconds\n", total_in_base);

	s = clock();
	multiply_base();
	t = clock();
	total_mul_base += ((double)t-(double)s) / CLOCKS_PER_SEC;
	printf("[Baseline] Total time taken during the multiply = %f seconds\n", total_mul_base);
	fclose(fin1);
	fclose(fin2);
	fclose(fout);
	free_all();

	flush_all_caches();

	fin1 = fopen("./input1.in", "r");
	fin2 = fopen("./input2.in", "r");
	fout = fopen("./out.in", "w");
	ftest = fopen("./reference.in", "r");

	s = clock();
	load_matrix();
	t = clock();
	total_in_your += ((double)t-(double)s) / CLOCKS_PER_SEC;
	printf("Total time taken during the load = %f seconds\n", total_in_your);

	s = clock();
	multiply();
	t = clock();
	total_mul_your += ((double)t-(double)s) / CLOCKS_PER_SEC;
	printf("Total time taken during the multiply = %f seconds\n", total_mul_your);
	write_results();
	fclose(fin1);
	fclose(fin2);
	fclose(fout);
	free_all();
	compare_results();

	return 0;
}

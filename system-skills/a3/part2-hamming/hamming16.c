#include "hamming16.h"
#include <stdio.h>

/*
* Data structure
*
* +--------+--------+--------+--------+
* | ( 0)EP | ( 1)P  | ( 2)P  | ( 3)D  |  <-- row 0
* +--------+--------+--------+--------+
* | ( 4)P  | ( 5)D  | ( 6)D  | ( 7)D  |  <-- row 1
* +--------+--------+--------+--------+
* | ( 8)P  | ( 9)D  | (10)D  | (11)D  |  <-- row 2
* +--------+--------+--------+--------+
* | (12)D  | (13)D  | (14)D  | (15)D  |  <-- row 3
* +--------+--------+--------+--------+
*          ↑        ↑        ↑        ↑
*        col 0    col 1    col 2    col 3
*/

/*
 * extendedHammingEncode:
 *  - data: lower 11 bits represent the 11-bit data.
 *  - Returns a 16-bit code word with:
 *      - Extended Parity (EP) at bit 0.
 *      - Hamming parity bits at positions 1, 2, 4, 8.
 *      - Data bits at positions 3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15.
 */
uint16_t extendedHammingEncode(uint16_t data) {
    uint16_t code = 0;

    // TODO: Insert the 11 data bits into the proper positions of the 15-bit Hamming code.
    // For example, assign bit0 of data to position 3, bit1 to position 5, etc.

    code |= ((data >> 0) & 0x1) << 3;
    code |= ((data >> 1) & 0x1) << 5;
    code |= ((data >> 2) & 0x1) << 6;
    code |= ((data >> 3) & 0x1) << 7;
    code |= ((data >> 4) & 0x1) << 9;
    code |= ((data >> 5) & 0x1) << 10;
    code |= ((data >> 6) & 0x1) << 11;
    code |= ((data >> 7) & 0x1) << 12;
    code |= ((data >> 8) & 0x1) << 13;
    code |= ((data >> 9) & 0x1) << 14;
    code |= ((data >> 10) & 0x1) << 15;

    // TODO: Compute the parity bits for positions 1, 2, 4, and 8.
    // TODO: Insert the computed parity bits into the code word.
    // For each parity bit, XOR all bits in the code word covered by that parity position.
    // Compute parity for position 1
    uint16_t p1 = 0;
    p1 = ((code >> 3) & 0x1) ^ ((code >> 5) & 0x1) ^ ((code >> 7) & 0x1) ^
         ((code >> 9) & 0x1) ^ ((code >> 11) & 0x1) ^ ((code >> 13) & 0x1) ^
         ((code >> 15) & 0x1);
    code |= (p1 & 0x1) << 1;

    // Compute parity for position 2
    uint16_t p2 = 0;
    p2 = ((code >> 3) & 0x1) ^ ((code >> 6) & 0x1) ^ ((code >> 7) & 0x1) ^
         ((code >> 10) & 0x1) ^ ((code >> 11) & 0x1) ^ ((code >> 14) & 0x1) ^
         ((code >> 15) & 0x1);
    code |= (p2 & 0x1) << 2;

    // Compute parity for position 4
    uint16_t p4 = 0;
    p4 = ((code >> 5) & 0x1) ^ ((code >> 6) & 0x1) ^ ((code >> 7) & 0x1) ^
         ((code >> 12) & 0x1) ^ ((code >> 13) & 0x1) ^ ((code >> 14) & 0x1) ^
         ((code >> 15) & 0x1);
    code |= (p4 & 0x1) << 4;

    // Compute parity for position 8
    uint16_t p8 = 0;
    p8 = ((code >> 9) & 0x1) ^ ((code >> 10) & 0x1) ^ ((code >> 11) & 0x1) ^
         ((code >> 12) & 0x1) ^ ((code >> 13) & 0x1) ^ ((code >> 14) & 0x1) ^
         ((code >> 15) & 0x1);
    code |= (p8 & 0x1) << 8;

    // TODO: Compute the overall parity bit (bit 0) as the XOR of bits 1 through 15.
    // Then, set bit 0 accordingly.
    uint16_t opb = 0;
    for (int i = 1; i < 16; i++) {
        opb ^= (code >> i) & 0x1;
    }

    code |= (opb & 0x1) << 0;

    return code;
}

/*
 * extendedHammingDecode:
 *  - code: 16-bit Extended Hamming code word.
 *  - errorPos: pointer to an integer to store the bit position of a corrected error.
 *  - Returns the original 11-bit data (in the lower 11 bits).
 *
 * The function should:
 *   1. Recompute the parity bits to form a syndrome.
 *   2. Check the overall parity (bit 0) to confirm if an error occurred.
 *   3. If a single-bit error is detected, correct the error by flipping that bit.
 *   4. Extract and return the 11-bit data.
 */

uint16_t extendedHammingDecode(uint16_t code, int *errorPos) {
    uint16_t correctedCode = code;
    int syndrome = 0;
    int parity = 0;

    // TODO: Recompute parity bits for positions 1, 2, 4, and 8.
    // Combine the parity check results to form a syndrome (a number from 0 to 15).

    // Calculate syndrome by using each parity bit
    int p1 = 0, p2 = 0, p4 = 0, p8 = 0;

    p1 = ((code >> 3) & 0x1) ^ ((code >> 5) & 0x1) ^ ((code >> 7) & 0x1) ^
         ((code >> 9) & 0x1) ^ ((code >> 11) & 0x1) ^ ((code >> 13) & 0x1) ^
         ((code >> 15) & 0x1);

    p2 = ((code >> 3) & 0x1) ^ ((code >> 6) & 0x1) ^ ((code >> 7) & 0x1) ^
         ((code >> 10) & 0x1) ^ ((code >> 11) & 0x1) ^ ((code >> 14) & 0x1) ^
         ((code >> 15) & 0x1);

    p4 = ((code >> 5) & 0x1) ^ ((code >> 6) & 0x1) ^ ((code >> 7) & 0x1) ^
         ((code >> 12) & 0x1) ^ ((code >> 13) & 0x1) ^ ((code >> 14) & 0x1) ^
         ((code >> 15) & 0x1);

    p8 = ((code >> 9) & 0x1) ^ ((code >> 10) & 0x1) ^ ((code >> 11) & 0x1) ^
         ((code >> 12) & 0x1) ^ ((code >> 13) & 0x1) ^ ((code >> 14) & 0x1) ^
         ((code >> 15) & 0x1);

	syndrome = (p8 << 3) | (p4 << 2) | (p2 << 1) | p1;

    printf("P1: %d, P2: %d, P4: %d, P8: %d, Syndrome: 0x%03X\n", p1, p2, p4, p8, syndrome); // debug

    // TODO: Check the overall parity bit (bit 0) to decide if an error occurred.
    // If syndrome != 0 and overall parity is incorrect, then a single-bit error occurred.

    // Correct the error by flipping the bit at the position indicated by syndrome.
    // Set *errorPos to the corrected bit position (1-indexed).
    // If no error, set *errorPos to 0.
    // If error detection indicates an uncorrectable error, set *errorPos to -1.

    for (int i = 1; i < 16; i++) {
        parity ^= (correctedCode >> i) & 0x1;
    }

    uint16_t ep = (correctedCode >> 0) & 0x1;
    int overallParityError = (parity != ep);
    *errorPos = 0;

    if (syndrome != 0) {
        if (overallParityError) {
            *errorPos = syndrome;
            correctedCode ^= (1 << (syndrome - 1));
        } else {
            *errorPos = -1;
        }
    } else if (overallParityError) {
        *errorPos = 0;
    }

    // TODO: Extract the original 11-bit data from the corrected code word.

    uint16_t data = 0;

    data |= ((correctedCode >> 2) & 0x1) << 0;
    data |= ((correctedCode >> 4) & 0x1) << 1;
    data |= ((correctedCode >> 6) & 0x1) << 3;
    data |= ((correctedCode >> 8) & 0x1) << 4;
    data |= ((correctedCode >> 9) & 0x1) << 5;
    data |= ((correctedCode >> 10) & 0x1) << 6;
    data |= ((correctedCode >> 11) & 0x1) << 7;
    data |= ((correctedCode >> 12) & 0x1) << 8;
    data |= ((correctedCode >> 13) & 0x1) << 9;
    data |= ((correctedCode >> 14) & 0x1) << 10;

    return data;
}

/*
 * introduceError:
 *  - code: a 16-bit code word.
 *  - bitPos: bit position (1-indexed, 1 to 16) to flip.
 *  - Returns the code word with the specified bit flipped.
 */
uint16_t introduceError(uint16_t code, int bitPos) {
    return code ^ (1 << (bitPos - 1));
}
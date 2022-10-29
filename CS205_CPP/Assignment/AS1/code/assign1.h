/**
 * @file assign1.h
 * @brief Functions that you need to implement for SUSTech CS205 (s22) assignment 1
 * @details EVERY functions should be defined in your source file, even if you could not implement it (in this case, leave a blank function body is okay). You NEED NOT modify this file, and DON'T submit this to Blackboard
 * @author gdjs2
 * @author chris
 * @date 2022-03-01
 */

#ifndef __CS205_ASSIGN_1_H__
#define __CS205_ASSIGN_1_H__

#include "assign1_mat.h"

/// Instead of using the *magic number* 1e9 + 7 everywhere in your program, you should use this constant when you need to take a module
extern const int MODULO;

/**
 * Calculate power of x^n in O(log n)
 * @todo task 1, 20 pts
 *
 * @param x the base number
 * @param n the exponent number
 * @return the power, an integer
 */
int quick_power(int x, int n);

/**
 * Calculate the result the of matrix addition
 * When returning 1, do not modify the entities in @p mat_res
 * @todo task 2-1, 20 pts
 *
 * @param mat_res the matrix used to store the result of A + B
 * @return 1 if the size of @p mat_res does not match required size or @p mat_a and @p mat_b cannot do the addition; 0 otherwise
 * @attention it is guaranteed that @p mat_res is different from @p mat_a or @p mat_b
 */
int matrix_addition(matrix mat_a, matrix mat_b, matrix mat_res);

/**
 * Calculate the result the of matrix multiplication
 * @todo task 2-2, 30 pts
 *
 * @param mat_res the matrix used to store the result of A * B
 * @return 1 if the size of @p mat_res does not match required size or @p mat_a and @p mat_b cannot do the multiplication; 0 otherwise
 * @attention it is guaranteed that @p mat_res is different from @p mat_a or @p mat_b
 */
int matrix_multiplication(matrix mat_a, matrix mat_b, matrix mat_res);

/**
 * Calculate the result the of matrix exponentiation in a naive way
 * @todo task 3, 15 pts
 *
 * @param mat_res the matrix used to store the result of mat_a ^ exp
 * @return 1 if the size of @p mat_res does not match required size or @p mat_a is not square; 0 otherwise
 * @attention it is guaranteed that @p mat_res is different from @p mat_a
 */
int naive_matrix_exp(matrix mat_a, int exp, matrix mat_res);

/**
 * Calculate the result the of matrix exponentiation in a fast way
 * @todo task 4, 15 pts
 *
 * @param mat_res the matrix used to store the result of mat_a ^ exp
 * @return 1 if the size of @p mat_res does not match required size or @p mat_a is not square; 0 otherwise
 * @attention it is guaranteed that @p mat_res is different from @p mat_a
 */
int fast_matrix_exp(matrix mat_a, long long exp, matrix mat_res);

/**
 * Calculate the nth number in Fibonacci Sequence using **Fast Matrix Exponentiation**
 * @todo task 5, 20 pts
 *
 * @param n the index of the result in Fibonacci Sequence
 * @return the number with index @n in Fibonacci Sequence
 */
int fast_cal_fib(long long n);

#endif  // __CS205_ASSIGN_1_H__

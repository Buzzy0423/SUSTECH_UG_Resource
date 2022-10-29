/**
 * @file assign1_mat.h
 * @brief Provided struct and functions for SUSTech CS205 (s22) assignment 1
 * @details The functions defined in this header have been implemented, you NEED NOT modify this file, and DON'T submit this to Blackboard
 * @author gdjs2
 * @author chris
 * @date 2022-03-01
 */


#ifndef __CS205_ASSIGN_1_MAT_H__
#define __CS205_ASSIGN_1_MAT_H__


#include <stddef.h>


/**
 * @brief Structure to store a matrix with integers
 * @details In this assignment, **int**s are stored in the matrix
 */
typedef struct matrix {
    int m_col;  ///< number of columns in the matrix, aka. width
    int m_row;  ///< number of rows in the matrix, aka. height
    size_t m_data_size;  ///< number of *bytes* in the data zone, aka. m_col * m_row * sizeof(int)
    void *m_data;
} matrix;


/**
 * Create a matrix filled with *zeros*
 *
 * @param row the height (row) of the matrix
 * @param col the width (col) of the matrix
 * @return a matrix filled with zeros, size: row * col
 */
matrix create_matrix_all_zero(int row, int col);


/**
 * Free the memory pointed by m_data in matrix mat
 * You **MUST** destroy every matrix before the program exits properly
 *
 * @param mat the matrix you not longer need
 */
void delete_matrix(matrix mat);


/**
 * Hard copy a matrix
 * 
 * @param mat the matrix to be copied
 * @return a copy of the @p mat
 */
matrix copy_matrix(matrix mat);


/**
 * Set the entry at @p row and @p col of @p mat to the value of @p val
 *
 * @param mat the matrix to be affected
 * @param val value will be set to mat[row, col]
 * @attention if @p row or @p col is out of bounds, no action will be taken
 */
void set_by_index(matrix mat, int row, int col, int val);


/**
 * Get the value in the entry at @p row and @p col of @p mat
 *
 * @param mat the matrix to be affected
 * @return value of mat[row, col]
 * @attention if @p row or @p col is out of bounds, retval will be 0xdeadbeef
 */
int get_by_index(matrix mat, int row, int col);


/**
 * Scalar multiplication of a matrix and an integer
 * If the size of @p mat_res does not match the size of @p mat_a, DO NOT modify the data in @p mat_res
 *
 * @param mat_a the matrix
 * @param x the constant integer
 * @param mat_res the matrix in which the result should be stored
 * @return 1 if the size of @p mat_res does not match the size of @p mat_a, 0 otherwise
 */
int scalar_multiplication(matrix mat_a, int x, matrix mat_res);

#endif  // __CS205_ASSIGN_1_MAT_H__

#pragma once

/**
 * @brief return product of 2 ~ 5 int numbers
 *
 * @return int product of numbers
 */
inline int product(int a, int b, int c = 1, int d = 1, int e = 1) {
    return a * b * c * d * e;
}

/**
 * @brief return product of 2 ~ 5 doubles numbers
 *
 * @return double product of numbers
 */
inline double product(double a, double b, double c = 1, double d = 1, double e = 1) {
    return a * b * c * d * e;
}
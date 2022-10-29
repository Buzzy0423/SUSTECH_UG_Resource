#include <stdio.h>
#include <string.h>
#include "assign1.h"

int Mod(long long num);

int main() {
    printf("%d", fast_cal_fib(11111111111));
}

int Mod(long long num) {
    return (int) (num % MODULO);
}

int quick_power(int x, int n) {
    long long ans = 1;
    long long power_factor = x % MODULO;
    while (n != 0) {
        if (n % 2 == 1) {
            ans = Mod(ans * power_factor);
        }
        power_factor = Mod(power_factor * power_factor);
        n = n / 2;
    }
    return (int) ans;
}

int matrix_addition(matrix mat_a, matrix mat_b, matrix mat_res) {
    if (mat_res.m_row != mat_a.m_row ||
        mat_res.m_col != mat_a.m_col ||
        mat_res.m_row != mat_b.m_row ||
        mat_res.m_col != mat_b.m_col) {
        return 1;
    }
    for (int i = 0; i < mat_res.m_row; ++i) {
        for (int j = 0; j < mat_res.m_col; ++j) {
            int val = Mod(Mod(get_by_index(mat_a, i, j)) + Mod(get_by_index(mat_b, i, j)));
            set_by_index(mat_res, i, j, val);
        }
    }
    return 0;
}

int matrix_multiplication(matrix mat_a, matrix mat_b, matrix mat_res) {
    if (mat_a.m_col != mat_b.m_row ||
        mat_a.m_row != mat_res.m_row ||
        mat_b.m_col != mat_res.m_col) {
        return 1;
    }
    matrix m = create_matrix_all_zero(mat_res.m_row, mat_res.m_col);
    for (int i = 0; i < mat_a.m_row; ++i) {
        for (int j = 0; j < mat_a.m_col; ++j) {
            int tmp = Mod(get_by_index(mat_a, i, j));//增加内存读取连续性
            for (int k = 0; k < mat_b.m_col; ++k) {
                long long tmp2 = (long long) tmp * (long long) Mod(get_by_index(mat_b, j, k));
                set_by_index(m, i, k, Mod(get_by_index(m, i, k) + Mod(tmp2)));
            }
        }
    }
    memcpy(mat_res.m_data, m.m_data, mat_res.m_data_size);
    return 0;
}

int naive_matrix_exp(matrix mat_a, int exp, matrix mat_res) {
    if (mat_a.m_col != mat_res.m_col ||
        mat_a.m_row != mat_res.m_row ||
        mat_a.m_col != mat_a.m_row ||
        exp < 0) {
        return 1;
    }
    matrix tmp = copy_matrix(mat_a);
    memcpy(mat_res.m_data, mat_a.m_data, mat_res.m_data_size);
    for (int i = 0; i < exp - 1; ++i) {
        matrix_multiplication(mat_a, tmp, mat_res);
        memcpy(tmp.m_data, mat_res.m_data, tmp.m_data_size);
    }
    return 0;
}

int fast_matrix_exp(matrix mat_a, long long exp, matrix mat_res) {
    if (mat_a.m_col != mat_res.m_col ||
        mat_a.m_row != mat_res.m_row ||
        mat_a.m_col != mat_a.m_row ||
        exp < 0) {
        return 1;
    }
    matrix ans = create_matrix_all_zero(mat_res.m_row, mat_res.m_col);
    matrix tmp = create_matrix_all_zero(mat_res.m_row, mat_res.m_col);
    matrix power_factor = copy_matrix(mat_a);
    for (int i = 0; i < ans.m_row; ++i) {
        set_by_index(ans, i, i, 1);
    }
    while (exp != 0) {
        if (exp % 2 == 1) {
            matrix_multiplication(ans, power_factor, ans);
        }
        matrix_multiplication(power_factor, power_factor, tmp);
        memcpy(power_factor.m_data, tmp.m_data, power_factor.m_data_size);
        exp = exp / 2;
    }
    memcpy(mat_res.m_data, ans.m_data, mat_res.m_data_size);
    return 0;
}

int fast_cal_fib(long long n) {
    matrix mat = create_matrix_all_zero(2, 2);
    matrix mat_res = create_matrix_all_zero(2, 2);
    set_by_index(mat, 0, 0, 1);
    set_by_index(mat, 0, 1, 1);
    set_by_index(mat, 1, 0, 1);
    fast_matrix_exp(mat, n, mat_res);
    return get_by_index(mat_res, 0, 1);
}
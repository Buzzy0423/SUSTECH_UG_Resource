#include <iostream>
#include <string.h>
#include <cstring>

using namespace std;

const int MODULO = 1e9 + 7;

typedef struct matrix {
    int m_col;           ///< number of columns in the matrix, aka. width
    int m_row;           ///< number of rows in the matrix, aka. height
    size_t m_data_size;  ///< number of *bytes* in the data zone, aka. m_col * m_row * sizeof(int)
    void *m_data;
} matrix;

int Mod(long long num);

matrix copy_matrix(matrix mat);

matrix create_matrix_all_zero(int row, int col);

void set_by_index(matrix mat, int row, int col, int val);

int get_by_index(matrix mat, int row, int col);

int matrix_multiplication(matrix mat_a, matrix mat_b, matrix mat_res);

int fast_matrix_exp(matrix mat_a, long long exp, matrix mat_res);

int main() {
    cin.sync_with_stdio(false);
    cout.sync_with_stdio(false);
    int n = 0;
    int m = 0;
    cin >> n >> m;
    matrix mat = create_matrix_all_zero(m, m);
    if (m != 1){
        for (size_t i = 0; i < m - 1; i++) {
            set_by_index(mat, 0, i, 1);
            set_by_index(mat, i + 1, i, 1);
        }
        set_by_index(mat, 0, m - 1, 1);
        matrix exp = create_matrix_all_zero(m,m);
        fast_matrix_exp(mat, n, exp);
        long long ans = Mod(get_by_index(exp, 0, 0) + get_by_index(exp, 0, 1));
        cout << ans;
    }else{
        cout << 1;
    }
}

int Mod(long long num) {
    return (int)(num % MODULO);
}

matrix copy_matrix(matrix mat) {
    matrix _mat = mat;
    _mat.m_data = malloc(_mat.m_data_size);
    memcpy(_mat.m_data, mat.m_data, _mat.m_data_size);
    return _mat;
}

matrix create_matrix_all_zero(int row, int col) {
    matrix mat;
    mat.m_col = col;
    mat.m_row = row;
    mat.m_data_size = row * col * sizeof(int);
    mat.m_data = malloc(mat.m_data_size);
    memset(mat.m_data, 0, mat.m_data_size);
    return mat;
}

void set_by_index(matrix mat, int row, int col, int val) {
    if (row >= mat.m_row || col >= mat.m_col) return;
    *(((int *)mat.m_data) + row * mat.m_col + col) = val;
}

int get_by_index(matrix mat, int row, int col) {
    if (row >= mat.m_row || col >= mat.m_col) return 0xdeadbeef;
    return *(((int *)mat.m_data) + row * mat.m_col + col);
}

int matrix_multiplication(matrix mat_a, matrix mat_b, matrix mat_res) {
    matrix m = create_matrix_all_zero(mat_res.m_row, mat_res.m_col);
    for (int i = 0; i < mat_a.m_row; ++i) {
        for (int j = 0; j < mat_a.m_col; ++j) {
            int tmp = Mod(get_by_index(mat_a, i, j));  //增加内存读取连续性
            for (int k = 0; k < mat_b.m_col; ++k) {
                long long tmp2 = (long long)tmp * (long long)Mod(get_by_index(mat_b, j, k));
                set_by_index(m, i, k, Mod(get_by_index(m, i, k) + Mod(tmp2)));
            }
        }
    }
    memcpy(mat_res.m_data, m.m_data, mat_res.m_data_size);
    return 0;
}

int fast_matrix_exp(matrix mat_a, long long exp, matrix mat_res) {
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
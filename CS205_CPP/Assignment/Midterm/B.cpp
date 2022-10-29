#include <iostream>
using namespace std;

int main() {
    int len = 0;
    int arr_start = 0;
    cin >> len >> arr_start;
    int XOR[len];
    for (size_t i = 0; i < len; i++) {
        XOR[i] = arr_start + 2 * i;
        cout << XOR[i];
        if (i == len - 1) {
            cout << endl;
        } else {
            cout << ",";
        }
    }
    int tmp = XOR[0];
    for (size_t i = 1; i < len; i++) {
        tmp = tmp ^ XOR[i];
    }
    char con[32];
    long long tmp2 = 1;
    for (size_t i = 0; i < 32; i++) {
        if (tmp & tmp2) {
            con[i] = '1';
        } else {
            con[i] = '0';
        }
        tmp2 = tmp2 << 1;
    }
    for (size_t i = 0; i < 32; i++) {
        cout << con[31 - i];
    }
}
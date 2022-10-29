#include <cmath>
#include <complex>
#include <iostream>
#include <iomanip>
#define pi 3.1415926535
#define m  131072
using namespace std;

typedef complex<double> cp;
double arr[m];

cp *FFT(int x, int N, int s);

double mag(cp t);

int main() {
    cout.sync_with_stdio(false);
    int n = 0;
    cin >> n;
    for (size_t i = 0; i < pow(2, n); i++) {
        cin >> arr[i];
    }
    cp *ans = FFT(0, pow(2, n), 1);
    for (size_t i = 0; i < pow(2, n); i++) {
        printf("%.10f\n", mag(*(ans + i)));
    }
    delete ans;
}

cp *FFT(int x, int N, int s) {
    if (N == 1) {
        cp *tmp = new cp[N];
        *tmp = cp(arr[x],0);
        return tmp;
    } else {
        cp omega = cp(cos(2 * pi / N), sin(2 * pi / N));
        cp *arr1 = FFT(x, N / 2, 2 * s);
        cp *arr2 = FFT(x + s, N / 2, 2 * s);
        cp *tmp = new cp[N];
        for (size_t i = 0; i < N / 2; i++) {
            cp p = *(arr1 + i);
            cp q = pow(omega, i) * *(arr2 + i);
            *(tmp + i) = p + q;
            *(tmp + i + N/2) = p - q;
        }
        delete arr1;
        delete arr2;
        return tmp;
    }
}

double mag(cp t){
    return pow(pow(t.real(), 2) + pow(t.imag(), 2), 0.5);
}

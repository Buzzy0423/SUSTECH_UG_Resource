#include <iostream>
using namespace std;

long long fb(long long n);

int main() {
    int n = 0;
    cin >> n;
    cout << fb(n) / 3;
}

long long fb(long long n) {
    if (n == 1 || n == 2) {
        return 1;
    }
    return fb(n - 1) + fb(n - 2);
}
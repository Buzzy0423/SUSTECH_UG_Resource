#include <cmath>
#include <iostream>
using namespace std;

long long Q5(long long num, long long exp);

int main() {
    long long ans = Q5(3, 3);
    cout << ans;
}

long long Q5(long long num, long long exp) {
    if (exp == 0) {
        return num;
    }
    return pow(Q5(num, exp - 1), 2);
}
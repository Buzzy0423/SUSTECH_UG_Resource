#include <iostream>
#include "swap.h"
using namespace std;

int main() {
    int a = 1;
    int b = 2;
    cout << "a: " << a << " b: " << b << endl;
    useless_swap(a, b);
    cout << "a: " << a << " b: " << b << endl;
    swap(&a, &b);
    cout << "a: " << a << " b: " << b << endl;
    swap(a, b);
    cout << "a: " << a << " b: " << b << endl;
    return 0;
}
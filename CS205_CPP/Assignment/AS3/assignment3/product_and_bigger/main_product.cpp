#include <iostream>

#include "product.h"

using namespace std;

int main() {
    // test product of int
    cout << "=== test product of int ===" << endl;
    cout << product(1, 2) << endl;
    cout << product(1, 2, 3) << endl;
    cout << product(1, 2, 3, 4) << endl;
    cout << product(1, 2, 3, 4, 5) << endl;

    // test product of double
    cout << "=== test product of double ===" << endl;
    cout << product(0.4, 2.0) << endl;
    cout << product(0.4, 2.0, 3.0) << endl;
    cout << product(0.4, 2.0, 3.0, 4.0) << endl;
    cout << product(0.4, 2.0, 3.0, 4.0, 5.0) << endl;
}
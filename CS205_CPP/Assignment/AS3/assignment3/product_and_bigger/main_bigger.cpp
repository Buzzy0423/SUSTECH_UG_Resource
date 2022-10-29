#include <iostream>

#include "bigger.h"

using namespace std;

int main() {
    // test int
    cout << "=== test int ===" << endl;
    cout << bigger(1, 2) << endl;

    // test double
    cout << "=== test double ===" << endl;
    cout << bigger(1.5, 2.5) << endl;

    // test const char * (type of "a" is const char *)
    cout << "=== test const char* ===" << endl;
    cout << bigger("d", "ab") << endl;

    // test string
    cout << "=== test string ===" << endl;
    string s1 = "d";
    string s2 = "ab";
    cout << bigger(s1, s2) << endl;
}
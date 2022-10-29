#include <iostream>

#include "pair.h"

using namespace std;

int main() {
    // test case 1
    cout << "=== testcase 1 ===" << endl;
    string s = "12123434565";
    // 6 should not be printed
    int length = 0;
    Pair* pairs = splitPair(s, length);
    for (int i = 0; i < length; i++) {
        printPair(&pairs[i]);
    }

    // test case 2
    cout << "=== testcase 2 ===" << endl;
    s = "12123434";
    // all of 1, 2, 3, 4 should be printed
    length = 0;
    pairs = splitPair(s, length);
    for (int i = 0; i < length; i++) {
        printPair(&pairs[i]);
    }
}
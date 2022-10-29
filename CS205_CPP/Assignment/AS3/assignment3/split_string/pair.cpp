#include "pair.h"

#include <iostream>

using namespace std;

Pair* splitPair(string s, int& length) {
    int len = s.size();
    length = (len % 4 == 2) ? len / 2 - 1 : len / 2;
    Pair* arr = new Pair[length];
    int count = 0;
    for (size_t i = 0; i + 2 < len; i += 4) {
        Pair p;
        p.l = s.at(i);
        p.r = s.at(i + 2);
        *(arr + count++) = p;
        if (i + 3 >= len) {
            break;
        } else {
            Pair p2;
            p2.l = s.at(i + 1);
            p2.r = s.at(i + 3);
            *(arr + count++) = p2;
        }
    }
    return arr;
}

void printPair(Pair* pair) {
    cout << pair->l << " " << pair->r << endl;
}

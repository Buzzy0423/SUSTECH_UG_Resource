#include "Stack.h"

#include <iostream>

using namespace std;

int main() {
    Stack s1;
    Stack s2(5);
    s1.push(1);
    s1.push(4);
    Stack s3(s1);
    Item i = 0;
    s3.pop(i);
    cout << i << endl;
    s3.pop(i);
    cout << i << endl;
    cout << boolalpha << s3.isempty() << endl;
    cout << boolalpha << s3.pop(i) << endl;
    for (int i = 0; i < 5; i++) {
        s2.push(1);
    }
    cout << boolalpha << s2.isfull() << endl;
    cout << boolalpha << s2.push(2) << endl;
}
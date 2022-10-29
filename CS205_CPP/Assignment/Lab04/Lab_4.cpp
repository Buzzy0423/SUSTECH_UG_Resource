#include <iostream>
using namespace std;

void Max(int a) { cout << "Max 1" << endl; }

void Max(int a, int b) { cout << "Max 2" << endl; }

void Max(int a, int b, int c) { cout << "Max 3" << endl; }

int main(){   

    int a;    int &b = a;    b = 10;   

    cout << a << endl;
    return 0;
}
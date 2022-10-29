#include "candybar.hpp"
using namespace std;

int main() {
    CandyBar cb;
    CandyBar *cbb = &cb;
    cout << "Call the set function of Passing by pointer" << endl;
    set(cbb);
    cout << "Call the show function of Passing by pointer" << endl;
    show(cbb);
    cout << "Call the set function of Passing by reference" << endl;
    set(cb);
    cout << "Call the show function of Passing by reference" << endl;
    show(cb);
}
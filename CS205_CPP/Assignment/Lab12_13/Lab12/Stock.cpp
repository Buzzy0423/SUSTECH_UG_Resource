#include "Stock.h"

int main(){
    Stock s("abcde", 13, 13.123);
    cout << s << endl;
    s.sell(1, 1324);
    s.buy(5, 120);
    cout << s << endl;
}
#include <iostream>
#include "china.h"

using namespace std;

int main(){
    generate("world_cities.csv");
    string s = string("Beijing,,China,39.900,116.400");
    china(s);
    cout << "Complete!" << endl;
}
#include "china.h"

#include <cstring>
#include <fstream>
#include <iostream>
#include <string>

using namespace std;

void generate(const char* dir) {
    ifstream infile;
    ofstream outfile;
    infile.open(dir);
    outfile.open("china_cities.csv");
    string line;
    while (infile >> line) {
        if (china(line)) {
            outfile << line << endl;
        }
    }
    infile.close();
    outfile.close();
}

bool china(string line) {
    int l = 0;
    int cnt = 0;
    for (size_t i = 0; i < line.length(); i++) {
        if (line.at(l++) == ',') {
            if (cnt++ == 1) {
                break;
            }
        }
    }
    if (l + 5 >= line.length()) {
        return false;
    } else {
        return line.substr(l, 5).compare(string("China")) == 0;
    }
}
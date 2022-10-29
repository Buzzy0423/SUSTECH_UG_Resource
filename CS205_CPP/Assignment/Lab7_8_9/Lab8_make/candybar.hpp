#ifndef EXC_CANDYBAR_H
#define EXC_CANDYBAR_H
#include <iostream>

struct CandyBar {
    char brand[30];
    double weight;
    int calories;
};

void set(CandyBar &cb);

void set(CandyBar *const cb);

void show(const CandyBar &cb);

void show(const CandyBar *cb);

#endif
#include "candybar.hpp"
using namespace std;

void set(CandyBar &cb) {
    cout << "Please enter the brand name: ";
    cin >> cb.brand;
    cout << "Please enter the weight: ";
    cin >> cb.weight;
    cout << "Please enter the calories: ";
    cin >> cb.calories;
}

void set(CandyBar *const cb) {
    cout << "Please enter the brand name: ";
    cin >> cb->brand;
    cout << "Please enter the weight: ";
    cin >> cb->weight;
    cout << "Please enter the calories: ";
    cin >> cb->calories;
}

void show(const CandyBar &cb) {
    cout << "The brand name is: " << cb.brand << endl;
    cout << "The weight is: " << cb.weight << endl;
    cout << "The calories is:" << cb.calories << endl;
}

void show(const CandyBar *cb) {
    cout << "The brand name is: " << cb->brand << endl;
    cout << "The weight is: " << cb->weight << endl;
    cout << "The calories is:" << cb->calories << endl;
}

#include "sales.h"

#include <iostream>

namespace SALES {
void setSales(Sales &s, const double ar[], int n) {
    double max = -1;
    double min = INFINITY;
    double sum = 0;
    for (size_t i = 0; i < 4; i++) {
        if (!ar[i] || i > n - 1) {
            s.sales[i] = 0;
        } else {
            s.sales[i] = ar[i];
            max = ar[i] > max ? ar[i] : max;
            min = ar[i] < min ? ar[i] : min;
            sum += s.sales[i];
        }
    }
    s.average = n < 4 ? sum / n : sum/4;
    s.max = max;
    s.min = min;
}

void setSales(Sales &s) {
    double max = -1;
    double min = INFINITY;
    double sum = 0;
    std::cout << "Enter sales for 4 quarters: ";
    for (size_t i = 0; i < 4; i++) {
        std::cin >> s.sales[i];
        max = s.sales[i] > max ? s.sales[i] : max;
        min = s.sales[i] < min ? s.sales[i] : min;
        sum += s.sales[i];
    }
    s.average = sum / 4;
    s.max = max;
    s.min = min;
}

void showSales(const Sales &s) {
    std::cout << "Sales: ";
    for (int i = 0; i < 4; i++) {
        std::cout << s.sales[i] << " ";
    }
    std::cout << std::endl;
    std::cout << "Average: " << s.average << std::endl;
    std::cout << "Max: " << s.max << std::endl;
    std::cout << "Min: " << s.min << std::endl;
}
}  // namespace SALES
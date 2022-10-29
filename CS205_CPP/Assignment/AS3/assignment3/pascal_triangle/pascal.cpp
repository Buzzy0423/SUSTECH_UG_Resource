#include "pascal.h"

int pascal(int row, int column) {
    if (row == 0) {
        if (column == 0) {
            return 1;
        } else {
            return 0;
        }
    } else {
        return (pascal(row - 1, column) + pascal(row - 1, column - 1));
    }
}
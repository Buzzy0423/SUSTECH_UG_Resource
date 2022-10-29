#include <iostream>
#ifndef NUMBER_H
#define NUMBER_H

class Number {
   private:
    int n;

   public:
    Number() : n(0) {}
    Number(int i) {
        this->n = i;
    }
    Number operator++(){
        ++n;
        return *this;
    }
    Number operator++(int){
        Number tmp = *this;
        ++*this;
        return tmp;
    }
    Number operator--(){
        --n;
        return *this;
    }
    Number operator--(int){
        Number tmp = *this;
        --*this;
        return tmp;
    }
    friend std::ostream &operator<<(std::ostream &out, Number &n){
        out << n.n;
        return out;
    }

    friend std::istream &operator>>(std::istream &in, Number &n){
        in >> n.n;
        return in;
    }
};

#endif
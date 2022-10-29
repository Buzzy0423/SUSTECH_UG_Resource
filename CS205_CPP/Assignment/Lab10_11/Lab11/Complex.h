#include <iostream>
#ifndef COMPLEX_H
#define COMPLEX_H

class Complex {
   private:
    double real;
    double imag;

   public:
    Complex() : real(1), imag(1) {}
    Complex(double r, double i) {
        real = r;
        imag = i;
    }
    Complex operator+(const Complex &rhs) {
        return Complex(this->real + rhs.real, this->imag + rhs.imag);
    }
    Complex operator~() {
        return Complex(this->real, -this->imag);
    }
    Complex operator-() {
        return Complex(-this->real, -this->imag);
    }
    Complex operator-(const Complex &rhs) {
        return Complex(this->real - rhs.real, this->imag - rhs.imag);
    }
    Complex operator-(double n) {
        return Complex(this->real - n, this->imag);
    }
    friend Complex operator-(double n, Complex &rhs);
    Complex operator*(const Complex &rhs) {
        return Complex(this->real * rhs.real - this->imag * rhs.imag, this->real * rhs.imag + this->imag * rhs.real);
    }
    Complex operator*(double n) {
        return Complex(this->real * n, this->imag * n);
    }
    friend Complex operator*(double n, Complex &rhs);
    bool operator==(Complex &rhs) {
        if (rhs.imag == this->imag && rhs.real == this->real) {
            return true;
        } else {
            return false;
        }
    }
    bool operator!=(Complex &rhs) {
        if (*this == rhs) {
            return false;
        } else {
            return true;
        }
    }
    friend std::ostream &operator<<(std::ostream &output, const Complex &c) {
        if (c.imag > 0) {
            output << c.real << " + " << c.imag << "i";
        } else {
            output << c.real << " - " << -c.imag << "i";
        }
        return output;
    }
    friend std::istream &operator>>(std::istream &input, Complex &c) {
        std::cout << "real: ";
        input >> c.real;
        std::cout << "imaginary: ";
        input >> c.imag;
        return input;
    }
};

Complex operator-(double n, Complex &rhs) {
    return Complex(n-rhs.real, -rhs.imag);
}

Complex operator*(double n, Complex &rhs) {
    return Complex(rhs.real * n, rhs.imag * n);
}

#endif
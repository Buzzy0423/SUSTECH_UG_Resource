#include <cstring>
#include <iostream>

struct stringy {
    char *str;
    int ct;
};

void show(std::string s, int n);
void show(stringy s, int n);
void set(stringy &s, char t[]);

void set(stringy &s, char t[]) {
    char *tmp = (char *)malloc(strlen(t) * sizeof(char));
    strcpy(tmp, t);
    s.str = tmp;
    s.ct = strlen(t);
}

void show(std::string s, int n = 1) {
    for (size_t i = 0; i < n; i++) {
        std::cout << s << std::endl;
    }
}

void show(stringy s, int n = 1) {
    for (size_t i = 0; i < n; i++) {
        for (size_t j = 0; j < s.ct; j++) {
            std::cout << *(s.str + j);
        }
        std::cout << std::endl;
    }
}

template <typename T>
inline T Max5(const T arr[]) {
    T tmp = -1;
    for (int i = 0;i < 5;i++) {
        tmp = arr[i] > tmp ? arr[i] : tmp;
    }
    return tmp;
}

int main() {
    stringy beany;
    char testing[] = "Reality isn't what it used to be.";

    set(beany, testing);

    show(beany);
    show(beany, 2);
    testing[0] = 'D';
    testing[1] = 'u';
    show(testing);
    show(testing, 3);
    show("Done!");
    double d[] = {1.1, 2.0, 3.0, 4.0, 5.5};
    int i[] = {1, 2, 3, 4, 5};
    std::cout << "Max int = " << Max5(i) << std::endl;
    std::cout << "Max double = " << Max5(d) << std::endl;
    return 0;
}
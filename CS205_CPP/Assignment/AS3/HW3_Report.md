# CS205 C/ C++ Programming_Assignment3

**Name**: Lizinan

**SID**: 12011517

## Part 1. **Split String**

-   ### **Analysis**

    For struct Pair, I use the simplest single char to store the value. I think there is no need to use pointer or array here.
    
    The printPair function can be easily achieve by using cout.
    
    The splitPair function is a bit confused, the **length of pair array depends on the result of s.size() % 4.**
    
    For example the string "121234" should be {1,1},{2,2} after using function split Pair.
    
    If s.size() % 4 == 2, the length of pair array should be (s.size() / 2) - 1, else the length should be s.size() / 2.
    
    Here I use a ternary operator to simplify the code.
    
    Then I go through the string to add all the possible pair to the array.
    
-   ### Code

```CPP
struct Pair {
    char l, r;
};
```

```CPP
Pair* splitPair(string s, int& length) {
    int len = s.size();
    length = (len % 4 == 2) ? len / 2 - 1 : len / 2;
    Pair* arr = new Pair[length];
    int count = 0;
    for (size_t i = 0; i + 2 < len; i += 4) {
        Pair p;
        p.l = s.at(i);
        p.r = s.at(i + 2);
        *(arr + count++) = p;
        if (i + 3 >= len) {
            break;
        } else {
            Pair p2;
            p2.l = s.at(i + 1);
            p2.r = s.at(i + 3);
            *(arr + count++) = p2;
        }
    }
    return arr;
}

void printPair(Pair* pair) {
    cout << pair->l << " " << pair->r << endl;
}
```

```makefile
CC = clang++
LIB = $(LIB_DIR)/pair.so
TARGET = main
CFLAGES = -std=c++17 -stdlib=libc++ -c -Wall
OBJ = main.cpp 
LIB_DIR = ./lib


$(TARGET) : $(OBJ) $(LIB)
	$(CC) -o $@ -L. $(OBJ) $(LIB)

$(LIB_DIR)/pair.so : pair.cpp
	mkdir -p $(LIB_DIR)
	$(CC) -shared -fPIC -o $(LIB) pair.cpp 

clean:
	rm -f *.o $(TARGET) $(LIB)
	rmdir $(LIB_DIR)
```

-   ### Result & Verification

      <img src="/Users/lee/Library/Application Support/typora-user-images/截屏2022-05-05 20.06.11.png" alt="截屏2022-05-05 20.06.11" style="zoom:50%;" />

-   ### Difficulties & Solutions

    Writing the make file is a bit hard for me, I am not good at writing makefile

## Part 2. 

-   ### **Analysis**

    Using the default arguments we can easily finish task 1&2, setting the default value to 1.

    The const char* and string can't be compared with a >, so the template specialization is needed. 

-   ### Code

```CPP
inline int product(int a, int b, int c = 1, int d = 1, int e = 1) {
    return a * b * c * d * e;
}

inline double product(double a, double b, double c = 1, double d = 1, double e = 1) {
    return a * b * c * d * e;
}

template <typename T>
inline T bigger(T const a, T const b) {
    return a > b ? a : b;
}

template <>
inline const char* bigger<const char*>(const char* a, const char* b) {
    return strlen(a) > strlen(b) ? a : b;
}

template <>
inline std::string bigger<std::string>(const std::string a, const std::string b) {
    return a.length() > b.length() ? a : b;
}
```

```cmake
cmake_minimum_required(VERSION 3.0)

project(bigger_product)

add_executable(main_bigger main_bigger.cpp)

add_executable(main_product main_product.cpp)
```

-   ### Result & Verification

    <img src="/Users/lee/Library/Application Support/typora-user-images/截屏2022-05-05 20.44.44.png" alt="截屏2022-05-05 20.44.44" style="zoom:50%;" />  

-   ### Difficulties & Solutions

    It take me a while to deal with compile erro in OJ because I didn't write

    \#include<cstring>
    
    \#include<string>

## Part 3. 

-   ### **Analysis**

    It's a recursive function, the base case is row == 0, each time return pascal(row - 1, column) + pascal(row - 1, column - 1)

-   ### Code

```CPP
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
```

-   ### Result & Verification

  <img src="/Users/lee/Library/Application Support/typora-user-images/截屏2022-05-06 10.39.57.png" alt="截屏2022-05-06 10.39.57" style="zoom:50%;" />    

-   ### Difficulties & Solutions

    None

## Part 4. 

-   ### **Analysis**

    csv file is well arranged.It is easy to judge whether the country name of each row is "China"(judge the word between the second and third comma). Because I am not sure that whether there exist a city, province or state call China(or contain), I write a function to judge whther the string have "China" between the second and the third comma and avoid the index out of bound error. Therefore the execution logic of the program is to read every lines in the world_cities.csv and judge whther it's in China, if so we write this line to the china_cities.csv. 

    #### How to run

    I write a cmakelists, use cmake to bulid the program. 

-   ### Code

```CPP
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
```

-   ### Result & Verification

    ​    <img src="/Users/lee/Library/Application Support/typora-user-images/截屏2022-05-06 13.04.43.png" alt="截屏2022-05-06 13.04.43" style="zoom:50%;" />

    <img src="/Users/lee/Library/Application Support/typora-user-images/截屏2022-05-06 13.05.04.png" alt="截屏2022-05-06 13.05.04" style="zoom:50%;" />

    Part of the file

-   ### Difficulties & Solutions

    None




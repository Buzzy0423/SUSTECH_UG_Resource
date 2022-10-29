#include <iostream>
typedef unsigned long Item;

class Stack {
   private:
    enum { MAX = 10 };
    Item* pitems;
    int size;
    int top;

   public:
    Stack(int n = MAX) {
        size = n;
        pitems = new Item[size + 1];
        top = -1;
    }
    Stack(const Stack& st) {
        size = st.size;
        pitems = new Item[size + 1];
        top = st.top;
        for (int i = 0; i <= top; i++) {
            *(pitems + i) = *(st.pitems + i);
        }
    }
    ~Stack() {
        delete pitems;
    }
    void print() {
        for (int i = 0; i <= top; i++) {
            std::cout << *(pitems + i) << std::endl;
        }
    }
    bool isempty() const {
        return top == -1;
    };
    bool isfull() const {
        return top == size - 1;
    };
    bool push(const Item& item) {
        if (isfull()) {
            return false;
        }
        top++;
        *(pitems + top) = item;
        return true;
    };
    bool pop(Item& item) {
        if (isempty()) {
            return false;
        }
        item = *(pitems + top);
        top--;
        return true;
    };
    Stack& operator=(const Stack& st) {
        size = st.size;
        pitems = new Item[size + 1];
        top = st.top;
        for (int i = 0; i < top; i++) {
            *(pitems + i) = *(st.pitems + i);
        }
        return *this;
    }
};
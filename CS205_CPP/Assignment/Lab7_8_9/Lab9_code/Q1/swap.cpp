void useless_swap(int a, int b){
    int c = a;
    a = b;
    b = c;
}

void swap(int *a, int *b){
    int c = *a;
    *a = *b;
    *b = c;
}

void swap(int &a, int &b){
    int c = a;
    a = b;
    b = c;
}
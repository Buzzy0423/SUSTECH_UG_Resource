#include <iostream>
#include <queue>
#include <vector>
using namespace std;
struct PC {
    int year;
    char brand[16];
    int value;
};

struct Student {
    int studentID;
    char name[16];
    PC* computer;
};

struct com {
    bool operator()(Student a, Student b) {
        return a.studentID > b.studentID;  //小顶堆
    }
};

struct com2 {
    bool operator()(Student a, Student b) {
        return a.computer->year > b.computer->year;  //大顶堆
    }
};

int main() {
    int n = 0;
    cin >> n;
    PC pc[n + 1];
    priority_queue<Student, vector<Student>, com2> withPC;
    priority_queue<Student, vector<Student>, com> withoutPC;
    for (size_t i = 1; i <= n; i++) {
        cin >> pc[i].year >> pc[i].brand >> pc[i].value;
    }
    for (size_t i = 0; i < n; i++) {
        int p = 0;
        Student tmp;
        cin >> tmp.studentID >> tmp.name >> p;
        if (p > 0 && p < n + 1) {
            tmp.computer = &pc[p];
            withPC.push(tmp);
        } else {
            withoutPC.push(tmp);
        }
    }
    while (!withoutPC.empty()) {
        cout << withoutPC.top().name << " " << withoutPC.top().studentID << endl;
        withoutPC.pop();
    }
    while (!withPC.empty()) {
        cout << withPC.top().name << " " << withPC.top().studentID << " " << withPC.top().computer->year << " " << withPC.top().computer->brand << " " << withPC.top().computer->value << endl;
        withPC.pop();
    }
}
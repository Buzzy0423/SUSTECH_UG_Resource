#include <iostream>
#include <string>

using namespace std;

class Stock {
   private:
    char* company;
    int shares;
    double share_val;
    double total_val;
    void set_tot() {
        total_val = shares * share_val;
    }

   public:
    Stock() {
        company = nullptr;
        shares = 0;
        share_val = 0;
        set_tot();
    }
    Stock(const string& co, long n = 0, double pr = 0.0) {
        company = new char[co.size() + 1];
        strcpy(company, co.c_str());
        shares = n;
        share_val = pr;
    }
    ~Stock() {
        delete company;
    }

    void buy(long num, double price) {
        shares += num;
        update((total_val + num * price) / shares);
        set_tot();
    }

    void sell(long num, double price) {
        shares -= num;
    }

    void update(double price) {
        share_val = price;
    }
    const Stock& topval(const Stock& s) const {
        return *this;
    }

    friend ostream& operator<<(ostream& output, const Stock& s) {
        output << s.company << endl
               << s.shares << endl
               << s.share_val << endl
               << s.total_val << endl;
        return output;
    }
};
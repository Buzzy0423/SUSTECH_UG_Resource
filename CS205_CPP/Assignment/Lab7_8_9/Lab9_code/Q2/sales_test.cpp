#include "sales.cpp"

int main() {
    using namespace std;
    double arr[] = {345.2, 621.8, 1073.5};
    SALES::Sales ss;
    SALES::setSales(ss, arr, 3);
    cout << "Non-interactive version of setSales() to provide values:" << endl;
    SALES::showSales(ss);
    cout << "Interactive version of setSales() to provide values:" << endl;
    SALES::setSales(ss);
    SALES::showSales(ss);
    return 0;
}
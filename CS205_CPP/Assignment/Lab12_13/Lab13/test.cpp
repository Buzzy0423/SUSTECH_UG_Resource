#include "CStereoShape.h"

int CStereoShape::num = 0;

int main(){
    CCube a_cube(4.0, 5.0, 6.0);
    CSphere c_sphere(7.9);
    CStereoShape *p;
    p = &a_cube;
    p->Show();
    p = &c_sphere;
    p->Show();
    cout << "The num of CStereoShape is " << CStereoShape::GetNumOfObject();
}
#include <iostream>

using namespace std;

class CStereoShape {
   private:
    static int num;

   public:
    CStereoShape() {
        num++;
    }
    virtual double GetArea() {
        cout << "CStereoShape::GetArea()" << endl;
        return 0;
    }
    virtual double GetVolume() {
        cout << "CStereoShape::GetVolume()" << endl;
        return 0;
    }
    virtual void Show() {
        cout << "CStereoShape::Show()" << endl;
    }
    static int GetNumOfObject() {
        return num;
    }
};

class CCube : public CStereoShape {
   private:
    double length;
    double height;
    double width;

   public:
    CCube() {
        length = 1;
        height = 1;
        width = 1;
    }
    CCube(double l, double h, double w) {
        length = l;
        height = h;
        width = w;
    }
    CCube(CCube &c) {
        length = c.length;
        height = c.height;
        width = c.width;
    }
    double GetArea() {
        return (2 * width * height) + (2 * width * length) + (2 * length * height);
    }
    double GetVolume() {
        return (width * length * height);
    }
    void Show() {
        cout << "length: " << length << endl;
        cout << "width: " << width << endl;
        cout << "height: " << height << endl;
        cout << "Area: " << GetArea() << endl;
        cout << "Volume: " << GetVolume() << endl;
    }
};

class CSphere : public CStereoShape {
   private:
    double radius;

   public:
    CSphere() {
        radius = 1;
    }
    CSphere(double r) {
        radius = r;
    }
    CSphere(CSphere &s) {
        radius = s.radius;
    }
    double GetArea() {
        return 4 * M_PI * pow(radius, 2);
    }
    double GetVolume() {
        return 4 * M_PI * pow(radius, 3) / 3;
    }
    void Show() {
        cout << "radius: " << radius << endl;
        cout << "Area: " << GetArea() << endl;
        cout << "Volume: " << GetVolume() << endl;
    }
};
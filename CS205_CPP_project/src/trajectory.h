#ifndef SUSTECH_CS205_HCI_TRAJECTORY_H
#define SUSTECH_CS205_HCI_TRAJECTORY_H

#include "opencv2/opencv.hpp"
//#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/dnn.hpp"
#include <iostream>
#include <cmath>
#include <vector>
#include <stdio.h>
#include <unistd.h>

using namespace std;
using namespace cv;
enum objectType {
    Line = 2, Triangle, Rectangle, Pentagon, Hexagon, Heptagon, Circle, Square
};

class Trajectory {
private:
    vector<Point> coordinates;
    vector<Point> outPutContour;
public:
    Trajectory();

    //拟合一个多边形
//    int type_answer =0;
    void matching(int i);

    void mouse_control(int type);

    void append(const Point &p);

    void clear();

    bool empty();

    void draw(const Mat& in);

    void draw_path(Mat in);

    void write(Mat in);

    void removeOutlier(vector<Point> inData, int radius, int k, vector<Point> &outData);
};


#endif //SUSTECH_CS205_HCI_TRAJECTORY_H

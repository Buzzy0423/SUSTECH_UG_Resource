#ifndef CV_BACKGROUD_REMOVAL_H
#define CV_BACKGROUD_REMOVAL_H
#pragma once

#include "opencv2/opencv.hpp"

using namespace cv;
using namespace std;

class Background {
public:
    Background();
    Mat removeKNN(Mat &in);
    void cap(Mat &in);
    Mat remove(Mat &in);
private:
    Mat mask;
    Mat background;
    bool capture;
    Ptr<BackgroundSubtractor> pBackSub;
    Mat getMask(Mat &in);
};

#endif //CV_BACKGROUD_REMOVAL_H

#ifndef CV_SKIN_DETECTOR_H
#define CV_SKIN_DETECTOR_H

#include "opencv2/opencv.hpp"
#include "iostream"

using namespace cv;
using namespace std;

class Skin{
private:
    Rect capture_rect1;
    Rect capture_rect2;
    Scalar high;
    Scalar low;
    bool captured;
    void cal_threshold(const Mat &m1, const Mat &m2);
    Mat get_mask(Mat m);
    void performOpening(Mat binaryImage, int kernelShape, Point kernelSize);
public:
    Skin();
    void draw_rect(Mat &m);
    void capture(Mat &m);
    Mat remove(const Mat &m);
    Mat test(const Mat &m);
};

#endif //CV_SKIN_DETECTOR_H

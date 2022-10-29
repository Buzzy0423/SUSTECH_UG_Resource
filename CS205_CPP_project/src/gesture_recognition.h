#ifndef CV_GESTURE_RECOGNITION_H
#define CV_GESTURE_RECOGNITION_H
#include "opencv2/opencv.hpp"

using namespace std;
using namespace cv;

class Gesture {
private:
    Scalar color_blue;
    Scalar color_green;
    Scalar color_red;
    Scalar color_black;
    Scalar color_white;
    Scalar color_yellow;
    Scalar color_purple;
    static vector<Point> compactOnNeighborhoodMedian(vector<Point> points, double max_neighbor_distance);
    static double findAngle(const Point& a, const Point& b, const Point& c);
    static bool
    isFinger(const Point &a, const Point &b, const Point &c, const Point &palm_center,
             double min_distance_from_palm);
    static double findPointsDistance(const Point& , const Point& );
    static vector<Point> findClosestOnX(vector<Point> points, const Point& pivot);
    static double findPointsDistanceOnX(const Point& a,const Point& b);
    static void drawVectorPoints(Mat image, vector<Point> points, const Scalar& color, bool with_numbers);
public:
    Gesture();
    Point contours(Mat input, Mat frame);
};

#endif //CV_GESTURE_RECOGNITION_H

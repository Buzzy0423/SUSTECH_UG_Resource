#pragma once
#ifndef CV_FACE_REMOVAL_H
#define CV_FACE_REMOVAL_H

#include "opencv2/opencv.hpp"

using namespace std;
using namespace cv;

class Face {
public:
    Face();

    void remove(const Mat &m, Mat &out);

private:
    String faceClassifierFileName = "/Users/lee/GitHub/SUSTech_CS205_HCI/model/haarcascade_frontalface_alt.xml";
    CascadeClassifier faceCascadeClassifier;
    vector<Rect> faces;
};

#endif //CV_FACE_REMOVAL_H

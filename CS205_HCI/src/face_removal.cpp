#include "face_removal.h"

Face::Face() {
    if (!faceCascadeClassifier.load(faceClassifierFileName))
        throw runtime_error("load file error" + faceClassifierFileName);
}

void Face::remove(const Mat& m, Mat &out) {
    Mat frameGray;

    cvtColor(m, frameGray, CV_BGR2GRAY);
    equalizeHist(frameGray, frameGray);

    faceCascadeClassifier.detectMultiScale(frameGray, faces, 1.1, 2, 0 | CV_HAAR_SCALE_IMAGE, Size(120, 120));

    for (auto & face : faces) {
        rectangle(
                out,
                Point(face.x, face.y + 20),
                Point(face.x + face.width, face.y + face.height + 50),
                Scalar(0, 0, 0),
                -1
        );
    }
}

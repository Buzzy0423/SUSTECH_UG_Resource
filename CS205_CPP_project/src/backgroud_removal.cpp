#include "backgroud_removal.h"

Background::Background() {
    pBackSub = createBackgroundSubtractorKNN();
    capture = false;
}

Mat Background::removeKNN(Mat &in) {
    Mat res;
    pBackSub->apply(in, mask, 0.0001);
    in.copyTo(res, mask);
    return res;
}

void Background::cap(Mat &in) {
    cvtColor(in, background, CV_BGR2GRAY);
    capture = true;
}

Mat Background::getMask(Mat &in) {
    Mat foregroundMask;

    if (!capture) {
        foregroundMask = Mat::zeros(in.size(), CV_8UC1);
        return foregroundMask;
    }

    cvtColor(in, foregroundMask, CV_BGR2GRAY);

    int thresholdOffset = 10;

    for (int i = 0; i < foregroundMask.rows; i++) {
        for (int j = 0; j < foregroundMask.cols; j++) {
            uchar framePixel = foregroundMask.at<uchar>(i, j);
            uchar bgPixel = background.at<uchar>(i, j);

            if (framePixel >= bgPixel - thresholdOffset && framePixel <= bgPixel + thresholdOffset)
                foregroundMask.at<uchar>(i, j) = 0;
            else
                foregroundMask.at<uchar>(i, j) = 255;
        }
    }

    return foregroundMask;
}

Mat Background::remove(Mat &in) {
    Mat foregroundMask = getMask(in);
    Mat foreground;
    in.copyTo(foreground, foregroundMask);

    return foreground;
}

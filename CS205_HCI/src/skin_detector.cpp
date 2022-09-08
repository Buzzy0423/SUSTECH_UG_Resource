#include "skin_detector.h"

Skin::Skin() {
    captured = false;
}

void Skin::draw_rect(Mat &m) {
    int width = m.size().width;
    int height = m.size().height;
    int size = 20;
    capture_rect1 = Rect(width / 5, height * 3 / 7, size, size);
    capture_rect2 = Rect(width / 5, height * 4 / 7, size, size);
    rectangle(m, capture_rect1, Scalar(150, 0, 150));
    rectangle(m, capture_rect2, Scalar(150, 0, 150));
}

void Skin::capture(Mat &m) {
    Mat imag_HSV;
    cvtColor(m, imag_HSV, CV_BGR2HSV);
    Mat tmp1 = Mat(imag_HSV, capture_rect1);
    Mat tmp2 = Mat(imag_HSV, capture_rect2);
    cal_threshold(tmp1, tmp2);
    captured = true;
}

void Skin::cal_threshold(const Mat &m1, const Mat &m2) {
    int offset = 80;
    Scalar m_val1 = mean(m1);
    Scalar m_val2 = mean(m2);
    int h_low = min(m_val1[0], m_val2[0]) - offset;
    int h_high = max(m_val1[0], m_val2[0]) + offset;
    int s_low = min(m_val1[1], m_val2[1]) - offset;
    int s_high = max(m_val1[1], m_val2[1]) + offset;
    high = Scalar(h_high, s_high, 255);
    low = Scalar(h_low, s_low, 0);
}

Mat Skin::remove(const Mat &m) {
    Mat hsv, mask;
    cvtColor(m, hsv, CV_BGR2HSV);
    if (captured) {
        mask = get_mask(hsv);
        performOpening(mask, MORPH_ELLIPSE, {3, 3});
        erode(mask, mask, Mat());
        dilate(mask, mask, Mat());
        filter2D(mask, mask, -1, CV_GAUSSIAN);
    } else {
        mask = m.clone();
    }
    return mask;
}

Mat Skin::get_mask(Mat m) {
    Mat mask;
    inRange(m, low, high, mask);
    return mask;
}


void Skin::performOpening(Mat binaryImage, int kernelShape, Point kernelSize) {
    Mat structuringElement = getStructuringElement(kernelShape, kernelSize);
    morphologyEx(binaryImage, binaryImage, MORPH_OPEN, structuringElement);
}

Mat Skin::test(const Mat &m) {
    Mat hsv, gray, ycrcb;
    cvtColor(m, hsv, CV_BGR2HSV);
    cvtColor(m, gray, CV_BGR2GRAY);
    cvtColor(m, ycrcb, CV_BGR2YCrCb);
    Scalar hsv_low = Scalar(0, 0, 0);
    Scalar hsv_high = Scalar(23, 255, 255);
    Scalar ycrcb_low = Scalar(0, 135, 94);
    Scalar ycrcb_high = Scalar(255, 170, 125);
    Mat hsv_mask, ycrcb_mask;
    inRange(hsv, hsv_low, hsv_high, hsv_mask);
    inRange(ycrcb, ycrcb_low, ycrcb_high, ycrcb_mask);
    Mat res;
    bitwise_and(hsv_mask, ycrcb_mask, res);
    filter2D(res, res, -1, CV_GAUSSIAN);
    erode(res, res, Mat(), Point(1,1), 4);
    dilate(res, res, Mat(), Point(1,1), 1);
    return res;
}

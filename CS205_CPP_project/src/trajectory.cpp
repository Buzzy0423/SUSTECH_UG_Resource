//
// Created by 革 on 2022/5/17.
//

#include "trajectory.h"

void Trajectory::removeOutlier(vector<Point> inData, int radius, int k, vector<Point> &outData) {
    outData.clear();
    int cnt = 0;
    int n = 0;
    for (int m = 0; m < inData.size(); m++) {
        cnt = 0;
        for (n = 0; n < inData.size(); n++) {
            if (n == m)
                continue;
            if (sqrt(pow(inData[m].x - inData[n].x, 2) + pow(inData[m].y - inData[n].y, 2)) <= radius) {
                cnt++;
                if (cnt >= k) {
                    outData.push_back(inData[m]);
                    n = 0;
                    break;
                }
            }
        }
    }
}

void Trajectory::matching(int i) {
    //角点向量，仅存储轮廓中的角点
    vector<vector<Point>> contours;
    vector<Point> contour;
//    Mat img = Mat::zeros(10000, 2000, CV_8UC3);
    //! img initialization?
    Mat imgDrawn = Mat::zeros(1000, 1000, CV_8UC3);
    vector<Point> coordinates0 = coordinates;
    removeOutlier(coordinates0, 30, 2, coordinates);
    Mat mat = Mat(coordinates);

    for (int i = 0; i < coordinates.size() - 1; ++i) {
        line(imgDrawn, coordinates[i], coordinates[i + 1], Scalar(0, 0, 255), 3);
    }
    line(imgDrawn, coordinates[coordinates.size() - 1], coordinates[0], Scalar(0, 0, 255), 3);

//    imshow("test", imgDrawn);
//    waitKey(0);

    cvtColor(imgDrawn, imgDrawn, COLOR_BGR2GRAY);
    Mat imgBlur;
    GaussianBlur(imgDrawn, imgBlur, Size(3, 3), 3, 0);
    //3.进行坎尼边缘检测
    Mat imgCanny;
    Canny(imgBlur, imgCanny, 25, 75);
    //4.进行膨胀操作
    Mat imgDil;
    Mat kernel = getStructuringElement(MORPH_RECT, Size(3, 3));
    dilate(imgCanny, imgDil, kernel);

    //! imgDrawn is the img converted from our given points
    findContours(imgDil, contours, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

    //找到每个轮廓的周长
    //! Here the contour should be the founded contour after calling function: findContour
    //! We have only one contour to judge each time after we draw(?)
    //! 判断面积去除噪点？
//    vector<Point> outPutContour;
    double perimeter = arcLength(contours[0], true);
    approxPolyDP(contours[0], outPutContour, 0.025 * perimeter, true);
    //定义每个轮廓的角点数
    for (auto &j: outPutContour) {
        cout << j << endl;
    }
    int objCor = (int) outPutContour.size();
//    imshow("test", imgBlur);
//    waitKey(0);
    //绘制矩形边界框，将含纳每一个独立的形状
    Rect boundRect;
    boundRect = boundingRect(outPutContour);
    //将边界框打印在原图上
    rectangle(imgDrawn, boundRect.tl(), boundRect.br(), Scalar(0, 255, 255));
    //定义轮廓对象的名称
//    imshow("test", imgDil);
//    waitKey(10);
    string objectType;
    //三角形判断
    cout << objCor << endl;
}

Trajectory::Trajectory() = default;

void Trajectory::mouse_control(int type) {
    if (type == 2) {

    } else if (type == 3) {

    } else if (type == 4) {

    } else {

    }
}

void Trajectory::append(const Point &now_point) {
    coordinates.emplace_back(now_point);
}

void Trajectory::clear() {
    coordinates.clear();
}

bool Trajectory::empty() {
    return coordinates.empty();
}

void Trajectory::draw_path(Mat in) {
    for (const Point &p: coordinates) {
        circle(in, p, 4, Scalar(255, 230, 255), -1);
    }
}


void Trajectory::draw(const Mat &in) {
    Rect boundRect;
    boundRect = boundingRect(outPutContour);
    int objCor = outPutContour.size();
    String objectType;
    if (objCor == 2) {
        objectType = "Line";
    }
    if (objCor == 3) {
        objectType = "Triangle";
    }
    //矩形判断
    if (objCor == 4) {
//        定义长宽比
        float aspectRatio = (float) boundRect.width /
                            (float) boundRect.height;
        cout << "长宽比" << aspectRatio << endl;
//        正方形判断
        if (aspectRatio > 0.95 && aspectRatio < 1.05) {
            objectType = "Square";
        }
//            不然即是矩形
        else {
            objectType = "Rectangle";

        }
        objectType = "Quadrangle";
    }
    //圆形判断
    if (objCor == 5) {
        objectType = "Pentagon";
    }
    if (objCor == 6) {
        objectType = "Hexagon";
    }
    if (objCor == 7) {
        objectType = "Heptagon";
    }
    if (objCor > 7) {
        objectType = "Circle";
    }
    cout << objCor << endl;
//    cout << objectType << endl;
    Mat t(2720, 2780, CV_8UC3);
    for (int i = 0; i < t.rows; ++i) {
        for (int j = 0; j < t.cols; ++j) {
            cv::Vec3b pixel;
            // 注意：opencv通道顺序为BGR
            pixel[0] = 255;
            pixel[1] = 255;
            pixel[2] = 255;
            t.at<Vec3b>(i, j) = pixel;
        }
    }
    switch (objCor) {
        case 0:
            break;
        case 1:
            break;
        case 2:
            polylines(in, outPutContour, true, Scalar(160, 102, 211), 10, LINE_8);
            break;
        case 3:
            polylines(in, outPutContour, true, Scalar(160, 102, 211), 10, LINE_8);
            break;
        case 4:
            polylines(in, outPutContour, true, Scalar(160, 102, 211), 10, LINE_8);
            break;
        case 5:
            polylines(in, outPutContour, true, Scalar(160, 102, 211), 10, LINE_8);
            break;
        case 6:
            polylines(in, outPutContour, true, Scalar(160, 102, 211), 10, LINE_8);
            break;
        case 7:
            polylines(in, outPutContour, true, Scalar(160, 102, 211), 10, LINE_8);
            break;
        default:

            double x1 = outPutContour[0].x;
            double y1 = outPutContour[0].y;
            double x2 = outPutContour[2].x;
            double y2 = outPutContour[2].y;
            double x3 = outPutContour[5].x;
            double y3 = outPutContour[5].y;

            double x1x1 = x1 * x1;
            double y1y1 = y1 * y1;
            double x2x2 = x2 * x2;
            double y2y2 = y2 * y2;
            double x3x3 = x3 * x3;
            double y3y3 = y3 * y3;

            double x2y3 = x2 * y3;
            double x3y2 = x3 * y2;

            double x2_x3 = x2 - x3;
            double y2_y3 = y2 - y3;

            double x1x1py1y1 = x1x1 + y1y1;
            double x2x2py2y2 = x2x2 + y2y2;
            double x3x3py3y3 = x3x3 + y3y3;

            double A = x1 * y2_y3 - y1 * x2_x3 + x2y3 - x3y2;
            double B = x1x1py1y1 * (-y2_y3) + x2x2py2y2 * (y1 - y3) + x3x3py3y3 * (y2 - y1);
            double C = x1x1py1y1 * x2_x3 + x2x2py2y2 * (x3 - x1) + x3x3py3y3 * (x1 - x2);
            double D = x1x1py1y1 * (x3y2 - x2y3) + x2x2py2y2 * (x1 * y3 - x3 * y1) + x3x3py3y3 * (x2 * y1 - x1 * y2);

            double x = -B / (2.0 * A);
            double y = -C / (2.0 * A);
            double r = sqrt((B * B + C * C - 4 * A * D) / (4 * A * A));

            cout << r << " " << x << " " << y << endl;
            circle(in, Point((int) x, (int) y), (int) r, Scalar(160, 102, 211), 10, LINE_8);
            break;

    }
    putText(in, "Image Recognition: ", Point(30, 50), FONT_HERSHEY_COMPLEX, 1, Scalar(255, 102, 102), 2);
    putText(in, objectType, Point(30, 100), FONT_HERSHEY_COMPLEX, 1, Scalar(255, 102, 102), 2);

}

void Trajectory::write(Mat in) {
    int objCor = outPutContour.size();
    if (objCor > 7) {
        if (access("/Users/lee/GitHub/SUSTech_CS205_HCI/capture/captured.jpg", 0) < 0) {
            imwrite("/Users/lee/GitHub/SUSTech_CS205_HCI/capture/captured.jpg", in);
        }
    } else if (objCor == 3) {
        if (access("/Users/lee/GitHub/SUSTech_CS205_HCI/capture/captured_gray.jpg", 0) < 0) {
            Mat gray;
            cvtColor(in, gray, CV_BGR2GRAY);
            imwrite("/Users/lee/GitHub/SUSTech_CS205_HCI/capture/captured_gray.jpg", gray);
        }
    } else if (objCor == 4) {
        if (access("/Users/lee/GitHub/SUSTech_CS205_HCI/capture/captured_erode.jpg", 0) < 0) {
            Mat ero;
            erode(in, ero, Mat(), Point(1, 1), 50);
            imwrite("/Users/lee/GitHub/SUSTech_CS205_HCI/capture/captured_erode.jpg", ero);
        }
    }
}
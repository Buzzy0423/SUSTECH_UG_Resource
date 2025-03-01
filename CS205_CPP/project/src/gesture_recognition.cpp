#include "gesture_recognition.h"

#define LIMIT_ANGLE_SUP 60
#define LIMIT_ANGLE_INF 5
#define BOUNDING_RECT_FINGER_SIZE_SCALING 0.3
#define BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING 0.05

Gesture::Gesture() {
    color_blue = Scalar(255, 0, 0);
    color_green = Scalar(0, 255, 0);
    color_red = Scalar(0, 0, 255);
    color_yellow = Scalar(0, 255, 255);
    color_purple = Scalar(255, 0, 255);
}


Point Gesture::contours(Mat input, Mat frame) {
    Mat img = Mat::zeros(input.size(), CV_8UC3);
    //input
    if (input.empty() || input.channels() != 1) {
        return {0, 0};
    }

    vector<vector<Point>> contours;
    vector<Vec4i> hierarchy;

    findContours(input, contours, hierarchy, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_NONE);


    if (contours.empty()) {
        return {0, 0};
    }

    int max_index = -1;
    double max_area = 0;

    //Suppose the biggest area is the hand
    for (int i = 0; i < contours.size(); ++i) {
        double area = contourArea(contours[i], false);
        if (area > max_area) {
            max_index = i;
            max_area = area;
        }
    }

    vector<Point> hull;
    vector<int> hull_ints;

    convexHull(Mat(contours[max_index]), hull, true);

    convexHull(Mat(contours[max_index]), hull_ints, false);

    vector<Vec4i> defects;
    if (hull_ints.size() > 3) {
        convexityDefects(Mat(contours[max_index]), hull_ints, defects);
    } else {
        return {0, 0};
    }

    Rect hand_rec = boundingRect(Mat(hull));

    Point hand_co((hand_rec.tl().x + hand_rec.br().x) / 2, (hand_rec.tl().y + hand_rec.br().y) / 2);

    Point hand((hand_rec.tl().x + hand_rec.br().x) / 2, hand_rec.tl().y + (hand_rec.br().x - hand_rec.tl().x) / 2);

    vector<Point> start_points;
    vector<Point> far_points;

    for (auto &defect: defects) {
        start_points.push_back(contours[max_index][defect.val[0]]);

        if (norm(contours[max_index][defect.val[2]] - hand_co) <
            hand_rec.height * BOUNDING_RECT_FINGER_SIZE_SCALING) {
            far_points.push_back(contours[max_index][defect.val[2]]);
        }
    }

    vector<Point> filtered_start_points = compactOnNeighborhoodMedian(start_points, hand_rec.height *
                                                                                    BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING);
    vector<Point> filtered_far_points = compactOnNeighborhoodMedian(far_points, hand_rec.height *
                                                                                BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING);

    // now we try to find the fingers
    vector<Point> filtered_finger_points;

    if (filtered_far_points.size() > 1) {
        vector<Point> finger_points;

        for (auto &filtered_start_point: filtered_start_points) {
            vector<Point> closest_points = findClosestOnX(filtered_far_points, filtered_start_point);

            if (isFinger(closest_points[0], filtered_start_point, closest_points[1], hand_co,
                         hand_rec.height * BOUNDING_RECT_FINGER_SIZE_SCALING))
                finger_points.push_back(filtered_start_point);
        }

        if (!finger_points.empty()) {

            // we have at most five fingers usually :)
            while (finger_points.size() > 5)
                finger_points.pop_back();

            // filter out the points too close to each other
            for (int i = 0; i < finger_points.size() - 1; i++) {
                if (findPointsDistanceOnX(finger_points[i], finger_points[i + 1]) >
                    hand_rec.height * BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING * 1.5)
                    filtered_finger_points.push_back(finger_points[i]);
            }

            if (finger_points.size() > 2) {
                if (findPointsDistanceOnX(finger_points[0], finger_points[finger_points.size() - 1]) >
                    hand_rec.height * BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING * 1.5)
                    filtered_finger_points.push_back(finger_points[finger_points.size() - 1]);
            } else
                filtered_finger_points.push_back(finger_points[finger_points.size() - 1]);
        }

        if (!finger_points.empty()) {
            
        }
    }
    // we draw what found on the returned image
    drawContours(img, contours, max_index, color_green, 2, 8, hierarchy);
    polylines(img, hull, true, color_blue);
    rectangle(img, hand_rec.tl(), hand_rec.br(), color_red, 2, 8, 0);
    circle(img, hand_co, 5, color_purple, 2, 8);
    drawVectorPoints(img, filtered_start_points, color_blue, true);
    drawVectorPoints(img, filtered_far_points, color_red, true);
    drawVectorPoints(img, filtered_finger_points, color_yellow, false);
    putText(img, to_string(filtered_finger_points.size()), hand_co, FONT_HERSHEY_PLAIN, 3, color_purple);

    // and on the starting frame
    drawContours(frame, contours, max_index, color_green, 2, 8, hierarchy);
    rectangle(frame, hand_rec.tl(), hand_rec.br(), color_red, 2, 8, 0);
    circle(frame, hand, 5, color_purple, 2, 8);
    drawVectorPoints(frame, filtered_finger_points, color_yellow, false);
    putText(frame, to_string(filtered_finger_points.size()), hand_co, FONT_HERSHEY_PLAIN, 3, color_purple);

    return hand;
}

vector<Point> Gesture::compactOnNeighborhoodMedian(vector<Point> points, double max_neighbor_distance) {
    vector<Point> median_points;

    if (points.empty() || max_neighbor_distance <= 0) {
        return median_points;
    }

    // we start with the first point
    Point reference = points[0];
    Point median = points[0];

    for (int i = 1; i < points.size(); i++) {
        if (norm(reference - points[i]) > max_neighbor_distance) {
            // the point is not in range, we save the median
            median_points.push_back(median);
            // we swap the reference
            reference = points[i];
            median = points[i];
        } else
            median = (points[i] + median) / 2;
    }

    // last median
    median_points.push_back(median);

    return median_points;
}

double Gesture::findPointsDistance(const Point &a, const Point &b) {
    return norm(a - b);
}

double Gesture::findAngle(const Point &a, const Point &b, const Point &c) {
    double ab = findPointsDistance(a, b);
    double bc = findPointsDistance(b, c);
    double ac = findPointsDistance(a, c);
    return acos((ab * ab + bc * bc - ac * ac) / (2 * ab * bc)) * 180 / CV_PI;
}

bool Gesture::isFinger(const Point &a, const Point &b, const Point &c, const Point &palm_center,
                       double min_distance_from_palm) {
    double angle = findAngle(a, b, c);
    if (angle > 60 || angle < 5)
        return false;

    // the finger point sohould not be under the two far points
    int delta_y_1 = b.y - a.y;
    int delta_y_2 = b.y - c.y;
    if (delta_y_1 > 0 && delta_y_2 > 0)
        return false;

    // the two far points should not be both under the center of the hand
    int delta_y_3 = palm_center.y - a.y;
    int delta_y_4 = palm_center.y - c.y;
    if (delta_y_3 < 0 && delta_y_4 < 0)
        return false;

    double distance_from_palm = findPointsDistance(b, palm_center);
    if (distance_from_palm < min_distance_from_palm)
        return false;

    // this should be the case when no fingers are up
    double distance_from_palm_far_1 = findPointsDistance(a, palm_center);
    double distance_from_palm_far_2 = findPointsDistance(c, palm_center);
    if (distance_from_palm_far_1 < min_distance_from_palm / 4 || distance_from_palm_far_2 < min_distance_from_palm / 4)
        return false;

    return true;
}

vector<Point> Gesture::findClosestOnX(vector<Point> points, const Point &pivot) {
    vector<Point> to_return(2);

    if (points.empty())
        return to_return;

    double distance_x_1 = DBL_MAX;
    double distance_1 = DBL_MAX;
    double distance_x_2 = DBL_MAX;
    double distance_2 = DBL_MAX;
    int index_found = 0;

    for (int i = 0; i < points.size(); i++) {
        double distance_x = findPointsDistanceOnX(pivot, points[i]);
        double distance = findPointsDistance(pivot, points[i]);

        if (distance_x < distance_x_1 && distance_x != 0 && distance <= distance_1) {
            distance_x_1 = distance_x;
            distance_1 = distance;
            index_found = i;
        }
    }

    to_return[0] = points[index_found];

    for (int i = 0; i < points.size(); i++) {
        double distance_x = findPointsDistanceOnX(pivot, points[i]);
        double distance = findPointsDistance(pivot, points[i]);

        if (distance_x < distance_x_2 && distance_x != 0 && distance <= distance_2 && distance_x != distance_x_1) {
            distance_x_2 = distance_x;
            distance_2 = distance;
            index_found = i;
        }
    }

    to_return[1] = points[index_found];

    return to_return;
}

double Gesture::findPointsDistanceOnX(const Point &a, const Point &b) {
    double to_return = 0.0;

    if (a.x > b.x)
        to_return = a.x - b.x;
    else
        to_return = b.x - a.x;

    return to_return;
}

void Gesture::drawVectorPoints(Mat image, vector<Point> points, const Scalar &color, bool with_numbers) {
    for (int i = 0; i < points.size(); i++) {
        circle(image, points[i], 5, color, 2, 8);
        if (with_numbers)
            putText(image, to_string(i), points[i], FONT_HERSHEY_PLAIN, 3, color);
    }
}


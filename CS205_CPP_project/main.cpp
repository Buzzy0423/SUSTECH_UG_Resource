#include "iostream"
#include "src/gesture_recognition.h"
#include "src/backgroud_removal.h"
#include "src/face_removal.h"
#include "src/skin_detector.h"
#include "src/trajectory.h"

int main() {
    VideoCapture vid_capture(0);
    vid_capture.set(CV_CAP_PROP_FOURCC, CV_FOURCC('M', 'J', 'P', 'G'));

    Face face;
    Background back;
    Skin skin;
    Gesture gesture;
    Trajectory trajectory;

    bool tracking = false;
    bool match = false;
    int type = 0;

    while (vid_capture.isOpened()) {
        // Initialize frame matrix
        Mat frame;
        // Initialize a boolean to check if frames are there or not
        bool isSuccess = vid_capture.read(frame);
        // If frames are not there, close it
        if (!isSuccess) {
            std::cout << "Stream disconnected" << std::endl;
            break;
        } else {
            flip(frame, frame, 1);
            //skin.draw_rect(frame);
            Mat foreground = back.removeKNN(frame);
            face.remove(frame, foreground);
            Mat mask = skin.test(foreground);
            cv::Point hand_co = gesture.contours(mask, frame);
            //imshow("foreground", foreground);
            //imshow("skin_test", mask);
            if (tracking && !match) {
                trajectory.append(hand_co);
            }

            if(!match){
                trajectory.draw_path(frame);
            }

            if(!tracking && match){
                if(!trajectory.empty()){
                    trajectory.matching(type);
                    trajectory.draw(frame);
                    trajectory.write(frame);
                }
            }

            imshow("result", frame);

            int key = waitKey(1);
            if (key == 27) { break; }   //esc to quit
            if (key == 32) {    //blankspace to start tracking
                tracking = !tracking;
                match = false;
                if(tracking){
                    trajectory.clear();
                }
            }

            if (key == 13){ //enter to matching
                match = true;
            }

            //if (key == 115) { skin.capture(frame); }// press 's'
            //if (key == 98) {back.cap(frame);} // press 'b'
        }
    }
    vid_capture.release();
}
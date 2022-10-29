cmake_minimum_required(VERSION 3.19)
project(cv)

set(CMAKE_CXX_STANDARD 17)

find_package(OpenCV REQUIRED)

include_directories(${OpenCV_INCLUDE_DIRS})

#add_executable(cv main.cpp  src/backgroud_removal.cpp src/backgroud_removal.h src/skin_detector.cpp src/skin_detector.h src/gesture_recognition.cpp src/gesture_recognition.h src/trajectory.cpp src/trajectory.h)
add_executable(cv main.cpp src/backgroud_removal.cpp src/face_removal.cpp src/skin_detector.cpp src/gesture_recognition.cpp src/trajectory.cpp)
target_link_libraries(${PROJECT_NAME} ${OpenCV_LIBS})
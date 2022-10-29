cmake_minimum_required(VERSION 3.20)
project(AS2)

set(CMAKE_CXX_STANDARD 14)

include_directories(code)

add_executable(AS2
        code/assign2.cpp
        code/assign2.hpp
        code/assign2_exception.hpp
        code/assign2_tree.hpp
        code/test.cpp)

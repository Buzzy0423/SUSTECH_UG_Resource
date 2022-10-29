cmake_minimum_required(VERSION 3.20)
project(AS1 C)

set(CMAKE_C_STANDARD 11)
set(CMAKE_SYSROOT "${SYSROOT_PATH}")
set(CMAKE_FIND_ROOT_PATH "${SYSROOT_PATH}" "${CMAKE_PREFIX_PATH}" "${TOOLCHAIN_PATH}")

include_directories(code)


add_executable(AS1
        code/assign1.h
        code/assign1_mat.c
        code/assign1_mat.h)

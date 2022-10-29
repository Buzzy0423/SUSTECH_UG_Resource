cmake_minimum_required(VERSION 3.0)

project(CardGame)

set(CMAKE_CXX_STANDARD 17)

aux_source_directory(. DIR_SRC)

add_executable(main ${DIR_SRC})

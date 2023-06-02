cmake_minimum_required(VERSION 3.18.1)
project("app")

include(define.cmake)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SRC_LIST
        ${CMAKE_CURRENT_SOURCE_DIR}/stools.cpp
)

add_library(stools SHARED ${SRC_LIST})

find_library(log-lib log)
find_library(android-lib android)

target_link_libraries(
    stools
    ${log-lib}
    ${android-lib}
)
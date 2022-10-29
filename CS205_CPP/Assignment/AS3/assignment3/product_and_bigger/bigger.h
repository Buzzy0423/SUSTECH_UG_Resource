#pragma once
#include<cstring>
#include<string>

template <typename T>
inline T bigger(T const a, T const b) {
    return a > b ? a : b;
}

template <>
inline const char* bigger<const char*>(const char* a, const char* b) {
    return strlen(a) > strlen(b) ? a : b;
}

template <>
inline std::string bigger<std::string>(const std::string a, const std::string b) {
    return a.length() > b.length() ? a : b;
}
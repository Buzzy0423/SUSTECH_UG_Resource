#include "Random.h"

Random::Random() {
    struct timespec tn;
    seed = (seedUniquifier() ^ clock_gettime(CLOCK_REALTIME, &tn));  // nanotime 10e9
}

Random::Random(int64_t seed) {
    this->seed = seed;
}

Random::~Random() {
}

int64_t Random::getModule() {
    return module;
}

int64_t Random::seedUniquifier() {
    return aug * aug2;
}

void Random::setSeed(int64_t seed) {
    this->seed = seed;
}

long long Random::getSeed() {
    return this->seed;
}

int64_t Random::next(int bit = 48) {  // random number in [0, 2^48 - 1]
    this->seed = (this->seed * multiplier + addend) & module;
    return this->seed >> (48 - bit);
}

int Random::nextInt() {
    return this->next(32);
}

int Random::nextInt(int n) {// random number in [0, n)
    return floor(((double)this->next() / module) * n);
}

int64_t Random::nextLong() {
    return (this->next(32) << 32 | this->next(32));
}

float Random::nextFloat() {
    return this->next(24) / ((float)(1 << 24));
}

double Random::nextDouble() {
    return (((long)(this->next(26)) << 27) + this->next(27)) * double_unit;
}

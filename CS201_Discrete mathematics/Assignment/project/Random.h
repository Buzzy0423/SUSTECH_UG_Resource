#include <ctime>
#include <climits>
#include <cmath>

class Random
{
private:
    int64_t seed;
    int64_t module = (1L << 48) - 1;
    int64_t multiplier = 0x5DEECE66DL;
    int64_t addend = 0xBL;
    int64_t aug = 1181783497276652981L;
    int64_t aug2 = 8682522807148012L;
    double double_unit = 0x1.0p-53;
    long long seedUniquifier();
public:
    Random();
    Random(int64_t seed);
    ~Random();
    int64_t getModule();
    void setSeed(int64_t seed);
    int64_t getSeed();
    int64_t next(int bit);
    int32_t nextInt();
    int32_t nextInt(int32_t n);
    int64_t nextLong();
    float nextFloat();
    double nextDouble();
};
#include "Random.h"


class Red_Envelope {
   private:
    int money;
    Random r;

   public:
    Red_Envelope(int m) {
        this->money = m;
    }

    ~Red_Envelope() {
    }

    int get_money() {
        return this->money;
    }

    int get_money_from_red_envelope() {
        if (money == 0) {
            return -1;
        }   
        int m = this->r.nextInt(this->money);
        money -= m;
        return m;
    }
};
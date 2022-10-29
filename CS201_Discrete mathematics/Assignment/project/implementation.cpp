#include <chrono>
#include <fstream>
#include <iostream>
#include "Red_Envelope.h"

using namespace std;

char* what_jiaran_eat_today();

int main() {
    Red_Envelope red = Red_Envelope(10);
    while (1) {
        int i = red.get_money_from_red_envelope();
        cout << i << endl;
        if (red.get_money() == 0) {
            break;
        }
    }
    return 0;
}

// char* what_jiaran_eat_today() {
//     static Random r;
//     switch (r.nextInt(10)) {
//         case 0:
//             return "fried chicken";
//             break;
//         case 1:
//             return "humberger";
//             break;
//         case 2:
//             return "pizza";
//             break;
//         case 3:
//             return "sushi";
//             break;
//         case 4:
//             return "steak";
//             break;
//         case 5:
//             return "roast beef";
//             break;
//         case 6:
//             return "noodles";
//             break;
//         case 7:
//             return "hot pot";
//             break;
//         case 8:
//             return "oat meal";
//             break;
//         case 9:
//             return "too fat to eat :(";
//             break;
//         default:
//             break;
//     }
// }


#include "card.h"
#include "player.h"

using namespace std;

class BigBossCard : public Card {
   public:
    BigBossCard(string name, int attack, int defense);
    void effect(Card& oppenentCard, Player& player, Player& opponent);
};
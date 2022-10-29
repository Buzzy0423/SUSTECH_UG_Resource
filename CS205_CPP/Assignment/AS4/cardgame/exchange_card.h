#include "card.h"
#include "player.h"

using namespace std;

class ExchangeCard : public Card{
    public:
    ExchangeCard(string name, int attack, int defense);
    void effect(Card& oppenentCard, Player& player, Player& opponent) override;
};
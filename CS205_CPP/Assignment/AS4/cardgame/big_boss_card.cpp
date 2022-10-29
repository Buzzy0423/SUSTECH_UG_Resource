#include "big_boss_card.h"
#include "player.h"

BigBossCard::BigBossCard(string name, int attack, int defense): Card(name, attack, defense) {}

void BigBossCard::effect(Card& oppenentCard, Player& player, Player& opponent) {
    for (Card& c : player.deck) {
        c.attack += oppenentCard.attack;
        c.defense += oppenentCard.defense;
    }
    for (int i = opponent.deck.size() - 1; i >-1; i--) {
        if (opponent.deck[i].attack == oppenentCard.attack || opponent.deck[i].defense == oppenentCard.defense) {
            opponent.deck.erase(opponent.deck.begin() + i);
        }
    }
}
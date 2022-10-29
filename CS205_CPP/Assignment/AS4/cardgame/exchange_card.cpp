#include "exchange_card.h"

ExchangeCard::ExchangeCard(string name, int attack, int defense): Card(name, attack, defense) {}

void ExchangeCard::effect(Card& oppenentCard, Player& player, Player& opponent){
    int tmp = oppenentCard.attack;
    oppenentCard.attack = oppenentCard.defense;
    oppenentCard.defense = tmp;
}
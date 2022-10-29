#include "card.h"

using namespace std;

Card::Card(string name, int attack, int defense){
    this->attack = attack;
    this->defense = defense;
    this->name = name;
}

void Card::effect(Card& oppenentCard, Player& player, Player& opponent){}

double Card::power(Card opponentCard){
    return this->attack - opponentCard.defense / 2;
}

ostream& operator<<(ostream& os, const Card& card){
    os << card.name << " " << card.attack << " " << card.defense << endl;
    return os;
}
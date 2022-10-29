#include "player.h"

using namespace std;

Player::Player(vector<Card>& deck, string name) {
    this->deck = deck;
    this->name = name;
    hand = {};
    for (int i = 0; i < 5; ++i) {
        draw();
    }
}

void Player::draw() {
    this->hand.push_back(*deck.begin());
    this->deck.erase(deck.begin());
}

Card Player::play(int index) {
    Card tmp = this->hand[index];
    this->hand.erase(hand.begin() + index);
    return tmp;
}

void Player::displayHand() {
    for (Card c : hand) {
        cout << c;
    }
}
package blackjack;

import java.util.ArrayList;

public class Player {

    private ArrayList<Hand> hands;

    public Player() {
        this.hands = new ArrayList<>();
    }

    public ArrayList<Hand> getHands() {
        return hands;
    }

    public void addHand(Hand hand) {
        this.hands.add(hand);
    }
}

package blackjack;

import java.util.ArrayList;

public class Player {

    private final ArrayList<Hand> hands;

    public Player() {
        this.hands = new ArrayList<>();

        addHand(new Hand());
    }

    public ArrayList<Hand> getHands() {
        return hands;
    }

    /**
     * Adds a hand
     *
     * @param hand The hand to add
     */
    public void addHand(Hand hand) {
        this.hands.add(hand);
    }

    /**
     * If a hand is bust. A hand is bust
     * if its value is greater than 21
     *
     * @param hand The hand
     * @return If a hand is bust
     */
    public boolean handBust(Hand hand) {
        return hand.value() > 21;
    }
}

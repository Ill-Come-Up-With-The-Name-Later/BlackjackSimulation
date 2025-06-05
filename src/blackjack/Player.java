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
     * Splits the hand into two
     *
     * @param hand The hand to split
     */
    public void splitHand(Hand hand) {
        if(hand.canSplit()) {
            Card card1 = hand.getCards().get(0);
            Card card2 = hand.getCards().get(1);

            Hand hand1 = new Hand();
            hand1.addCard(card1);

            Hand hand2 = new Hand();
            hand2.addCard(card2);

            hands.remove(hand);

            hands.add(hand1);
            hands.add(hand2);
        } else {
            System.out.println("Cannot split hand.");
        }
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

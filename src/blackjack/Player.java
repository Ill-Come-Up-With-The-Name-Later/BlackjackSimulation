package blackjack;

import java.util.ArrayList;

public class Player {

    private final ArrayList<Hand> hands;
    private PlayerStatus status;
    private String name;
    private boolean isDealer;

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.status = PlayerStatus.IN;
        this.isDealer = false;

        addHand(new Hand());
    }

    /**
     * Deals a card to another player
     */
    public void dealCard(Player other, int handIndex, Deck deck, boolean showCard) {
        Card card = deck.drawCard();
        card.setShow(showCard);

        other.getHand(handIndex).addCard(card);
    }

    /**
     * Gets the player's hands
     *
     * @return The player's hands
     */
    public ArrayList<Hand> getHands() {
        return hands;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }

    /**
     * Gets the hand at an index
     *
     * @param index The index
     * @return The hand at index
     */
    public Hand getHand(int index) {
        return this.hands.get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
     * Takes the first card from a deck
     *
     * @param hand The hand to add the card to
     * @param deck The deck to take the card from
     */
    public void hit(Hand hand, Deck deck) {
        hand.addCard(deck.drawCard());
        this.setStatus(PlayerStatus.HIT);
    }

    /**
     * Stands and ends the turn
     */
    public void stand() {
        this.setStatus(PlayerStatus.STAND);
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

    /**
     * True if all hands are bust
     *
     * @return If all hands are bust
     */
    public boolean bust() {
        for(Hand hand : this.hands) {
            if(!this.handBust(hand)) {
                return false;
            }
        }

        return true;
    }

    /**
     * If the player has not stood or gone bust
     *
     * @return If the player is still in the game
     */
    public boolean isActive() {
        return !(this.getStatus() == PlayerStatus.BUST || this.getStatus() == PlayerStatus.STAND);
    }

    /**
     * Sets the player's status to BUST
     */
    public void setBust() {
        this.setStatus(PlayerStatus.BUST);
    }

    public PlayerStatus getStatus() {
        return status;
    }

    private void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**
     * Doubles down. Adds one card to the hand
     * and then stands
     *
     * @param hand The hand to double down
     * @param deck The deck to draw a card from
     */
    public void doubleDown(Hand hand, Deck deck) {
        this.hit(hand, deck);
        this.stand();
    }

    @Override
    public String toString() {
        if(isDealer()) {
            for(Hand hand : hands) {
                hand.setShowValue(false);
            }
        }

        return name + ": " + hands + " | Status: " + status;
    }
}

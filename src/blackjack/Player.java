package blackjack;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private final ArrayList<Hand> hands;
    private PlayerStatus status;
    private String name;
    private boolean isDealer;
    private final HashMap<Hand, Boolean> handBust;

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.status = PlayerStatus.IN;
        this.isDealer = false;
        this.handBust = new HashMap<>();

        Hand hand = new Hand();
        handBust.put(hand, false);
        addHand(hand);
    }

    public Player(String name, boolean isDealer) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.status = PlayerStatus.IN;
        this.isDealer = isDealer;
        this.handBust = new HashMap<>();

        Hand hand = new Hand();
        handBust.put(hand, false);
        addHand(hand);
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
        if(isDealer) {
            return;
        }

        if(hand.canSplit()) {
            Card card2 = hand.getCards().get(1);

            Hand hand2 = new Hand();
            hand2.addCard(card2);

            hand.getCards().remove(1);

            hands.add(hand2);

            this.setStatus(PlayerStatus.SPLIT);
        } else {
            System.out.println("Cannot split hand.");
        }
    }

    /**
     * Takes the first card from a deck
     *
     * @param manager The game manager
     * @param hand The hand to add the card to
     */
    public void hit(GameManager manager, Hand hand) {
        manager.dealCard(this, this.hands.indexOf(hand), manager.getDeck(), true);
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
     * Sets the hand to be bust
     *
     * @param hand The hand
     */
    public void setHandBust(Hand hand) {
        this.handBust.put(hand, true);
    }

    /**
     * Determines if a hand is bust
     *
     * @param hand A Hand
     * @return If hand is bust
     */
    public boolean isHandBust(Hand hand) {
        return this.handBust.get(hand);
    }

    /**
     * True if all hands are bust
     *
     * @return If all hands are bust
     */
    public boolean allHandsBust() {
        for(Hand hand : this.hands) {
            if(!this.isHandBust(hand)) {
                return false;
            }
        }

        return true;
    }

    /**
     * If the player stands
     *
     * @return If the player stands
     */
    public boolean isStanding() {
        return this.status == PlayerStatus.STAND;
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
     * @param manger The game manager
     * @param hand The hand to double down
     */
    public void doubleDown(GameManager manger, Hand hand) {
        if(isDealer) {
            return;
        }

        this.setStatus(PlayerStatus.DOUBLE_DOWN);
        this.hit(manger, this.getHand(0));
        this.stand();
    }

    @Override
    public String toString() {
        return name + ": " + hands + " | Status: " + status;
    }

    /**
     * If the player should split a hand
     *
     * @param hand The hand
     * @return If the player should split hand
     */
    public boolean shouldSplitHand(Hand hand) {
        return hand.getCard(0).getName().equals("A") || (hand.getCard(0).value() >= 8 && hand.getCard(0).value() <= 9);
    }
}

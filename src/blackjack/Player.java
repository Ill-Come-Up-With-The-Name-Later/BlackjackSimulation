package blackjack;

import java.util.ArrayList;

public class Player {

    private final ArrayList<Hand> hands;
    private PlayerStatus status;
    private String name;
    private boolean isDealer;
    private double money;

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.status = PlayerStatus.IN;
        this.isDealer = false;
        this.money = 0;

        createNewHand();
    }

    public Player(String name, double money) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.status = PlayerStatus.IN;
        this.isDealer = false;
        this.money = money;

        createNewHand();
    }

    public Player(String name, boolean isDealer) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.status = PlayerStatus.IN;
        this.isDealer = isDealer;
        this.money = 0;

        createNewHand();
    }

    /**
     * Creates a new hand for the player
     */
    public Hand createNewHand() {
        Hand hand = new Hand();
        hand.setOwner(this);
        addHand(hand);

        return hand;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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

    /**
     * Gets the first hand
     *
     * @return This player's first hand
     */
    public Hand getFirstHand() {
        return this.getHand(0);
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
     * @return The new hand
     */
    public Hand splitHand(Hand hand) {
        if(isDealer) {
            return null;
        }

        if(hand.canSplit()) {
            Card card2 = hand.getCards().get(1);

            Hand hand2 = createNewHand();
            hand2.addCard(card2);
            hand.setSplit(true);

            hand.getCards().remove(1);
            hand.setSplit(true);

            this.setStatus(PlayerStatus.SPLIT);
            return hand2;
        } else {
            System.out.println("Cannot split hand.");
        }

        return null;
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
     * Determines if a hand is bust
     *
     * @param hand A Hand
     * @return If hand is bust
     */
    public boolean isHandBust(Hand hand) {
        return hand.isBust();
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
     * @param manager The game manager
     * @param hand The hand to double down
     */
    public void doubleDown(GameManager manager, Hand hand) {
        if(isDealer) {
            return;
        }

        this.setStatus(PlayerStatus.DOUBLE_DOWN);
        this.hit(manager, hand);
        hand.setDoubledDown(true);
        this.stand();
    }

    /**
     * Sets the player as in the game
     */
    public void setIn() {
        this.setStatus(PlayerStatus.IN);
    }

    public boolean canDoubleDown(Hand hand) {
        return !hand.isDoubledDown();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(":");

        if(!isDealer) {
            builder.append(" Balance: $").append(String.format("%.2f", getMoney()));
        }

        builder.append(" Hands: ").append(hands.size()).append(" ").append(hands);
        builder.append(" Active: ").append(isActive());

        return builder.toString();
    }

    /**
     * If the player should split a hand
     *
     * @param hand The hand
     * @return If the player should split hand
     */
    public boolean shouldSplitHand(Hand hand) {
        return hand.getCard(0).getName().equals("A") || (hand.getCard(0).value() >= 8 && hand.getCard(0).value() <= 10);
    }

    private boolean hasFaceCard() {
        return getFirstHand().getCard(0).isFaceCard() || getFirstHand().getCard(1).isFaceCard();
    }

    private boolean hasAce() {
        return getFirstHand().getCard(0).getName().equals("A") || getFirstHand().getCard(1).getName().equals("A");
    }

    public boolean hasBlackjack() {
        return hasAce() && hasFaceCard() && getFirstHand().getCards().size() == 2 && getHands().size() == 1;
    }
}

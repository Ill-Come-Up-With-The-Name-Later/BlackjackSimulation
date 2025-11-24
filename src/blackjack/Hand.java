package blackjack;

import java.util.ArrayList;

public class Hand {

    private final ArrayList<Card> cards;
    private boolean showValue;
    private boolean doubledDown;
    private Player owner;
    private boolean split;

    public Hand() {
        this.cards = new ArrayList<>();
        this.showValue = true;
        this.doubledDown = false;
        this.split = false;
    }

    public Hand(boolean showValue) {
        this();
        this.showValue = showValue;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean isShowValue() {
        return showValue;
    }

    public void setShowValue(boolean showValue) {
        this.showValue = showValue;
    }

    public void setDoubledDown(boolean doubledDown) {
        this.doubledDown = doubledDown;
    }

    public boolean isDoubledDown() {
        return doubledDown;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isBust() {
        return value() > 21;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    /**
     * Gets the card at an index
     *
     * @param index The index
     * @return The card at index
     */
    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * The value of the hand
     *
     * @return The hand's value
     */
    public int value() {
        int val = 0;

        for(Card card : cards) {
            val += card.value();
        }

        if(val > 21) {
            for(Card card : cards) {
                if(card.getName().equals("A") && val > 21) {
                    val -= 10;
                }
            }
        }

        return val;
    }

    /**
     * Determines if the hand can be split
     * <p>
     * The hand can only be split if the hand has two cards of
     * equal value
     *
     * @return If the hand can be split
     */
    public boolean canSplit() {
        if(cards.size() == 2) {
            if(cards.get(0).isFaceCard()) {
                return cards.get(1).isFaceCard();
            }

            if(cards.get(0).getName().equals("A")) {
                return cards.get(1).getName().equals("A");
            }

            return cards.get(0).valueEqual(cards.get(1)) &&
                    cards.get(0).getName().equals(cards.get(1).getName());
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        if(showValue) {
            str.append(String.format("Hand Value: %d - ", value()));
        } else {
            str.append(" Hand - ");
        }

        for(Card card : cards) {
            str.append(String.format("%s", card));
        }

        if(!owner.isDealer()) {
            str.append(String.format("Doubled Down: %b |", doubledDown));
        }
        str.append(String.format(" Bust: %b", value() > 21));
        return str.toString();
    }
}

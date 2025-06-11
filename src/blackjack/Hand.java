package blackjack;

import java.util.ArrayList;

public class Hand {

    private final ArrayList<Card> cards;
    private boolean showValue;

    public Hand() {
        this.cards = new ArrayList<>();
        this.showValue = true;
    }

    public Hand(boolean showValue) {
        this.cards = new ArrayList<>();
        this.showValue = true;
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
            return cards.get(0).valueEqual(cards.get(1)) &&
                    cards.get(0).getName().equals(cards.get(1).getName());
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        if(showValue) {
            str.append(String.format(" Hand Value: %d - ", value()));
        } else {
            str.append(" Hand - ");
        }

        for(Card card : cards) {
            str.append(String.format("%s", card));
        }

        str.append(String.format("Bust: %b", value() > 21));

        return str.toString();
    }
}

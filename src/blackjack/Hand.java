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
     * The value of the hand
     *
     * @return The hand's value
     */
    public int value() {
        int val = 0;

        for(Card card : cards) {
            switch (card.getName()) {
                case "2", "3", "4", "5", "6", "7", "8", "9", "10":
                    val += Integer.parseInt(card.getName());
                    break;
                case "J", "Q", "K":
                    val += 10;
                    break;
                case "A":
                    if(val <= 10) {
                        val += 11;
                    } else {
                        val += 1;
                    }

                    break;
            }
        }

        if(val > 21) {
            for(Card card : cards) {
                if(card.getName().equals("A")) {
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
            return cards.get(0).valueEqual(cards.get(1));
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if(showValue) {
            str.append(String.format("Hand Value: %d - ", value()));
        } else {
            str.append("Hand - ");
        }

        for(Card card : cards) {
            if(cards.getLast().equals(card)) {
                str.append(String.format("%s", card.toString()));
            } else {
                str.append(String.format("%s | ", card.toString()));
            }
        }

        return str.toString();
    }
}

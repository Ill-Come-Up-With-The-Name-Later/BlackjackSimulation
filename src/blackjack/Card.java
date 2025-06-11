package blackjack;

import java.util.Objects;

public class Card implements Comparable<Card> {

    private String name;
    private Suit suit;
    private boolean show;

    public Card(String name, Suit suit) {
        this.name = name;
        this.suit = suit;
        this.show = true;
    }

    public Card(String name, Suit suit, boolean show) {
        this.name = name;
        this.suit = suit;
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isShow() {
        return show;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        if(show) {
            return String.format("%s%s", name, suit.getSymbol());
        } else {
            return "?";
        }
    }

    @Override
    public int compareTo(Card o) {
        if(o.hashCode() < this.hashCode()) {
            return -1;
        }

        if(o.equals(this)) {
            return 0;
        }

        return 1;
    }

    /**
     * Determines if this card's value is equal
     * to another. Method is for determining if a
     * hand can be split.
     *
     * @param other The other card
     * @return If the other card's value is equal
     */
    public boolean valueEqual(Card other) {
        return switch(other.name) {
            case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> this.name.equals(other.getName());
            case "J", "Q", "K" -> this.name.equals(other.getName()) || this.name.equals("10");
            case "A" -> this.name.equals("A");
            default -> false;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) return false;
        return this.getSuit() == card.getSuit() && Objects.equals(this.getName(), card.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getSuit());
    }
}

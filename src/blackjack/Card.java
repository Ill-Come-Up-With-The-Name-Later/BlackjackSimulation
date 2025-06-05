package blackjack;

import java.util.Objects;

public record Card(String name, Suit suit) implements Comparable<Card> {

    @Override
    public String toString() {
        return String.format("%s%s", name, suit.getSymbol());
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
            case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> this.name.equals(other.name());
            case "J", "Q", "K" -> this.name.equals(other.name()) || this.name.equals("10");
            case "A" -> this.name.equals("A");
            default -> false;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) return false;
        return suit() == card.suit() && Objects.equals(name(), card.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), suit());
    }
}

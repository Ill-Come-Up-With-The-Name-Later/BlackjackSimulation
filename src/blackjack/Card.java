package blackjack;

import java.util.Objects;
import java.util.Random;

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

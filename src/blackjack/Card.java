package blackjack;

public record Card(String name, Suit suit) {

    @Override
    public String toString() {
        return String.format("%s %s", name, suit.getSymbol());
    }
}

package blackjack;

public enum Suit {

    HEARTS("❤"),
    CLUBS("♣"),
    SPADES("♠"),
    DIAMONDS("◆"),
    ;

    final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

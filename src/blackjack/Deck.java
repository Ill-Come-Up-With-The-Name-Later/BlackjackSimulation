package blackjack;

import java.util.Stack;

public class Deck {

    public Stack<Card> cards;

    /**
     * Creates the deck with 52 cards in it
     */
    public Deck() {
        this.cards = new Stack<>();

        addCards();
    }

    /**
     * Adds the standard 52 cards to the deck:
     * <p>
     * 2-10, J, Q, K, A of all suits, no jokers
     */
    public void addCards() {
        for(Suit suit : Suit.values()) {
            for(int i = 2; i <= 10; i++) {
                Card card = new Card(Integer.toString(i), suit);
                this.addCard(card);
            }

            Card jack = new Card("J", suit);
            Card queen = new Card("Q", suit);
            Card king = new Card("K", suit);
            Card ace = new Card("A", suit);

            this.addCard(jack);
            this.addCard(queen);
            this.addCard(king);
            this.addCard(ace);
        }
    };

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        this.cards.sort(new CardShuffleComparer());
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public Card drawCard() {
        return cards.pop();
    }
}

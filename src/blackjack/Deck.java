package blackjack;

import java.util.Random;
import java.util.Stack;

public class Deck {

    public Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
    }

    /**
     * Adds the standard 52 cards to the deck:
     * <p>
     * 2-10, J, Q, K, A of all suits, no jokers
     */
    public void addCards() {
        for(Suit suit : Suit.values()) {
            for(int i = 2; i <= 10; i++) {
                Card card = new Card(Integer.toString(i), suit, true);
                this.addCard(card);
            }

            Card jack = new Card("J", suit, true);
            Card queen = new Card("Q", suit, true);
            Card king = new Card("K", suit, true);
            Card ace = new Card("A", suit, true);

            this.addCard(jack);
            this.addCard(queen);
            this.addCard(king);
            this.addCard(ace);
        }
    }

    /**
     * Adds the standard 52 cards to the deck multiple
     * times
     *
     * @param decks The number of times to add the cards
     */
    public void addCards(int decks) {
        for(int i = 0; i < decks; i++) {
            addCards();
        }
    }

    public void addCard(Card card) {
        this.cards.push(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        for(int i = cards.size() - 1; i >= 1; i--) {
            int j = new Random().nextInt(0, i + 1);

            Card card = cards.get(i);
            Card card2 = cards.get(j);

            cards.set(i, card2);
            cards.set(j, card);
        }
    }

    /**
     * Draws a card from the top of the deck
     *
     * @return The card at the top of the deck
     */
    public Card drawCard() {
        return cards.pop();
    }

    /**
     * The number of complete 52 card decks in
     * this deck
     *
     * @return The number of complete 52 card decks in
     *         this deck
     */
    public double numCompleteDecks() {
        return this.cards.size() / 52.0;
    }

    /**
     * If there are still cards in the deck
     *
     * @return If there are still cards in the deck
     */
    public boolean hasCards() { return !this.cards.isEmpty(); }

    /**
     * The number of cards in the deck
     *
     * @return The number of cards in the deck
     */
    public int size() {
        return this.cards.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(Card card : cards) {
            str.append(String.format("%s | ", card.toString()));
        }

        return str.toString();
    }
}

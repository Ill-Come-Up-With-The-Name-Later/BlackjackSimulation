package blackjack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    public ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
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
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        int swaps = new Random().nextInt(cards.size() * 5, cards.size() * 10);

        for(int i = 0; i < swaps; i++) {
            int card1Index = new Random().nextInt(0, cards.size());
            int card2Index = new Random().nextInt(0, cards.size());

            while(card1Index == card2Index) {
                card2Index = new Random().nextInt(0, cards.size());
            }

            Card card1 = cards.get(card1Index);
            Card card2 = cards.get(card2Index);

            cards.remove(card1);
            cards.remove(card2);

            cards.add(Math.max(cards.size() - 1, Math.max(card2Index - 1, 0)), card1);
            cards.add(Math.max(cards.size() - 1, Math.max(card1Index - 1, 0)), card2);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Draws a card from the top of the deck
     *
     * @return The card at the top of the deck
     */
    public Card drawCard() {
        Card card = cards.getFirst();
        cards.removeFirst();

        return card;
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

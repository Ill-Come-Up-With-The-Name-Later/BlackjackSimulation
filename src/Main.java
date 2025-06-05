import blackjack.Deck;

public class Main {

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.addCards(1);
        deck.shuffle();

        System.out.println(deck);
    }
}

import blackjack.Deck;
import blackjack.Player;

public class Main {

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.addCards(1);
        deck.shuffle();

        System.out.println(deck);

        Player dealer = new Player("Dealer");
        dealer.setDealer(true);
        dealer.dealCard(dealer, 0, deck, true);
        dealer.dealCard(dealer, 0, deck, false);

        Player player = new Player("Test Player");
        dealer.dealCard(player, 0, deck, true);
        dealer.dealCard(player, 0, deck, true);

        System.out.println(dealer);
        System.out.println(player);
    }
}

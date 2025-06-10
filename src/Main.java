import blackjack.Deck;
import blackjack.Player;

public class Main {

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.addCards(1);
        deck.shuffle();

        System.out.println(deck);

        Player player = new Player("Test");
        player.hit(player.getHand(0), deck);
        player.hit(player.getHand(0), deck);

        player.splitHand(player.getHand(0));

        System.out.println(player);
    }
}

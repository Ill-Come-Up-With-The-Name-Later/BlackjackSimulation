import blackjack.Game;
import blackjack.GameManager;
import blackjack.Player;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.addPlayer(new Player("Player 1"));
        

        GameManager gameManager = new GameManager(game);
        gameManager.runUntilCardsOut();
    }
}

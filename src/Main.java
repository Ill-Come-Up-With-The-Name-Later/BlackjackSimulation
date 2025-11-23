import blackjack.Game;
import blackjack.GameManager;
import blackjack.Player;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));
        game.addPlayer(new Player("Player 4"));
        game.addPlayer(new Player("Player 5"));
        game.addPlayer(new Player("Player 6"));
        game.addPlayer(new Player("Player 7"));

        GameManager gameManager = new GameManager(game);
        gameManager.runUntilCardsOut();
    }
}

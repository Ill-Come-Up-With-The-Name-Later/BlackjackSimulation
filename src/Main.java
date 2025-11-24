import blackjack.Game;
import blackjack.GameManager;
import blackjack.Player;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        for(int i = 0; i < 1; i++) {
            Player player = new Player("Player " + (i + 1), 1000);
            game.addPlayer(player);
        }

        GameManager gameManager = new GameManager(game, 8);
        gameManager.playOnce();
    }
}

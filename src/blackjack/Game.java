package blackjack;

import java.util.ArrayList;

public class Game {

    private final Player dealer;
    private final ArrayList<Player> players;

    public Game() {
        this.dealer = new Player("Dealer");
        this.dealer.setDealer(true);

        this.players = new ArrayList<>();
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getDealer() {
        return dealer;
    }

    /**
     * Adds a player
     *
     * @param player The player
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * If the dealer is bust
     *
     * @return If the dealer is bust
     */
    public boolean dealerBust() {
        return dealer.isHandBust(dealer.getFirstHand());
    }

    public void reset() {
        dealer.getHands().clear();
        dealer.createNewHand();

        for(Player player : players) {
            player.getHands().clear();
            player.createNewHand();
        }
    }
}

package blackjack;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private final Game game;
    private final Deck deck;
    private final ArrayList<Card> drawnCards;

    public double runningCount;
    public double trueCount;

    public GameManager(Game game) {
        this.deck = new Deck();
        this.game = game;
        this.drawnCards = new ArrayList<>();
        this.runningCount = 0;
        this.trueCount = 0;

        this.deck.addCards(6);
    }

    public void runUntilCardsOut() {
        int stoppingPoint = new Random().nextInt(36, 52);

        Player dealer = game.getDealer();
        ArrayList<Player> players = game.getPlayers();

        deck.shuffle();

        dealInitialCards(dealer, players);
        printGameState();

        for(Player player : players) {
            playerMoves(player);
        }

        if(allPlayersBust()) {
            showDealerCards(dealer);
            printGameState();
        }

        dealerMoves(dealer);
    }

    /**
     * The moves for player
     *
     * @param player A player
     */
    private void playerMoves(Player player) {
        Hand activeHand = player.getHand(0);

        while(player.isActive()) {
            if(activeHand.value() > 21 && player.getHands().size() == 1) {
                player.setHandBust(activeHand);
                System.out.println(player.getName() + " went bust");
                player.setBust();
            } else if(activeHand.canSplit()) {
                if(player.shouldSplitHand(activeHand)) {
                    player.splitHand(activeHand);
                } else if(activeHand.value() == 20 && trueCount > 0.33) {
                    player.splitHand(activeHand);
                }
            } else if(activeHand.value() < 18) {
                player.hit(this, activeHand);
            } else {
                player.stand();
            }

            printGameState();
        }
    }

    /**
     * Sets the dealer's cards to show
     *
     * @param dealer The dealer
     */
    private void showDealerCards(Player dealer) {
        Hand activeHand = dealer.getHand(0);
        activeHand.setShowValue(true);
        activeHand.getCard(1).setShow(true);
    }

    /**
     * The dealer's moves, after the players
     *
     * @param dealer The dealer
     */
    private void dealerMoves(Player dealer) {
        Hand activeHand = dealer.getHand(0);
        activeHand.setShowValue(true);
        activeHand.getCard(1).setShow(true);
        updateCount(activeHand.getCard(1));

        while(dealer.isActive()) {
            if(activeHand.value() > 21) {
                dealer.setHandBust(activeHand);
                System.out.println("Dealer went bust");
                dealer.setBust();
            } else if(activeHand.value() >= 17) {
                dealer.stand();
            } else {
                dealer.hit(this, activeHand);
            }

            printGameState();
        }
    }

    /**
     * Prints out the players
     */
    public void printGameState() {
        System.out.println("============================");
        System.out.println("Game State");
        System.out.println(game.getDealer());

        for(Player player : game.getPlayers()) {
            System.out.println(player);
        }

        System.out.print("Drawn:");

        for(Card card : drawnCards) {
            System.out.printf("%s", card);
        }

        System.out.println();
        System.out.println("Running Count: " + runningCount);
        System.out.println("True Count: " + trueCount);
    }

    /**
     * Deals initial cards
     *
     * @param dealer The dealer
     * @param players The players
     */
    private void dealInitialCards(Player dealer, ArrayList<Player> players) {
        dealer.getHand(0).setShowValue(false);

        for(Player player : players) {
            dealCard(player, 0, deck, true);
            dealCard(player, 0, deck, true);
        }

        dealCard(dealer, 0, deck, true);
        dealCard(dealer, 0, deck, false);
    }

    public Game getGame() {
        return game;
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * Determines if all player's turns are over
     *
     * @return If all players have stood
     */
    public boolean allPlayersStand() {
        for(Player player : game.getPlayers()) {
            if(player.getStatus() != PlayerStatus.STAND) {
                return false;
            }
        }

        return true;
    }

    /**
     * If all players are bust
     *
     * @return If all players are bust
     */
    public boolean allPlayersBust() {
        for(Player player : game.getPlayers()) {
            if(player.getStatus() != PlayerStatus.BUST) {
                return false;
            }
        }

        return true;
    }

    /**
     * If all players either stand or are bust
     *
     * @return If all players either stand or are bust
     */
    public boolean allPlayersOut() {
        for(Player player : game.getPlayers()) {
            if(player.isActive()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Deals a card to a player
     */
    public void dealCard(Player player, int handIndex, Deck deck, boolean showCard) {
        Card card = deck.drawCard();
        card.setShow(showCard);

        if(showCard) {
            updateCount(card);
        }

        player.getHand(handIndex).addCard(card);
    }

    /**
     * Updates the card counting
     *
     * @param card The card drawn
     */
    public void updateCount(Card card) {
        drawnCards.add(card);

        if(card.value() < 7) {
            runningCount += 1;
        } else if(card.value() >= 10) {
            runningCount -= 1;
        }

        trueCount = runningCount / deck.numCompleteDecks();
    }
}

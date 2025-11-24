package blackjack;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private final Game game;
    private final Deck deck;
    private final ArrayList<Card> drawnCards;
    private final HashMap<Hand, Double> bets;

    public double runningCount;
    public double trueCount;

    public Player dealer;

    private final double MIN_BET = 100.00;
    private final double MAX_INITIAL_BET = 1500.00;

    private final int MAX_HANDS = 4;
    private final int CUT_POINT = 52;

    public GameManager(Game game) {
        this.deck = new Deck();
        this.game = game;
        this.drawnCards = new ArrayList<>();
        this.runningCount = 0;
        this.trueCount = 0;
        this.bets = new HashMap<>();

        dealer = game.getDealer();

        this.deck.addCards(6);
    }

    public GameManager(Game game, int decks) {
        this(game);

        this.deck.addCards(decks - 6);
    }

    /**
     * Runs the game
     */
    public void runUntilCardsOut() {
        deck.shuffle();

        while(deck.size() > CUT_POINT && !game.getPlayers().isEmpty()) {
            playGame();
        }
    }

    /**
     * Shuffles the deck and plays the game once.
     */
    public void playOnce() {
        deck.shuffle();
        playGame();
    }

    /**
     * Runs the entire game once. Does not shuffle.
     */
    public void playGame() {
        if(deck.cards.isEmpty()) {
            return;
        }

        ArrayList<Player> players = getGame().getPlayers();
        placeBets();

        if(game.getPlayers().isEmpty()) {
            return;
        }

        dealInitialCards(dealer, players);
        printGameState();

        if(dealer.hasBlackjack()) {
            showDealerCards(dealer);
            determineWinner();
            return;
        }

        for(Player player : players) {
            playerMoves(player);
        }

        if(allPlayersBust() || allPlayersBlackjack()) {
            showDealerCards(dealer);
            determineWinner();
            return;
        }

        dealerMoves(dealer);
        determineWinner();
    }

    /**
     * Places player initial bets. Removes any player
     * who cannot bet the minimum bet.
     */
    public void placeBets() {
        game.getPlayers().removeIf(x -> x.getMoney() < MIN_BET);

        for(Player player : game.getPlayers()) {
            for(Hand hand : player.getHands()) {
                if(trueCount >= 1) {
                    setBet(hand, Math.min(Math.min(MIN_BET * (Math.round(trueCount) + 1),
                            player.getMoney()), MAX_INITIAL_BET));
                } else {
                    setBet(hand, MIN_BET);
                }
            }
        }
    }

    /**
     * Sets the bet on a hand
     *
     * @param hand A hand
     * @param amount The amount bet on the hand
     */
    public void setBet(Hand hand, double amount) {
        Player player = hand.getOwner();

        if(player.getMoney() < amount) {
            return;
        }

        if(bets.containsKey(hand)) {
            double current = bets.get(hand);
            if(amount > current) {
                double amountToSubtract = amount - current;
                player.setMoney(player.getMoney() - amountToSubtract);
                bets.put(hand, amount);
            }
        } else {
            player.setMoney(player.getMoney() - amount);
            bets.put(hand, amount);
        }
    }

    /**
     * Determines winners, losers, and ties
     */
    public void determineWinner() {
        HashMap<Player, ArrayList<Hand>> winners = new HashMap<>();
        HashMap<Player, ArrayList<Hand>> ties = new HashMap<>();
        HashMap<Player, ArrayList<Hand>> losers = new HashMap<>();

        printGameState();

        for(Player player : getGame().getPlayers()) {
            for(Hand hand : player.getHands()) {
                if((hand.value() > dealer.getFirstHand().value() && !(hand.isBust())) || (game.dealerBust() && !hand.isBust())
                || (player.hasBlackjack() && !dealer.hasBlackjack())) {
                    if(!winners.containsKey(player)) {
                        winners.put(player, new ArrayList<>() {
                            {
                                add(hand);
                            }
                        });
                    } else {
                        winners.get(player).add(hand);
                    }
                } else if(hand.value() == dealer.getFirstHand().value() && !player.isHandBust(hand)) {
                    if(!ties.containsKey(player)) {
                        ties.put(player, new ArrayList<>() {
                            {
                                add(hand);
                            }
                        });
                    } else {
                        ties.get(player).add(hand);
                    }
                } else {
                    if(!losers.containsKey(player)) {
                        losers.put(player, new ArrayList<>() {
                            {
                                add(hand);
                            }
                        });
                    } else {
                        losers.get(player).add(hand);
                    }
                }
            }
        }

        ArrayList<Hand> winnersList = new ArrayList<>();

        for(ArrayList<Hand> hands : winners.values()) {
            winnersList.addAll(hands);
        }

        ArrayList<Hand> tiesList = new ArrayList<>();

        for(ArrayList<Hand> hands : ties.values()) {
            tiesList.addAll(hands);
        }

        payOut(winnersList, tiesList);

        if(!winners.isEmpty()) {
            System.out.println("- Winners -");

            printAllBetsInMap(winners);
        }

        if(!ties.isEmpty()) {
            System.out.println("- Ties -");

            printAllBetsInMap(ties);
        }

        if(!losers.isEmpty()) {
            System.out.println("- Losers -");

            printAllBetsInMap(losers);
        }

        reset();
    }

    /**
     * Resets the game
     */
    public void reset() {
        game.reset();
        bets.clear();
    }

    /**
     * Prints out all the players and bets from
     * a map.
     *
     * @param bets The bets map
     */
    private void printAllBetsInMap(HashMap<Player, ArrayList<Hand>> bets) {
        for(Player player : bets.keySet()) {
            System.out.print(player.getName() + " - Balance: $" + String.format("%.2f",
                    player.getMoney()) +  ": ");
            System.out.println();

            for(Hand hand : bets.get(player)) {
                System.out.print("\t- " + hand);
                System.out.println();
            }
        }
    }

    /**
     * Pays out the winning hands and refunds ties
     *
     * @param winners An ArrayList of winning hands
     */
    public void payOut(ArrayList<Hand> winners, ArrayList<Hand> ties) {
        for(Hand hand : winners) {
            if(hand.getCards().size() == 2 && hand.getOwner().getHands().size() == 1) {
                if(hand.getOwner().hasBlackjack() && !(hand.isDoubledDown() || hand.isSplit())) {
                    if(!dealer.hasBlackjack()) {
                        Player owner = hand.getOwner();
                        owner.setMoney(owner.getMoney() + bets.get(hand) + (bets.get(hand) * 1.5));

                        continue;
                    }
                }
            }

            Player owner = hand.getOwner();
            owner.setMoney(owner.getMoney() + (bets.get(hand) * 2));
        }

        for(Hand hand : ties) {
            Player owner = hand.getOwner();
            owner.setMoney(owner.getMoney() + bets.get(hand));
        }
    }

    /**
     * The moves for player
     *
     * @param player A player
     */
    private void playerMoves(Player player) {
        int index = 0;
        Hand activeHand = player.getHand(index);

        while(player.isActive()) {
            if(activeHand.isBust() && player.allHandsBust()) {
                System.out.println(player.getName() + " went bust");
                player.setBust();
            } else if(activeHand.canSplit()) {
                if(player.shouldSplitHand(activeHand) && player.getMoney() >= bets.get(activeHand)) {
                    Hand split = player.splitHand(activeHand);
                    setBet(split, bets.get(activeHand));
                } else if(activeHand.value() < 17) {
                    player.hit(this, activeHand);
                } else {
                    player.stand();
                }
            } else if(player.canDoubleDown(activeHand)) {
                if((activeHand.value() >= 9 && activeHand.value() <= 11)
                        && player.getMoney() >= bets.get(activeHand) * 2) {
                    player.doubleDown(this, activeHand);
                    setBet(activeHand, bets.get(activeHand) * 2);

                    player.stand();
                } else if(activeHand.value() < 17) {
                    player.hit(this, activeHand);
                } else {
                    player.stand();
                }
            } else if(activeHand.value() < 17) {
                player.hit(this, activeHand);
            } else {
                player.stand();
            }

            if(activeHand.isBust()) {
                //player.setHandBust(activeHand);
                System.out.println(player.getName() + "'s hand went bust");
                player.setBust();
            }

            //printGameState();

            if((player.getHands().size() > 1 && player.getHands().size() < MAX_HANDS + 1)
                    && (player.isStanding() || player.isHandBust(activeHand))) {
                if(index < player.getHands().size()) {
                    activeHand = player.getHand(index);
                    player.setIn();
                    index++;
                }
            }
        }

        printGameState();
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
        updateCount(activeHand.getCard(1));
    }

    /**
     * The dealer's moves, after the players
     *
     * @param dealer The dealer
     */
    private void dealerMoves(Player dealer) {
        Hand activeHand = dealer.getHand(0);
        showDealerCards(dealer);

        while(dealer.isActive()) {
            if(activeHand.isBust()) {
                //dealer.setHandBust(activeHand);
                System.out.println("Dealer went bust");
                dealer.setBust();
            } else if(activeHand.value() >= 17) {
                dealer.stand();
            } else {
                dealer.hit(this, activeHand);
            }

            //printGameState();
        }

        printGameState();
    }

    /**
     * Prints out the players
     */
    public void printGameState() {
        System.out.println("============================");
        System.out.println("Bets");

        for(Player player : game.getPlayers()) {
            System.out.println(player.getName() + ":");

            for(Hand hand : player.getHands()) {
                System.out.println("- $" + String.format("%.2f",
                        bets.get(hand)) + " " + hand);
            }
        }

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
        System.out.println("Cards Remaining: " + deck.size());
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

    public boolean allPlayersBlackjack() {
        for(Player player : game.getPlayers()) {
            if(!player.hasBlackjack()) {
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

package blackjack;

import java.util.Comparator;
import java.util.Random;

public class CardShuffleComparer implements Comparator<Card> {

    /**
     * Returns a random number to shuffle cards
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return A number between -1 and 1
     */
    @Override
    public int compare(Card o1, Card o2) {
        return new Random().nextInt(-1, 2);
    }
}

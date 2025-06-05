package blackjack;

import java.util.Comparator;
import java.util.Random;

@Deprecated
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
        if(o1.hashCode() < o2.hashCode()) {
            return -1;
        }

        if(o1.hashCode() == o2.hashCode()) {
            return 0;
        }

        return 1;
    }
}

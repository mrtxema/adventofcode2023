import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Card {
    private final int id;
    private final Set<Integer> winningNumbers;
    private final Set<Integer> cardNumbers;

    public Card(int id, Collection<Integer> winningNumbers, Collection<Integer> cardNumbers) {
        this.id = id;
        this.winningNumbers = new TreeSet<>(winningNumbers);
        this.cardNumbers = new TreeSet<>(cardNumbers);
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        int matchingCount = countMatchingNumbers();
        return matchingCount == 0 ? 0 : (int) Math.pow(2, matchingCount - 1);
    }

    int countMatchingNumbers() {
        return (int) winningNumbers.stream().filter(cardNumbers::contains).count();
    }
}

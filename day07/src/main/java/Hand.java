import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Hand {
    private final Card[] cards;
    private final long bidAmount;

    public Hand(Card[] cards, long bidAmount) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("Invalid number of cards: " + cards.length);
        }
        this.cards = cards;
        this.bidAmount = bidAmount;
    }

    public Card getCard(int position) {
        return cards[position];
    }

    public long getBidAmount() {
        return bidAmount;
    }

    public HandType getType() {
        List<Integer> kindCounts = Arrays.stream(cards)
                .collect(Collectors.groupingBy(Function.identity())).values().stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        kindCounts.add(0);
        return calculateHandType(kindCounts.get(0), kindCounts.get(1));
    }

    public HandType getAlternativeType() {
        Map<Card, List<Card>> kinds = Arrays.stream(cards).collect(Collectors.groupingBy(Function.identity()));
        int numJokers = kinds.getOrDefault(Card.J, List.of()).size();
        kinds.remove(Card.J);
        List<Integer> kindCounts = kinds.values().stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        kindCounts.add(0);
        kindCounts.add(0);
        return calculateHandType(kindCounts.get(0) + numJokers, kindCounts.get(1));
    }

    private HandType calculateHandType(int firstKindCount, int secondKindCount) {
        return switch (firstKindCount) {
            case 5 -> HandType.FIVE_OF_KIND;
            case 4 -> HandType.FOUR_OF_KIND;
            case 3 -> secondKindCount == 2 ? HandType.FULL_HOUSE : HandType.THREE_OF_KIND;
            case 2 -> secondKindCount == 2 ? HandType.TWO_PAIR : HandType.ONE_PAIR;
            default -> HandType.HIGH_CARD;
        };
    }

    public static Comparator<Hand> comparator() {
        return buildComparator(hand -> hand.getType().getValue())
                .thenComparing(buildComparator(hand -> hand.getCard(0).getValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(1).getValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(2).getValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(3).getValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(4).getValue()));
    }

    public static Comparator<Hand> alternativeComparator() {
        return buildComparator(hand -> hand.getAlternativeType().getValue())
                .thenComparing(buildComparator(hand -> hand.getCard(0).getAlternativeValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(1).getAlternativeValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(2).getAlternativeValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(3).getAlternativeValue()))
                .thenComparing(buildComparator(hand -> hand.getCard(4).getAlternativeValue()));
    }

    private static Comparator<Hand> buildComparator(ToIntFunction<Hand> function) {
        return Comparator.comparingInt(function);
    }

    @Override
    public String toString() {
        return Arrays.stream(cards).map(Card::toString).collect(Collectors.joining());
    }
}

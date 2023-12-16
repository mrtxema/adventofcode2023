import common.parser.IOUtils;

import java.util.Arrays;
import java.util.List;

public class Day4 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day4().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Card> cards = IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME), new CardParser());
        part1(cards);
        part2(cards);
    }

    private void part1(List<Card> cards) {
        int sum = cards.stream().mapToInt(Card::getPoints).sum();
        System.out.println("[Part 1] Scratchcard points sum: " + sum);
    }

    private void part2(List<Card> cards) {
        // init counts
        int[] counts = new int[cards.get(cards.size() - 1).getId() + 1];
        cards.forEach(card -> counts[card.getId()] += 1);

        // get copies
        for (Card card : cards) {
            int copies = counts[card.getId()];
            for (int i = 1; i <= card.countMatchingNumbers(); i++) {
                int winningCardId = card.getId() + i;
                if (winningCardId < counts.length) {
                    counts[card.getId() + i] += copies;
                }
            }
        }

        System.out.println("[Part 2] Total scratchcard count: " + Arrays.stream(counts).sum());
    }
}

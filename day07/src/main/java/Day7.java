import common.parser.IOUtils;

import java.util.List;
import java.util.stream.IntStream;

public class Day7 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day7().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Hand> hands = IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME), new HandParser());
        part1(hands);
        part2(hands);
    }

    private void part1(List<Hand> hands) {
        List<Hand> sortedHands = hands.stream().sorted(Hand.comparator()).toList();
        long totalWinnings = IntStream.rangeClosed(1, sortedHands.size())
                .mapToLong(rank -> rank * sortedHands.get(rank - 1).getBidAmount())
                .sum();
        System.out.println("[Part 1] Total winnings: " + totalWinnings);
    }

    private void part2(List<Hand> hands) {
        List<Hand> sortedHands = hands.stream().sorted(Hand.alternativeComparator()).toList();
        long totalWinnings = IntStream.rangeClosed(1, sortedHands.size())
                .mapToLong(rank -> rank * sortedHands.get(rank - 1).getBidAmount())
                .sum();
        System.out.println("[Part 2] Total winnings: " + totalWinnings);
    }
}

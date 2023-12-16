import common.parser.IOUtils;

import java.util.List;

public class Day9 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day9().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Sequence> sequences = IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME), new SequenceParser());
        part1(sequences);
        part2(sequences);
    }

    private void part1(List<Sequence> sequences) {
        long sum = sequences.stream().mapToLong(Sequence::extrapolateNextValue).sum();
        System.out.println("[Part 1] Extrapolated values sum: " + sum);
    }

    private void part2(List<Sequence> sequences) {
        long sum = sequences.stream().mapToLong(Sequence::extrapolatePreviousValue).sum();
        System.out.println("[Part 2] Extrapolated values sum: " + sum);
    }
}

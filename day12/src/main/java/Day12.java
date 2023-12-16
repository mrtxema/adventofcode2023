import common.parser.IOUtils;

import java.util.List;

public class Day12 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day12().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        var records = IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME), new ConditionRecordParser());
        part1(records);
        part2(records);
    }

    private void part1(List<ConditionRecord> records) {
        long arrangementCountSum = records.stream().mapToLong(ConditionRecord::countArrangements).sum();
        System.out.println("[Part 1] Arrangement count sum: " + arrangementCountSum);
    }

    private void part2(List<ConditionRecord> records) {
        long arrangementCountSum = records.stream().map(ConditionRecord::unfold).mapToLong(ConditionRecord::countArrangements).sum();
        System.out.println("[Part 2] Arrangement count sum: " + arrangementCountSum);
    }
}

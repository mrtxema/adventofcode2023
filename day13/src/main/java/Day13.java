import common.IOUtils;

import java.util.List;

public class Day13 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day13().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        var patterns = new MapPatternParser().parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        part1(patterns);
        part2(patterns);
    }

    private void part1(List<MapPattern> patterns) {
        long reflectionScoreSum = patterns.stream().mapToLong(MapPattern::getReflectionScore).sum();
        System.out.println("[Part 1] Reflection score sum: " + reflectionScoreSum);
    }

    private void part2(List<MapPattern> patterns) {
        long reflectionScoreSum = patterns.stream().mapToLong(MapPattern::getReflectionScoreWithSmudge).sum();
        System.out.println("[Part 2] Reflection score sum: " + reflectionScoreSum);
    }
}

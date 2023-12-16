import common.IOUtils;

import java.util.List;

public class Day6 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day6().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        RaceSpecsParser parser = new RaceSpecsParser();
        List<String> lines = IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME));
        part1(parser, lines);
        part2(parser, lines);
    }

    private void part1(RaceSpecsParser parser, List<String> lines) {
        List<Race> races = parser.parse(lines);
        int multiplication = races.stream().map(Race::getHoldTimesToWin).mapToInt(List::size).reduce(1, (a, b) -> a * b);
        System.out.println("[Part 1] Ways to win multiplication: " + multiplication);
    }

    private void part2(RaceSpecsParser parser, List<String> lines) {
        Race race = parser.parseSingleRace(lines);
        System.out.println("[Part 2] Ways to win: " + race.getHoldTimesToWin().size());
    }
}

public class Day11 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day11().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Universe universe = new UniverseParser().parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        part1(universe);
        part2(universe);
    }

    private void part1(Universe universe) {
        long distanceSum = universe.expand(2).getGalaxyPairs().mapToLong(pair -> pair.a().getDistance(pair.b())).sum();
        System.out.println("[Part 1] Galaxy pairs distance sum: " + distanceSum);
    }

    private void part2(Universe universe) {
        long distanceSum = universe.expand(1_000_000).getGalaxyPairs().mapToLong(pair -> pair.a().getDistance(pair.b())).sum();
        System.out.println("[Part 2] Galaxy pairs distance sum: " + distanceSum);
    }
}

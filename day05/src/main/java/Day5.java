public class Day5 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day5().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Almanac almanac = new AlmanacParser().parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        part1(almanac);
        //part2(almanac);
        new Alternative().part2(almanac);
    }

    private void part1(Almanac almanac) {
        long min = almanac.seeds().stream().mapToLong(almanac::convertSeed).min().orElseThrow();
        System.out.println("[Part 1] Lowest location number: " + min);
    }

    private void part2(Almanac almanac) {
        /*
        long seed = almanac.getSeedForLowestLocation();
        long min = almanac.convertSeed(seed);
        System.out.println("[Part 2] Lowest location number: " + min);
         */
        System.out.println("Seed 3977905 -> : Location " + almanac.convertSeed(3977905));
        System.out.println("Seed 32477653 -> : Location " + almanac.convertSeed(32477653));
    }
}

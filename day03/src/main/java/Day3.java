public class Day3 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day3().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Schematic schematic = Schematic.parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        part1(schematic);
        part2(schematic);
    }

    private void part1(Schematic schematic) {
        int sum = schematic.getPartNumbers().stream().mapToInt(n -> n).sum();
        System.out.println("[Part 1] Engine part numbers sum: " + sum);
    }

    private void part2(Schematic schematic) {
        int sum = schematic.getGearRatios().stream().mapToInt(n -> n).sum();
        System.out.println("[Part 2] Gear ratios sum: " + sum);
    }
}

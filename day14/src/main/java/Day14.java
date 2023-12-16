import common.IOUtils;

public class Day14 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day14().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        var patterns = new PlatformnParser().parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        part1(patterns);
        part2(patterns);
    }

    private void part1(Platform platform) {
        int totalLoad = platform.tilt(Direction.NORTH).getRoundedRocksLoad();
        System.out.println("[Part 1] Total load: " + totalLoad);
    }

    private void part2(Platform platform) {
        int totalLoad = platform.spin(1_000_000_000).getRoundedRocksLoad();
        System.out.println("[Part 2] Total load: " + totalLoad);
    }
}

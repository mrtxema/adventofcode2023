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
        Solver solver = new Solver(INPUT_FILE_NAME);
        System.out.println("[Part 1] Lowest location number: " + solver.part1());
        System.out.println("[Part 2] Lowest location number: " + solver.part2());
    }
}

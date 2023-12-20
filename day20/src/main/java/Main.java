public class Main {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Day20Solver solver = new Day20Solver(INPUT_FILE_NAME);
        System.out.println("[Part 1] Multiplied pulse counts: " + solver.part1());
        System.out.println("[Part 2] Button presses count: " + solver.part2());
    }
}

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
        Day21Solver solver = new Day21Solver(INPUT_FILE_NAME).parseFile();
        System.out.println("[Part 1] Reachable garden plots: " + solver.part1(64));
        System.out.println("[Part 2] Reachable garden plots: " + solver.part2(26501365));
    }
}

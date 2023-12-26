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
        Day24Solver solver = new Day24Solver(INPUT_FILE_NAME).parseFile();
        System.out.println("[Part 1] Intersection count: " + solver.part1(200000000000000L, 400000000000000L));
        System.out.println("[Part 2] Initial position coordinates sum: " + solver.part2());
    }
}

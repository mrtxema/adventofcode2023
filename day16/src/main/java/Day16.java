public class Day16 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day16().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Solver solver = new Solver(INPUT_FILE_NAME).parseFile();
        System.out.println("[Part 1] Energized tiles count: " + solver.part1());
        System.out.println("[Part 2] Best energized tiles count " + solver.part2());
    }
}

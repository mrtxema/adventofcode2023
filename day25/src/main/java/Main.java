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
        Day25Solver solver = new Day25Solver(INPUT_FILE_NAME).parseFile();
        System.out.println("[Part 1] Group sizes multiplied: " + solver.part1());
    }
}

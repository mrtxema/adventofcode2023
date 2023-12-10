public class Day10 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day10().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        PipeDiagram diagram = new PipeDiagramParser().parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        part1(diagram);
        part2(diagram);
    }

    private void part1(PipeDiagram diagram) {
        System.out.println("[Part 1] Farthest step count: " + diagram.getFarthestStepCount());
    }

    private void part2(PipeDiagram diagram) {
        System.out.println("[Part 2] Inside tile count: " + diagram.countInsideTiles());
    }
}

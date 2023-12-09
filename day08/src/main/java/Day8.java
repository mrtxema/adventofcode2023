import java.util.List;

public class Day8 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day8().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Network network = new NetworkParser().parse(IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME)));
        //part1(network);
        part2(network);
    }

    private void part1(Network network) {
        Network.Node currentNode = network.getNode("AAA");
        int steps = 0;
        while (!currentNode.code().equals("ZZZ")) {
            for (Direction direction : network.getInstructions()) {
                currentNode = network.getNode(currentNode.getNext(direction));
                steps++;
                if (currentNode.code().equals("ZZZ")) {
                    break;
                }
            }
        }
        System.out.println("[Part 1] Steps: " + steps);
    }

    private void part2(Network network) {
        List<Network.Cycle> cycles = network.getNodes().stream()
                .filter(node -> node.code().endsWith("A"))
                .map(network::findCycle)
                .toList();
        System.out.println("Cycles: " + cycles);
        //System.out.println("[Part 2] Steps: " + steps);
    }
}

import common.IOUtils;

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
        part1(network);
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
        long steps = network.getNodes().stream()
                .filter(node -> node.code().endsWith("A"))
                .mapToLong(network::walk)
                .reduce(1, Day8::lcm);
        System.out.println("[Part 2] Steps: " + steps);
    }

    private static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}

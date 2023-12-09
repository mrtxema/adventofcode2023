import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Network {
    private final List<Direction> instructions;
    private final Map<String, Node> nodes;

    public Network(List<Direction> instructions, List<Node> nodes) {
        this.instructions = instructions;
        this.nodes = nodes.stream().collect(Collectors.toMap(Node::code, Function.identity()));
    }

    public List<Direction> getInstructions() {
        return instructions;
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public Node getNode(String code) {
        return nodes.get(code);
    }

    public Cycle findCycle(Node startingNode) {
        long counter = 0;
        Set<Step> steps = new HashSet<>();
        List<Long> endingSteps = new ArrayList<>();
        Node currentNode = startingNode;
        steps.add(new Step(currentNode, getInstructions().get(0)));
        while (true) {
            for (Direction direction : getInstructions()) {
                currentNode = getNode(currentNode.getNext(direction));
                counter++;
                if (currentNode.code().endsWith("Z")) {
                    endingSteps.add(counter);
                }
                if (!steps.add(new Step(currentNode, direction))) {
                    return new Cycle(counter, endingSteps);
                }
            }
        }
    }

    public record Node(String code, String left, String right) {

        public String getNext(Direction direction) {
            return direction == Direction.L ? left : right;
        }
    }

    public record Step(Node node, Direction direction) {
    }

    public record Cycle(long length, List<Long> endingSteps) {
    }
}

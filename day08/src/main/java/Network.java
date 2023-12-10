import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    public long walk(Node node) {
        long step = 0L;
        while (!node.code().endsWith("Z")) {
            int move = (int) (step % getInstructions().size());
            node = getNode(node.getNext(getInstructions().get(move)));
            step++;
        }
        return step;
    }

    public record Node(String code, String left, String right) {

        public String getNext(Direction direction) {
            return direction == Direction.L ? left : right;
        }
    }
}

import java.util.List;

public class NetworkParser {

    public Network parse(List<String> lines) {
        return new Network(parseInstruction(lines.get(0)), lines.stream().skip(2).map(this::parseNode).toList());
    }

    private List<Direction> parseInstruction(String spec) {
        return StringUtils.characters(spec).map(String::valueOf).map(Direction::valueOf).toList();
    }

    private Network.Node parseNode(String spec) {
        SplitResult result = StringUtils.split(spec, "=", true);
        if (!result.tail().startsWith("(") || !result.tail().endsWith(")")) {
            throw new IllegalArgumentException("Invalid node format: " + spec);
        }
        SplitResult nextNodes = StringUtils.split(result.tail().substring(1, result.tail().length() - 1), ",", true);
        return new Network.Node(result.head(), nextNodes.head(), nextNodes.tail());
    }
}

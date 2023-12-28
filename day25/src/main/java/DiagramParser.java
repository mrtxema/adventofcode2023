import common.parser.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiagramParser {

    public Diagram parse(List<String> lines) {
        List<Diagram.Connection> connections = lines.stream().flatMap(this::parseConnections).toList();
        Set<String> nodes = Stream.concat(connections.stream().map(Diagram.Connection::a), connections.stream().map(Diagram.Connection::b))
                .collect(Collectors.toSet());
        return new Diagram(nodes, connections);
    }

    private Stream<Diagram.Connection> parseConnections(String line) {
        var split = StringUtils.split(line, ":", true);
        return Arrays.stream(split.tail().split("\\s+")).map(right -> new Diagram.Connection(split.head(), right));
    }
}

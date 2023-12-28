import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Diagram {
    private final Set<String> nodes;
    private final List<Connection> connections;

    public Diagram(Set<String> nodes, List<Connection> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public List<Connection> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    public record Connection(String a, String b) {

        public Connection reverse() {
            return new Connection(b, a);
        }
    }
}

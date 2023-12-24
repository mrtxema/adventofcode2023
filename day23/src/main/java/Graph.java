import common.movement.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private final Node start;
    private final Node exit;

    public Graph(Node start, Node exit) {
        this.start = start;
        this.exit = exit;
    }

    public int getLongestHikeLength() {
        return getLongestHikeLength(start, new HashSet<>(), 0);
    }

    private int getLongestHikeLength(Node position, Set<Node> visited, int steps) {
        if (position == exit) {
            return steps;
        }
        visited.add(position);
        int maxLength = 0;
        for (Link link : position.links) {
            if (!visited.contains(link.target)) {
                int length = getLongestHikeLength(link.target, visited, steps + link.weight);
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }
        visited.remove(position);
        return maxLength;
    }

    public static class Node {
        final Position position;
        final List<Link> links;

        Node(Position position) {
            this.position = position;
            this.links = new ArrayList<>();
        }

        void addLink(Node target, int weight) {
            if (links.stream().noneMatch(l -> l.target == target)) {
                links.add(new Link(target, weight));
                target.links.add(new Link(this, weight));
            }
        }
    }

    public record Link(Node target, int weight) {
    }
}

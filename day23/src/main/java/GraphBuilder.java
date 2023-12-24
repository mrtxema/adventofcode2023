import common.movement.Direction;
import common.movement.Position;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class GraphBuilder {
    private static final char FOREST = '#';
    private final char[][] map;
    private final boolean mustUseSlopes;
    private final Position startPosition;
    private final Position endPosition;

    public GraphBuilder(char[][] map, boolean mustUseSlopes, Position startPosition, Position endPosition) {
        this.map = map;
        this.mustUseSlopes = mustUseSlopes;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Graph build() {
        Graph.Node graphStart = new Graph.Node(startPosition);
        Graph.Node graphExit = null;
        Set<Graph.Node> graphNodes = new HashSet<>(Set.of(graphStart));

        Set<Position> visitedNodes = new HashSet<>();
        Queue<Graph.Node> toVisit = new LinkedList<>(graphNodes);
        visitedNodes.add(startPosition);
        while (!toVisit.isEmpty()) {
            Graph.Node current = toVisit.remove();
            List<Position> neighbours = getNextPositions(current.position, Set.of());
            for (Position neighbour : neighbours) {
                Set<Position> visited = new HashSet<>();
                visited.add(current.position);
                visited.add(neighbour);
                Position target = neighbour;
                List<Position> nextPositions = getNextPositions(neighbour, visited);
                while (nextPositions.size() == 1) {
                    target = nextPositions.get(0);
                    visited.add(target);
                    nextPositions = getNextPositions(target, visited);
                }
                Graph.Node next;
                if (visitedNodes.contains(target)) {
                    Position finalTarget = target;
                    next = graphNodes.stream().filter(n -> n.position.equals(finalTarget)).findAny().orElseThrow();
                } else {
                    next = new Graph.Node(target);
                    graphNodes.add(next);
                    toVisit.add(next);
                    visitedNodes.add(target);
                    if (target.equals(endPosition)) {
                        graphExit = next;
                    }
                }
                current.addLink(next, visited.size() - 1);
            }
        }

        return new Graph(graphStart, graphExit);
    }

    private List<Position> getNextPositions(Position position, Set<Position> visited) {
        return getValidDirections(position)
                .flatMap(direction -> newPosition(position, direction, visited).stream())
                .toList();
    }

    private Stream<Direction> getValidDirections(Position position) {
        if (!mustUseSlopes) {
            return Arrays.stream(Direction.values());
        }
        return Slope.fromSymbol(map[position.y()][position.x()])
                .map(slope -> Stream.of(slope.getDirection()))
                .orElse(Arrays.stream(Direction.values()));
    }

    private Optional<Position> newPosition(Position current, Direction direction, Set<Position> visited) {
        Position newPosition = direction.move(current);
        if (!isValidPosition(newPosition) || visited.contains(newPosition)) {
            return Optional.empty();
        }
        return Optional.of(newPosition);
    }

    private boolean isValidPosition(Position position) {
        return position.x() >= 0 && position.x() < map[0].length && position.y() >= 0 && position.y() < map.length
                && map[position.y()][position.x()] != FOREST;
    }
}

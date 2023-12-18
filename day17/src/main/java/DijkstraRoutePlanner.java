import common.movement.Direction;
import common.movement.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DijkstraRoutePlanner {
    private final int[][] map;
    private final int minStraightMoves;
    private final int maxStraightMoves;

    public DijkstraRoutePlanner(int[][] map, int minStraightMoves, int maxStraightMoves) {
        this.map = map;
        this.minStraightMoves = minStraightMoves;
        this.maxStraightMoves = maxStraightMoves;
    }

    public int calculateDistance(Position startPosition, Position endPosition) {
        GridDistanceCalculationQueue queue = new GridDistanceCalculationQueue();
        Stream.of(Direction.EAST, Direction.SOUTH)
                .map(direction -> new State(startPosition, direction, 0))
                .forEach(state -> queue.add(state, 0));
        queue.add(new State(startPosition, Direction.EAST, 0), 0);
        queue.add(new State(startPosition, Direction.SOUTH, 0), 0);
        while (queue.isNotEmpty()) {
            State current = queue.pop();
            if (current.position().equals(endPosition)) {
                return queue.getDistance(current);
            }

            List<State> candidates = getValidDirections(current)
                    .flatMap(direction -> newPosition(current, direction).stream())
                    .toList();

            for (State candidate : candidates) {
                int newCost = queue.getDistance(current) + getWeight(candidate.position());
                if (newCost < queue.getDistance(candidate)) {
                    queue.add(candidate, newCost);
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }

    private Stream<Direction> getValidDirections(State current) {
        List<Direction> validDirections = new ArrayList<>();
        if (current.lastDirectionCount() >= minStraightMoves) {
            switch (current.lastDirection()) {
                case NORTH, SOUTH -> Stream.of(Direction.EAST, Direction.WEST).forEach(validDirections::add);
                case EAST, WEST -> Stream.of(Direction.NORTH, Direction.SOUTH).forEach(validDirections::add);
            }
        }
        if (current.lastDirectionCount() < maxStraightMoves) {
            validDirections.add(current.lastDirection());
        }
        return validDirections.stream();
    }

    private Optional<State> newPosition(State current, Direction direction) {
        Position newPosition = direction.move(current.position());
        if (!isValidPosition(newPosition)) {
            return Optional.empty();
        }
        int directionCount = direction == current.lastDirection() ? current.lastDirectionCount() + 1 : 1;
        return Optional.of(new State(newPosition, direction, directionCount));
    }

    private int getWeight(Position position) {
        return map[position.y()][position.x()];
    }

    private boolean isValidPosition(Position position) {
        return position.x() >= 0 && position.x() < map[0].length && position.y() >= 0 && position.y() < map.length;
    }
}

import common.movement.Direction;
import common.movement.Position;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LongestPathCalculator {
    private static final char FOREST = '#';
    private final char[][] map;
    private final boolean mustUseSlopes;

    public LongestPathCalculator(char[][] map, boolean mustUseSlopes) {
        this.map = map;
        this.mustUseSlopes = mustUseSlopes;
    }

    public int calculateLongestPath(Position startPosition, Position endPosition) {
        Set<State> currentStates = Set.of(State.initialState(startPosition, map.length));
        int currentDistance = 0;
        int maxDistance = 0;
        while (!currentStates.isEmpty()) {
            if (currentStates.stream().anyMatch(state -> state.getPosition().equals(endPosition))) {
                maxDistance = currentDistance;
            }
            currentStates = currentStates.stream()
                    .filter(state -> !state.getPosition().equals(endPosition))
                    .flatMap(state -> getValidDirections(state).flatMap(direction -> newPosition(state, direction).stream()))
                    .collect(Collectors.toSet());
            currentDistance++;
        }
        return maxDistance;
    }

    private Stream<Direction> getValidDirections(State current) {
        if (!mustUseSlopes) {
            return Arrays.stream(Direction.values());
        }
        return Slope.fromSymbol(map[current.getPosition().y()][current.getPosition().x()])
                .map(slope -> Stream.of(slope.getDirection()))
                .orElse(Arrays.stream(Direction.values()));
    }

    private Optional<State> newPosition(State current, Direction direction) {
        Position newPosition = direction.move(current.getPosition());
        if (!isValidPosition(newPosition) || current.hasVisited(newPosition)) {
            return Optional.empty();
        }
        return Optional.of(current.add(newPosition));
    }

    private boolean isValidPosition(Position position) {
        return position.x() >= 0 && position.x() < map[0].length && position.y() >= 0 && position.y() < map.length
                && map[position.y()][position.x()] != FOREST;
    }
}

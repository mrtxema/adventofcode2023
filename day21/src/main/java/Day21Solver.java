import common.math.MathUtils;
import common.movement.Direction;
import common.movement.Position;
import common.parser.IOUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21Solver {
    private static final char START = 'S';
    private static final char ROCK = '#';
    private final String fileName;
    private char[][] map;

    public Day21Solver(String fileName) {
        this.fileName = fileName;
    }

    public Day21Solver parseFile() {
        map = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream().map(String::toCharArray).toArray(char[][]::new);
        return this;
    }

    public int part1(int steps) {
        Set<Position> reachedPositions = Set.of(findPosition(START).orElseThrow());
        for (int i = 0; i < steps; i++) {
            reachedPositions = reachedPositions.stream()
                    .flatMap(this::getNewPositions)
                    .filter(this::isValidPositionOnFiniteMap)
                    .collect(Collectors.toSet());
        }
        return reachedPositions.size();
    }

    public long part2(long totalSteps) {
        Position startPosition = findPosition(START).orElseThrow();
        List<Position> reachedPositions = new ArrayList<>(List.of(startPosition));
        Set<Position> allPositions = new HashSet<>(reachedPositions);
        Extrapolator extrapolator = new Extrapolator(1000);
        for (int steps = 1; steps <= extrapolator.size(); steps++) {
            reachedPositions = reachedPositions.stream()
                    .flatMap(this::getNewPositions)
                    .distinct()
                    .filter(p -> !allPositions.contains(p))
                    .filter(this::isValidPositionOnInfiniteMap)
                    .toList();
            allPositions.addAll(reachedPositions);
            if (extrapolator.registerResults(steps, reachedPositions.size())) {
                break;
            }
        }
        return extrapolator.extrapolate(totalSteps);
    }

    private Optional<Position> findPosition(char symbol) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == symbol) {
                    return Optional.of(new Position(x, y));
                }
            }
        }
        return Optional.empty();
    }

    private Stream<Position> getNewPositions(Position startPosition) {
        return Arrays.stream(Direction.values())
                .map(d -> d.move(startPosition))
                .distinct();
    }

    private boolean isValidPositionOnFiniteMap(Position position) {
        return position.x() >= 0 && position.x() < map[0].length && position.y() >= 0 && position.y() < map.length &&
                map[position.y()][position.x()] != ROCK;
    }

    private boolean isValidPositionOnInfiniteMap(Position position) {
        int boundedX = MathUtils.mod(position.x(), map[0].length);
        int boundedY = MathUtils.mod(position.y(), map.length);
        return map[boundedY][boundedX] != ROCK;
    }
}

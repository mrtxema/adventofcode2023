import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PipeDiagram {
    private final Pipe[][] diagram;
    private final Position startingPosition;

    public PipeDiagram(Pipe[][] diagram, Position startingPosition) {
        this.diagram = diagram;
        this.startingPosition = startingPosition;
    }

    public Optional<Pipe> getPipe(Position position) {
        return Optional.ofNullable(diagram[position.y()][position.x()]);
    }

    public int getFarthestStepCount() {
        return getLoop().countPoints() / 2;
    }

    public int countInsideTiles() {
        Loop loop = getLoop();
        return (int) getAllPositions().filter(loop::isInsidePoint).count();
    }

    private Stream<Position> getAllPositions() {
        int numColumns = Arrays.stream(diagram).mapToInt(pipes -> pipes.length).max().orElse(0);
        return IntStream.range(0, diagram.length).boxed()
                .flatMap(y -> IntStream.range(0, numColumns).mapToObj(x -> new Position(x, y)));
    }

    private Loop getLoop() {
        LoopBuilder loopBuilder = new LoopBuilder(startingPosition);
        List<Position> initials = startingPosition.getAdjacentPositions()
                .filter(this::isValidPosition)
                .filter(p -> getPipe(p).stream().flatMap(pipe -> pipe.getAdjacentPositions(p)).anyMatch(startingPosition::equals))
                .toList();
        if (initials.size() != 2) {
            throw new IllegalStateException("Invalid adjacent positions to starting point: " + initials);
        }
        int count = loopBuilder.addLeft(Stream.of(initials.get(0))) + loopBuilder.addRight(Stream.of(initials.get(1)));
        while (count > 0) {
            count = loopBuilder.addLeft(findAdjacentPositions(loopBuilder.getLastLeft())) +
                    loopBuilder.addRight(findAdjacentPositions(loopBuilder.getLastRight()));
        }
        return loopBuilder.build();
    }

    private Stream<Position> findAdjacentPositions(Position position) {
        if (position.equals(startingPosition)) {
            return position.getAdjacentPositions().filter(this::isValidPosition)
                    .filter(p -> getPipe(p).stream().flatMap(pipe -> pipe.getAdjacentPositions(p)).anyMatch(position::equals));
        }
        return getPipe(position).orElseThrow().getAdjacentPositions(position).filter(this::isValidPosition);
    }

    private boolean isValidPosition(Position position) {
        return position.y() >= 0 && position.y() < diagram.length && position.x() >= 0 && position.x() < diagram[position.y()].length;
    }
}

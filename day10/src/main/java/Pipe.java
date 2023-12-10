import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum Pipe {
    NORTH_SOUTH('|') {
        @Override
        public Stream<Position> getAdjacentPositions(Position pipePosition) {
            return Stream.of(pipePosition.move(0, -1), pipePosition.move(0, 1));
        }
    },
    EAST_WEST('-') {
        @Override
        public Stream<Position> getAdjacentPositions(Position pipePosition) {
            return Stream.of(pipePosition.move(-1, 0), pipePosition.move(1, 0));
        }
    },
    NORTH_EAST('L') {
        @Override
        public Stream<Position> getAdjacentPositions(Position pipePosition) {
            return Stream.of(pipePosition.move(0, -1), pipePosition.move(1, 0));
        }
    },
    NORTH_WEST('J') {
        @Override
        public Stream<Position> getAdjacentPositions(Position pipePosition) {
            return Stream.of(pipePosition.move(0, -1), pipePosition.move(-1, 0));
        }
    },
    SOUTH_WEST('7') {
        @Override
        public Stream<Position> getAdjacentPositions(Position pipePosition) {
            return Stream.of(pipePosition.move(0, 1), pipePosition.move(-1, 0));
        }
    },
    SOUTH_EAST('F') {
        @Override
        public Stream<Position> getAdjacentPositions(Position pipePosition) {
            return Stream.of(pipePosition.move(0, 1), pipePosition.move(1, 0));
        }
    };

    private final char symbol;

    Pipe(char symbol) {
        this.symbol = symbol;
    }

    public static Optional<Pipe> parse(char symbol) {
        return Arrays.stream(Pipe.values()).filter(pipe -> pipe.symbol == symbol).findAny();
    }

    public abstract Stream<Position> getAdjacentPositions(Position pipePosition);
}

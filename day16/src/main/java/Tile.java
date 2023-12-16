import common.movement.Direction;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum Tile {
    EMPTY('.') {

        @Override
        public Stream<Direction> getNewDirections(Direction direction) {
            return Stream.of(direction);
        }
    },
    SWNE_MIRROR('/') {

        @Override
        public Stream<Direction> getNewDirections(Direction direction) {
            return switch (direction) {
                case NORTH -> Stream.of(Direction.EAST);
                case EAST -> Stream.of(Direction.NORTH);
                case SOUTH -> Stream.of(Direction.WEST);
                case WEST -> Stream.of(Direction.SOUTH);
            };
        }
    },
    NWSE_MIRROR('\\') {

        @Override
        public Stream<Direction> getNewDirections(Direction direction) {
            return switch (direction) {
                case NORTH -> Stream.of(Direction.WEST);
                case EAST -> Stream.of(Direction.SOUTH);
                case SOUTH -> Stream.of(Direction.EAST);
                case WEST -> Stream.of(Direction.NORTH);
            };
        }
    },
    VERTICAL_SPLITTER('|') {

        @Override
        public Stream<Direction> getNewDirections(Direction direction) {
            return switch (direction) {
                case NORTH, SOUTH -> Stream.of(direction);
                case EAST, WEST -> Stream.of(Direction.NORTH, Direction.SOUTH);
            };
        }
    },
    HORIZONTAL_SPLITTER('-') {

        @Override
        public Stream<Direction> getNewDirections(Direction direction) {
            return switch (direction) {
                case NORTH, SOUTH -> Stream.of(Direction.EAST, Direction.WEST);
                case EAST, WEST -> Stream.of(direction);
            };
        }
    };

    private final char symbol;

    Tile(char symbol) {
        this.symbol = symbol;
    }

    public static Optional<Tile> parse(char symbol) {
        return Arrays.stream(Tile.values()).filter(tile -> tile.symbol == symbol).findAny();
    }

    public abstract Stream<Direction> getNewDirections(Direction direction);
}

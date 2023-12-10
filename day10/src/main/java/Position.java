import java.util.stream.Stream;

public record Position(int x, int y) {

    public Position move(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }

    public Stream<Position> getAdjacentPositions() {
        return Stream.of(
                new Position(x - 1, y),
                new Position(x + 1, y),
                new Position(x, y - 1),
                new Position(x, y + 1));
    }
}

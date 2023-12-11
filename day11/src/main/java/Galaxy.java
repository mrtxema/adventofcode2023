import java.util.BitSet;

public class Galaxy {
    private final int id;
    private final Position position;

    public Galaxy(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Galaxy expand(BitSet emptyColumns, BitSet emptyRows, int expansionFactor) {
        final int k = expansionFactor - 1;
        int expansionWidth = (int) emptyColumns.stream().filter(column -> column < position.x()).count() * k;
        int expansionHeight = (int) emptyRows.stream().filter(row -> row < position.y()).count() * k;
        return new Galaxy(id, new Position(position.x() + expansionWidth, position.y() + expansionHeight));
    }

    public long getDistance(Galaxy other) {
        return Math.abs(position.x() - other.getPosition().x()) + Math.abs(position.y() - other.getPosition().y());
    }

    public record Pair(Galaxy a, Galaxy b) {
    }
}

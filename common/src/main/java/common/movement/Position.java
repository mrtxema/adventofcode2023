package common.movement;

import java.util.Comparator;

public record Position(int x, int y) implements Comparable<Position> {
    private static final Comparator<Position> COMPARATOR = Comparator.comparingInt(Position::x).thenComparingInt(Position::y);

    @Override
    public int compareTo(Position o) {
        return COMPARATOR.compare(this, o);
    }
}

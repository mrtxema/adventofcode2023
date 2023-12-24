import common.movement.Position;
import java.util.BitSet;
import java.util.Objects;

public class State {
    private final Position position;
    private final BitSet visitedPositions;
    private final int yAxisLength;

    private State(Position position, BitSet visitedPositions, int yAxisLength) {
        this.position = position;
        this.visitedPositions = visitedPositions;
        this.yAxisLength = yAxisLength;
    }

    public static State initialState(Position startPosition, int yAxisLength) {
        BitSet visitedPositions = new BitSet(yAxisLength * yAxisLength);
        visitedPositions.set(getIndex(startPosition, yAxisLength));
        return new State(startPosition, visitedPositions, yAxisLength);
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasVisited(Position position) {
        return visitedPositions.get(getIndex(position, yAxisLength));
    }

    public State add(Position newPosition) {
        BitSet newVisitedPositions = (BitSet) visitedPositions.clone();
        newVisitedPositions.set(getIndex(newPosition, yAxisLength));
        return new State(newPosition, newVisitedPositions, yAxisLength);
    }

    private static int getIndex(Position position, int yAxisLength) {
        return position.x() * yAxisLength + position.y();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State state)) {
            return false;
        }
        return position.equals(state.position) && visitedPositions.equals(state.visitedPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, visitedPositions);
    }
}

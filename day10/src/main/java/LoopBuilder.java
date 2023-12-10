import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LoopBuilder {
    private final Position startingPosition;
    private final List<Position> left;
    private final List<Position> right;
    private final Set<Position> allPoints;

    public LoopBuilder(Position startingPosition) {
        this.startingPosition = startingPosition;
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.allPoints = new HashSet<>();
        this.allPoints.add(startingPosition);
    }

    public int addLeft(Stream<Position> positions) {
        return add(positions, left);
    }

    public int addRight(Stream<Position> positions) {
        return add(positions, right);
    }

    private int add(Stream<Position> positions, List<Position> destination) {
        List<Position> filteredPositions = positions.filter(p -> !allPoints.contains(p)).toList();
        destination.addAll(filteredPositions);
        allPoints.addAll(filteredPositions);
        return filteredPositions.size();
    }

    public Position getLastLeft() {
        return left.get(left.size() - 1);
    }

    public Position getLastRight() {
        return right.get(right.size() - 1);
    }

    public Loop build() {
        List<Position> reversedRight = new ArrayList<>(right);
        Collections.reverse(reversedRight);
        var points = Stream.concat(Stream.of(startingPosition), Stream.concat(left.stream(), reversedRight.stream())).toList();
        return new Loop(points);
    }
}

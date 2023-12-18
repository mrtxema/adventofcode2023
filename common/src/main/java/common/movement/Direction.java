package common.movement;

public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    private final int xOffset;
    private final int yOffset;

    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Position move(Position start) {
        return move(start, 1);
    }

    public Position move(Position start, int steps) {
        return new Position(start.x() + xOffset * steps, start.y() + yOffset * steps);
    }
}

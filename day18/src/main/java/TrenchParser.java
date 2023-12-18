import common.movement.Direction;
import common.parser.SimpleParser;

public class TrenchParser extends SimpleParser<Trench> {
    private static final Direction[] DIRECTIONS_FROM_HEX =
            new Direction[] { Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH };

    @Override
    protected Trench nonNullParse(String line) {
        String[] parts = line.split("\\s+", 3);
        return new Trench(parseDirection(parts[0]), Integer.parseInt(parts[1]), parseColor(parts[2]));
    }

    private Direction parseDirection(String spec) {
        return switch (spec) {
            case "U" -> Direction.NORTH;
            case "R" -> Direction.EAST;
            case "D" -> Direction.SOUTH;
            case "L" -> Direction.WEST;
            default -> throw new IllegalArgumentException("Invalid direction: " + spec);
        };
    }

    private String parseColor(String spec) {
        if (!spec.startsWith("(") || !spec.endsWith(")")) {
            throw new IllegalArgumentException("Invalid color: " + spec);
        }
        return spec.substring(1, spec.length() - 1);
    }

    public Trench swap(Trench trench) {
        int length = Integer.parseInt(trench.color().substring(1, 6), 16);
        Direction direction = DIRECTIONS_FROM_HEX[Integer.parseInt(trench.color().substring(6))];
        return new Trench(direction, length, null);
    }
}

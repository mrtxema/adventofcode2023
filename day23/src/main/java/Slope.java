import common.movement.Direction;

import java.util.Arrays;
import java.util.Optional;

public enum Slope {
    UP('^', Direction.NORTH),
    RIGHT('>', Direction.EAST),
    DOWN('v', Direction.SOUTH),
    LEFT('<', Direction.WEST);

    private final char symbol;
    private final Direction direction;

    Slope(char symbol, Direction direction) {
        this.symbol = symbol;
        this.direction = direction;
    }

    public static Optional<Slope> fromSymbol(char symbol) {
        return Arrays.stream(Slope.values()).filter(slope -> slope.symbol == symbol).findAny();
    }

    public Direction getDirection() {
        return direction;
    }
}

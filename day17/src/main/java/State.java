import common.movement.Direction;
import common.movement.Position;

record State(Position position, Direction lastDirection, int lastDirectionCount) {
}

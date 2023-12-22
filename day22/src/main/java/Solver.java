import common.parser.IOUtils;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final String fileName;
    private List<Brick> bricks;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        bricks = IOUtils.readTrimmedLines(getClass().getResource(fileName), new BrickParser());
        return this;
    }

    public long part1() {
        List<Brick> settledBricks = settleBricks();
        SupportEvaluator supportEvaluator = evaluateSupports(settledBricks);
        return settledBricks.stream().mapToInt(Brick::id).filter(supportEvaluator::isNotUniqueSupporter).count();
    }

    public long part2() {
        List<Brick> settledBricks = settleBricks();
        SupportEvaluator supportEvaluator = evaluateSupports(settledBricks);
        return settledBricks.stream().mapToInt(Brick::id).map(supportEvaluator::countNestedUniqueSupported).sum();
    }

    private List<Brick> settleBricks() {
        int maxZ = bricks.stream().mapToInt(Brick::getMinZ).max().orElse(1);
        List<Brick> settledBricks = bricks;
        for (int z = 2; z <= maxZ; z++) {
            int currentZ = z;
            List<Brick> bricksToSettle = bricks.stream().filter(brick -> brick.getMinZ() == currentZ).toList();
            for (Brick brick : bricksToSettle) {
                settledBricks = settle(brick, settledBricks);
            }
        }
        return settledBricks;
    }

    private SupportEvaluator evaluateSupports(List<Brick> settledBricks) {
        SupportEvaluator supportEvaluator = new SupportEvaluator();
        for (Brick supported : settledBricks) {
            Brick supportedMoved = supported.moveDown(1);
            for (Brick supporter : settledBricks) {
                if (supporter != supported && supportedMoved.collides(supporter)) {
                    supportEvaluator.addSupport(supporter.id(), supported.id());
                }
            }
        }
        return supportEvaluator;
    }

    private List<Brick> settle(Brick brick, List<Brick> settledBricks) {
        int moveDown = brick.getMinZ() - 1;
        for (int down = 1; down < brick.getMinZ(); down++) {
            if (collide(brick, settledBricks, down)) {
                moveDown = down - 1;
                break;
            }
        }
        if (moveDown == 0) {
            return settledBricks;
        }
        List<Brick> result = new ArrayList<>(settledBricks);
        result.remove(brick);
        result.add(brick.moveDown(moveDown));
        return result;
    }

    private boolean collide(Brick brick, List<Brick> bricks, int moveDown) {
        Brick moved = brick.moveDown(moveDown);
        return bricks.stream().filter(b -> b != brick).anyMatch(moved::collides);
    }
}

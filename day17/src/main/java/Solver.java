import common.movement.Position;
import common.parser.IOUtils;
import common.parser.StringUtils;

public class Solver {
    private final String fileName;
    private int[][] map;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        this.map = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                .map(line -> StringUtils.characters(line).mapToInt(c -> Short.parseShort("" + c)).toArray())
                .toArray(int[][]::new);
        return this;
    }

    public int part1() {
        Position startPosition = new Position(0, 0);
        Position targetPosition = new Position(map[0].length - 1, map.length - 1);
        return new DijkstraRoutePlanner(map, 1, 3).calculateDistance(startPosition, targetPosition);
    }

    public int part2() {
        Position startPosition = new Position(0, 0);
        Position targetPosition = new Position(map[0].length - 1, map.length - 1);
        return new DijkstraRoutePlanner(map, 4, 10).calculateDistance(startPosition, targetPosition);
    }
}

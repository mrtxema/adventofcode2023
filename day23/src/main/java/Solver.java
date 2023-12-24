import common.movement.Position;
import common.parser.IOUtils;
import java.util.stream.IntStream;

public class Solver {
    private static final char PATH = '.';
    private final String fileName;
    private char[][] map;
    private Position startPosition;
    private Position endPosition;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        map = IOUtils.readCharMap(getClass().getResource(fileName));
        startPosition = findSymbol(0, PATH);
        endPosition = findSymbol(map.length - 1, PATH);
        return this;
    }

    private Position findSymbol(int y, char symbol) {
        return IntStream.range(0, map[y].length).filter(x -> map[y][x] == symbol).mapToObj(x -> new Position(x, y)).findAny().orElseThrow();
    }

    public int part1() {
        return new LongestPathCalculator(map, true).calculateLongestPath(startPosition, endPosition);
    }

    public int part2() {
        return new GraphBuilder(map, false, startPosition, endPosition).build().getLongestHikeLength();
    }
}

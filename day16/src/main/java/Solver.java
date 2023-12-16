import common.movement.Direction;
import common.movement.Position;
import common.parser.IOUtils;
import common.parser.StringUtils;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solver {
    private final String fileName;
    private Tile[][] map;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        this.map = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                .map(line -> StringUtils.characters(line).map(c -> Tile.parse(c).orElseThrow()).toArray(Tile[]::new))
                .toArray(Tile[][]::new);
        return this;
    }

    public int part1() {
        return countEnergizedTiles(new Beam(new Position(0, 0), Direction.EAST));
    }

    public int part2() {
        int maxY = map.length;
        int maxX = map[0].length;
        Stream<Beam> startingBeams = Stream.of(
                IntStream.range(0, maxY).mapToObj(y -> new Position(0, y)).map(position -> new Beam(position, Direction.EAST)),
                IntStream.range(0, maxY).mapToObj(y -> new Position(maxX - 1, y)).map(position -> new Beam(position, Direction.WEST)),
                IntStream.range(0, maxX).mapToObj(x -> new Position(x, 0)).map(position -> new Beam(position, Direction.SOUTH)),
                IntStream.range(0, maxX).mapToObj(x -> new Position(x, maxY - 1)).map(position -> new Beam(position, Direction.NORTH)))
                .flatMap(Function.identity());
        return startingBeams.mapToInt(this::countEnergizedTiles).max().orElseThrow();
    }

    public int countEnergizedTiles(Beam startingBeam) {
        Set<Beam> processedBeams = new HashSet<>();
        Deque<Beam> pendingBeams = new ArrayDeque<>();
        pendingBeams.addLast(startingBeam);
        Beam currentBeam;
        while ((currentBeam = pendingBeams.pollFirst()) != null) {
            Beam beam = currentBeam;
            if (processedBeams.add(beam)) {
                Tile tile = map[beam.position().y()][beam.position().x()];
                tile.getNewDirections(beam.direction())
                        .map(direction -> new Beam(direction.move(beam.position()), direction))
                        .filter(newBeam -> isInsideLimits(newBeam.position()))
                        .forEach(pendingBeams::addLast);
            }
        }
        return (int) processedBeams.stream().map(Beam::position).distinct().count();
    }

    private boolean isInsideLimits(Position position) {
        return position.x() >= 0 && position.x() < map[0].length && position.y() >= 0 && position.y() < map.length;
    }

    private record Beam(Position position, Direction direction) {
    }
}

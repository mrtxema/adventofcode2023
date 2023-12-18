import common.movement.Position;
import common.parser.IOUtils;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final String fileName;
    private final TrenchParser trenchParser;
    private List<Trench> digMap;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.trenchParser = new TrenchParser();
    }

    public Solver parseFile() {
        digMap = IOUtils.readTrimmedLines(getClass().getResource(fileName), trenchParser);
        return this;
    }

    public long part1() {
        return calculateArea(calculateVertices(digMap));
    }

    public long part2() {
        List<Trench> swappedMap = digMap.stream().map(trenchParser::swap).toList();
        return calculateArea(calculateVertices(swappedMap));
    }

    private List<Position> calculateVertices(List<Trench> map) {
        List<Position> vertices = new ArrayList<>();
        Position position = new Position(0, 0);
        vertices.add(position);
        for (Trench trench : map) {
            position = trench.direction().move(position, trench.length());
            vertices.add(position);
        }
        return vertices;
    }

    long calculateArea(List<Position> vertices) {
        if (!vertices.get(0).equals(vertices.get(vertices.size() - 1))) {
            throw new IllegalStateException("Not closed polygon");
        }
        int n = vertices.size();
        long areaSum = 0;
        long perimeterSum = 0;
        for (int i = 0; i < n; i++) {
            int previousIndex = (i + n - 1) % n;
            int nextIndex = (i + 1) % n;
            areaSum += (long) vertices.get(i).x() * (vertices.get(nextIndex).y() - vertices.get(previousIndex).y());
            perimeterSum += Math.abs(vertices.get(i).x() - vertices.get(nextIndex).x()) + Math.abs(vertices.get(i).y() - vertices.get(nextIndex).y());
        }
        long innerArea = Math.abs(areaSum) / 2;
        long perimeter = perimeterSum / 2;
        return innerArea + perimeter + 1;
    }
}

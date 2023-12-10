import java.awt.Polygon;
import java.util.List;

public class Loop {
    private final List<Position> tiles;
    private final Polygon polygon;

    public Loop(List<Position> tiles) {
        this.tiles = tiles;
        this.polygon = new Polygon(
                tiles.stream().mapToInt(Position::x).toArray(),
                tiles.stream().mapToInt(Position::y).toArray(),
                tiles.size());
    }

    public int countPoints() {
        return tiles.size();
    }

    public boolean isInsidePoint(Position p) {
        return !tiles.contains(p) && polygon.contains(p.x(), p.y());
    }
}

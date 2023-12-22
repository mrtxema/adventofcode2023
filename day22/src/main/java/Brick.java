import java.util.function.Function;

public record Brick(int id, Position3D start, Position3D end) {

    public int getMinZ() {
        return Math.min(start.z(), end.z());
    }

    public boolean collides(Brick other) {
        return axisCollides(other, Position3D::x) && axisCollides(other, Position3D::y) && axisCollides(other, Position3D::z);
    }

    private boolean axisCollides(Brick other, Function<Position3D, Integer> extractor) {
        Range a = Range.from(extractor.apply(start), extractor.apply(end));
        Range b = Range.from(extractor.apply(other.start()), extractor.apply(other.end()));
        return a.collides(b);
    }

    public Brick moveDown(int steps) {
        return new Brick(id, start.moveDown(steps), end.moveDown(steps));
    }

    private record Range(int min, int max) {

        static Range from(int v1, int v2) {
            return new Range(Math.min(v1, v2), Math.max(v1, v2));
        }

        public boolean collides(Range other) {
            return other.min() <= max && other.max() >= min;
        }
    }
}

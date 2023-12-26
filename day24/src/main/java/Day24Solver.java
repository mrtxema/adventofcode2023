import common.parser.IOUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day24Solver {
    private final String fileName;
    private List<Hailstone> hailstones;

    public Day24Solver(String fileName) {
        this.fileName = fileName;
    }

    public Day24Solver parseFile() {
        hailstones = IOUtils.readTrimmedLines(getClass().getResource(fileName), new HailstoneParser());
        return this;
    }

    public int part1(long minPosition, long maxPosition) {
        AtomicInteger intersectionCount = new AtomicInteger(0);
        for (int i = 0; i < hailstones.size() - 1; i++) {
            Hailstone a = hailstones.get(i);
            for (int j = i + 1; j < hailstones.size(); j++) {
                calculateIntersectionPoint(a, hailstones.get(j))
                        .filter(p -> p.x() >= minPosition && p.x() <= maxPosition)
                        .filter(p -> p.y() >= minPosition && p.y() <= maxPosition)
                        .ifPresent(p -> intersectionCount.incrementAndGet());
            }
        }
        return intersectionCount.get();
    }

    private Optional<IntersectionPoint> calculateIntersectionPoint(Hailstone h1, Hailstone h2) {
        double m1 = ((double) h1.velocity().y()) / h1.velocity().x();
        double b1 = h1.position().y() - h1.position().x() * m1;
        double m2 = ((double) h2.velocity().y()) / h2.velocity().x();
        double b2 = h2.position().y() - h2.position().x() * m2;
        if (m1 == m2) {
            return Optional.empty();
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;
        if ((x - h1.position().x()) / h1.velocity().x() < 0) {
            return Optional.empty();
        }
        if ((x - h2.position().x()) / h2.velocity().x() < 0) {
            return Optional.empty();
        }
        return Optional.of(new IntersectionPoint(x, y, 0));
    }

    public long part2() {
        Hailstone h1 = hailstones.get(0);
        Hailstone h2 = hailstones.get(1);
        int amplitude = hailstones.size() < 10 ? 5 : 500;
        Hailstone rock = possibleRockVelocities(amplitude).stream()
                .flatMap(velocity -> deduceThrowingLocation(h1, h2, velocity).stream().map(p -> new Hailstone(p, velocity)))
                .filter(r -> hailstones.stream().allMatch(h -> willCollideWith(r, h)))
                .findFirst()
                .orElseThrow();
        return rock.position().x() + rock.position().y() + rock.position().z();
    }

    private List<Velocity3D> possibleRockVelocities(int amplitude) {
        var velocityRange = new LongRange(-amplitude, amplitude);
        var invalidXRanges = new HashSet<LongRange>();
        var invalidYRanges = new HashSet<LongRange>();
        var invalidZRanges = new HashSet<LongRange>();

        for (int i = 0; i < hailstones.size() - 1; i++) {
            Hailstone h1 = hailstones.get(i);
            for (int j = i + 1; j < hailstones.size(); j++) {
                Hailstone h2 = hailstones.get(j);
                testImpossible(h1.position().x(), h1.velocity().x(), h2.position().x(), h2.velocity().x()).ifPresent(invalidXRanges::add);
                testImpossible(h1.position().y(), h1.velocity().y(), h2.position().y(), h2.velocity().y()).ifPresent(invalidYRanges::add);
                testImpossible(h1.position().z(), h1.velocity().z(), h2.position().z(), h2.velocity().z()).ifPresent(invalidZRanges::add);
            }
        }

        var possibleX = velocityRange.stream().filter(x -> invalidXRanges.stream().noneMatch(it -> it.contains(x))).toList();
        var possibleY = velocityRange.stream().filter(y -> invalidYRanges.stream().noneMatch(it -> it.contains(y))).toList();
        var possibleZ = velocityRange.stream().filter(z -> invalidZRanges.stream().noneMatch(it -> it.contains(z))).toList();

        List<Velocity3D> result = new ArrayList<>();
        for (long vx : possibleX) {
            for (long vy : possibleY) {
                for (long vz : possibleZ) {
                    result.add(new Velocity3D(vx, vy, vz));
                }
            }
        }
        return result;
    }

    private Optional<LongRange> testImpossible(long p0, long v0, long p1, long v1) {
        if (p0 > p1 && v0 > v1) {
            return Optional.of(new LongRange(v1, v0));
        }
        if (p1 > p0 && v1 > v0) {
            return Optional.of(new LongRange(v0, v1));
        }
        return Optional.empty();
    }
    
    private Optional<Position3D> deduceThrowingLocation(Hailstone h1, Hailstone h2, Velocity3D velocity) {
        var h1rvx = h1.velocity().x() - velocity.x();
        var h1rvy = h1.velocity().y() - velocity.y();
        var h2rvx = h2.velocity().x() - velocity.x();
        var h2rvy = h2.velocity().y() - velocity.y();

        var slopeDiff = h1rvx * h2rvy - h1rvy * h2rvx;
        if (slopeDiff == 0L) {
            return Optional.empty();
        }

        long t = (h2rvy * (h2.position().x() - h1.position().x()) - h2rvx * (h2.position().y() - h1.position().y())) / slopeDiff;
        if (t < 0) {
            return Optional.empty();
        }

        return Optional.of(new Position3D(
                h1.position().x() + (h1.velocity().x() - velocity.x()) * t,
                h1.position().y() + (h1.velocity().y() - velocity.y()) * t,
                h1.position().z() + (h1.velocity().z() - velocity.z()) * t));
    }

    private boolean willCollideWith(Hailstone a, Hailstone other) {
        double t = -1;
        if (a.velocity().x() != other.velocity().x()) {
            t = ((double) (other.position().x() - a.position().x())) / (a.velocity().x() - other.velocity().x());
        } else if (a.velocity().y() != other.velocity().y()) {
            t = ((double) (other.position().y() - a.position().y())) / (a.velocity().y() - other.velocity().y());
        } else if (a.velocity().z() != other.velocity().z()) {
            t = ((double) (other.position().z() - a.position().z())) / (a.velocity().z() - other.velocity().z());
        }
        if (t < 0) {
            return false;
        }
        return a.positionAfterTime(t).equals(other.positionAfterTime(t));
    }

    private record LongRange(long min, long max) {

        public boolean contains(long v) {
            return v >= min && v <= max;
        }

        public Stream<Long> stream() {
            return LongStream.rangeClosed(min, max).boxed();
        }
    }
}

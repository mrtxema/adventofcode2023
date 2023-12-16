import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class MinSeedFinder {
    private final Almanac almanac;
    private final List<Range> seedRanges;
    private final AtomicLong location = new AtomicLong(0);

    public MinSeedFinder(Almanac almanac) {
        this.almanac = almanac;
        this.seedRanges = IntStream.range(0, almanac.seeds().size() / 2)
                .map(i -> i * 2)
                .mapToObj(i -> new Range(almanac.seeds().get(i), almanac.seeds().get(i) + almanac.seeds().get(i + 1)))
                .toList();
    }

    public long process() {
        while (true) {
            long current = location.getAndIncrement();
            long seed = almanac.process(current);
            if (current % 1_000_000 == 0) {
                System.out.printf("%,d%n", current);
            }
            if (seedRanges.stream().anyMatch(range -> range.contains(seed))) {
                return current;
            }
        }
    }

    private record Range(long start, long end) {

        public boolean contains(long value) {
            return value >= start && value < end;
        }
    }
}

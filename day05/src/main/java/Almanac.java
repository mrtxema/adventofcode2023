import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

public record Almanac(List<Long> seeds, List<FarmMap> maps) {

    public long getSeedForLowestLocation() {
        int lastMapIndex = maps.size() - 1;
        ArrayDeque<MapAndRange> pendingRanges = new ArrayDeque<>(maps.get(lastMapIndex).getAscendingSortedRanges(lastMapIndex));
        MapAndRange r;
        Result result = new Result();
        while ((r = pendingRanges.pollFirst()) != null) {
            MapAndRange range = r;
            System.out.println("Analyzing " + range + " (pending " + pendingRanges.size() + ")");
            int previousMapIndex = range.mapIndex() - 1;
            if (previousMapIndex < 0) {
                getAllSeeds().filter(seed -> range.range().includes(seed))
                        .forEach(seedResult -> result.store(seedResult, convertSeed(seedResult)));
            } else {
                FarmMap previousMap = maps.get(previousMapIndex);
                previousMap.unconvertRangeDescendingSorted(previousMapIndex, range.range()).forEach(pendingRanges::addFirst);
            }
        }
        if (result.seed == null) {
            throw new IllegalStateException("Didn't find any seed");
        }
        return result.seed;
    }

    private static class Result {
        Long seed;
        Long location;

        void store(long seed, long location) {
            if (this.location == null || location < this.location) {
                this.seed = seed;
                this.location = location;
                System.out.println("BEST RESULT seed " + this.seed + " -> location " + this.location);
            }
        }
    }

    public LongStream getAllSeeds() {
        return LongStream.range(0, seeds.size() / 2).map(n -> n * 2)
                .flatMap(i -> LongStream.range(seeds.get((int) i), seeds.get((int) i) + seeds.get((int) i + 1)));
    }

    public long convertSeed(long seed) {
        long value = seed;
        for (FarmMap map : maps) {
            value = map.convert(value);
        }
        return value;
    }
}

record FarmMap(String name, List<FarmMapRange> ranges) {

    public long convert(long value) {
        return ranges.stream().flatMap(range -> range.convert(value).stream()).findFirst().orElse(value);
    }

    List<MapAndRange> getAscendingSortedRanges(int index) {
        return getContinuousRanges().stream().map(range -> new MapAndRange(index, range)).toList();
    }

    List<MapAndRange> unconvertRangeDescendingSorted(int index, FarmMapRange nextRange) {
        List<FarmMapRange> sortedRanges = getContinuousRanges();
        List<FarmMapRange> result = new ArrayList<>();
        long destination = nextRange.sourceStart();
        while (destination < nextRange.sourceStart() + nextRange.length()) {
            long d = destination;
            FarmMapRange range = sortedRanges.stream().filter(r -> r.destinationIncludes(d)).findFirst()
                    .orElseThrow(() -> new IllegalStateException("No range found in map %d to include destination %d: %s"
                            .formatted(index, d, sortedRanges)));
            result.add(range.destinationIntersect(destination, nextRange.sourceStart() + nextRange.length()));
            destination = range.destinationStart() + range.length();
        }
        return result.stream()
                .sorted(Comparator.comparing(FarmMapRange::destinationStart).reversed())
                .map(r -> new MapAndRange(index, r))
                .toList();
    }

    private List<FarmMapRange> getContinuousRanges() {
        List<FarmMapRange> sortedRanges = ranges.stream().sorted(Comparator.comparing(FarmMapRange::destinationStart)).toList();
        long start = 0;
        List<FarmMapRange> result = new ArrayList<>();
        for (FarmMapRange range : sortedRanges) {
            if (start < range.destinationStart()) {
                result.add(new FarmMapRange(start, start, range.destinationStart()));
            }
            result.add(range);
            start = range.destinationStart() + range.length();
        }
        result.add(new FarmMapRange(start, start, Long.MAX_VALUE - start));
        return result;
    }
}

record FarmMapRange(long destinationStart, long sourceStart, long length) {

    public boolean includes(long source) {
        return source >= sourceStart && source < (sourceStart + length);
    }

    public Optional<Long> convert(long value) {
        return includes(value) ? Optional.of(destinationStart + value - sourceStart) : Optional.empty();
    }

    public boolean destinationIncludes(long destination) {
        return destination >= destinationStart && destination < (destinationStart + length);
    }

    public Optional<FarmMapRange> sourceIntersect(FarmMapRange otherRange) {
        long maxStart = Math.max(sourceStart, otherRange.sourceStart());
        long minEnd = Math.min(sourceStart + length, otherRange.sourceStart() + otherRange.length());
        return maxStart < minEnd ? Optional.of(new FarmMapRange(maxStart, maxStart, minEnd - maxStart)) : Optional.empty();
    }

    public FarmMapRange destinationIntersect(long start, long end) {
        long intersectStart = Math.max(destinationStart, start);
        long intersectEnd = Math.min(destinationStart + length, end);
        long offset = intersectStart - destinationStart;
        return new FarmMapRange(destinationStart + offset, sourceStart + offset, intersectEnd - intersectStart);
    }
}

record MapAndRange(int mapIndex, FarmMapRange range) {
}

import java.util.stream.Stream;

public record State(Range x, Range m, Range a, Range s) {

    public long countCombinations() {
        return Stream.of(x, m, a, s).mapToLong(Range::size).reduce(1, (a, b) -> a * b);
    }

    public Range get(String property) {
        return switch (property) {
            case "x" -> x;
            case "m" -> m;
            case "a" -> a;
            case "s" -> s;
            default -> throw new IllegalArgumentException("Invalid property: " + property);
        };
    }

    public State set(String property, Range range) {
        return switch (property) {
            case "x" -> new State(range, m, a, s);
            case "m" -> new State(x, range, a, s);
            case "a" -> new State(x, m, range, s);
            case "s" -> new State(x, m, a, range);
            default -> throw new IllegalArgumentException("Invalid property: " + property);
        };
    }

    record Range(int min, int max) {

        public long size() {
            return max < min ? 0 : max - min + 1;
        }

        public Range intersect(Range other) {
            return new Range(Math.max(min, other.min()), Math.min(max, other.max()));
        }
    }
}

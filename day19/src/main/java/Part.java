import java.util.Map;

public class Part {
    private final Map<String, Integer> values;

    public Part(Map<String, Integer> values) {
        this.values = values;
    }

    public int getValue(String property) {
        return values.get(property);
    }

    public int totalRating() {
        return values.values().stream().mapToInt(v -> v).sum();
    }
}

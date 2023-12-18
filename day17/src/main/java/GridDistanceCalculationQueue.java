import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GridDistanceCalculationQueue {
    private final Map<State, Integer> distances;
    private final TreeSet<WeightedValue> queue;

    public GridDistanceCalculationQueue() {
        this.distances = new HashMap<>();
        this.queue = new TreeSet<>(WeightedValue.comparator());
    }

    public void add(State state, int distance) {
        distances.put(state, distance);
        queue.add(new WeightedValue(state, distance));
    }

    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    public State pop() {
        WeightedValue value = queue.pollFirst();
        return value != null ? value.value() : null;
    }

    public int getDistance(State state) {
        return distances.getOrDefault(state, Integer.MAX_VALUE / 2);
    }

    private record WeightedValue(State value, int weight) {

        public static Comparator<WeightedValue> comparator() {
            return Comparator.comparing(WeightedValue::weight)
                    .thenComparing(wv -> wv.value().position().x())
                    .thenComparing(wv -> wv.value().position().y())
                    .thenComparing(wv -> wv.value().lastDirection())
                    .thenComparing(wv -> wv.value().lastDirectionCount());
        }
    }
}

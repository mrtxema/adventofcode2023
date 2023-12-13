import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MapPattern {
    private final List<String> rows;
    private final List<String> columns;

    public MapPattern(List<String> rows, List<String> columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public long getReflectionScore() {
        return findReflectionColumnIndex(false)
                .or(() -> findReflectionRowIndex(false).map(index -> index * 100))
                .orElseThrow();
    }

    public long getReflectionScoreWithSmudge() {
        return findReflectionColumnIndex(true)
                .or(() -> findReflectionRowIndex(true).map(index -> index * 100))
                .orElseThrow();
    }

    private Optional<Integer> findReflectionColumnIndex(boolean smudge) {
        return IntStream.range(1, columns.size())
                .filter(i -> isReflection(columns, i, smudge))
                .boxed()
                .findAny();
    }

    private Optional<Integer> findReflectionRowIndex(boolean smudge) {
        return IntStream.range(1, rows.size())
                .filter(i -> isReflection(rows, i, smudge))
                .boxed()
                .findAny();
    }

    private boolean isReflection(List<String> items, int index, boolean smudge) {
        int limit = Math.min(index, items.size() - index);
        int differences = IntStream.range(0, limit).map(i -> countDifferences(items.get(index + i), items.get(index - i - 1))).sum();
        int expectedDifference = smudge ? 1 : 0;
        return differences == expectedDifference;
    }

    private int countDifferences(String item1, String item2) {
        return IntStream.range(0, item1.length()).map(index -> item1.charAt(index) == item2.charAt(index) ? 0 : 1).sum();
    }
}

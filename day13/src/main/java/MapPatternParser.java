import java.util.List;
import java.util.stream.IntStream;

public class MapPatternParser {

    public List<MapPattern> parse(List<String> lines) {
        return StringUtils.groupLines(lines, String::isEmpty).map(this::parsePattern).toList();
    }

    private MapPattern parsePattern(List<String> rows) {
        return new MapPattern(rows, transpose(rows));
    }

    private List<String> transpose(List<String> rows) {
        return IntStream.range(0, rows.get(0).length()).mapToObj(i -> buildColumn(rows, i)).toList();
    }

    private String buildColumn(List<String> rows, int index) {
        StringBuilder builder = new StringBuilder();
        rows.forEach(row -> builder.append(row.charAt(index)));
        return builder.toString();
    }
}

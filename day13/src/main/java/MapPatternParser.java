import common.parser.StringUtils;

import java.util.List;

public class MapPatternParser {

    public List<MapPattern> parse(List<String> lines) {
        return StringUtils.groupLines(lines, String::isEmpty).map(this::parsePattern).toList();
    }

    private MapPattern parsePattern(List<String> rows) {
        return new MapPattern(rows, StringUtils.transpose(rows));
    }
}

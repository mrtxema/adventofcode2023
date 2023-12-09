import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlmanacParser {

    public Almanac parse(List<String> lines) {
        return new Almanac(parseSeeds(lines.get(0)), parseMaps(lines.subList(2, lines.size())));
    }

    private List<Long> parseSeeds(String spec) {
        return parseNumbers(StringUtils.split(spec, ":", true).tail());
    }

    private List<FarmMap> parseMaps(List<String> lines) {
        List<FarmMap> maps = new ArrayList<>();
        List<String> sectionLines = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                if (!sectionLines.isEmpty()) {
                    maps.add(parseMap(sectionLines));
                    sectionLines.clear();
                }
            } else {
                sectionLines.add(line);
            }
        }
        if (!sectionLines.isEmpty()) {
            maps.add(parseMap(sectionLines));
        }
        return maps;
    }

    private FarmMap parseMap(List<String> lines) {
        var name = StringUtils.split(lines.get(0), " ", true).head();
        var ranges = lines.stream().skip(1).map(this::parseNumbers).map(this::parseRange).toList();
        return new FarmMap(name, ranges);
    }

    private FarmMapRange parseRange(List<Long> values) {
        if (values.size() != 3) {
            throw new IllegalArgumentException("Invalid map range: " + values);
        }
        return new FarmMapRange(values.get(0), values.get(1), values.get(2));
    }

    private List<Long> parseNumbers(String s) {
        return Arrays.stream(s.split(" +")).map(Long::valueOf).toList();
    }
}

import common.parser.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RaceSpecsParser {

    public List<Race> parse(List<String> lines) {
        TimesAndDistances timesAndDistances = getTimesAndDistances(lines);
        List<Long> times = parseNumbers(timesAndDistances.times());
        List<Long> distances = parseNumbers(timesAndDistances.distances());
        if (times.size() != distances.size()) {
            throw new IllegalArgumentException("ERR2 - Invalid races spec: " + lines);
        }
        return IntStream.range(0, times.size()).mapToObj(i -> new Race(times.get(i), distances.get(i))).toList();
    }

    public Race parseSingleRace(List<String> lines) {
        TimesAndDistances timesAndDistances = getTimesAndDistances(lines);
        return new Race(parseNumber(timesAndDistances.times()), parseNumber(timesAndDistances.distances()));
    }

    private TimesAndDistances getTimesAndDistances(List<String> lines) {
        List<String> filteredLines = lines.stream().filter(s -> !s.isEmpty()).toList();
        if (filteredLines.size() != 2) {
            throw new IllegalArgumentException("ERR1 - Invalid races spec: " + lines);
        }
        String times = StringUtils.split(filteredLines.get(0), ":", true).tail();
        String distances = StringUtils.split(filteredLines.get(1), ":", true).tail();
        return new TimesAndDistances(times, distances);
    }

    private List<Long> parseNumbers(String s) {
        return Arrays.stream(s.split(" +")).map(Long::valueOf).toList();
    }

    private long parseNumber(String s) {
        return Long.parseLong(s.replaceAll(" +", ""));
    }

    private record TimesAndDistances(String times, String distances) {
    }
}

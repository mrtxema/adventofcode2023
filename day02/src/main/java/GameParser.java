import common.SimpleParser;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class GameParser extends SimpleParser<Game> {

    @Override
    public Game nonNullParse(String line) {
        int index = line.indexOf(':');
        if (index == -1) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        return new Game(
                parseId(line.substring(0, index)),
                Arrays.stream(line.substring(index + 1).split(";")).map(this::parseSubset).toList());
    }

    private int parseId(String label) {
        if (label.length() < 6) {
            throw new IllegalArgumentException("Invalid game id format: " + label);
        }
        return Integer.parseInt(label.substring(5).trim());
    }

    private CubeSubset parseSubset(String spec) {
        Map<Color, Integer> counts = Arrays.stream(spec.split(","))
                .map(String::trim)
                .map(this::parseColorCount)
                .collect(Collectors.toMap(ColorCount::color, ColorCount::count));
        return new CubeSubset(counts);
    }

    private ColorCount parseColorCount(String spec) {
        int index = spec.indexOf(' ');
        if (index == -1) {
            throw new IllegalArgumentException("Invalid color count format: " + spec);
        }
        return new ColorCount(
                Color.parse(spec.substring(index).trim()).orElseThrow(() -> new IllegalArgumentException("Invalid color: " + spec)),
                Integer.parseInt(spec.substring(0, index)));
    }

    private record ColorCount(Color color, int count) {
    }
}

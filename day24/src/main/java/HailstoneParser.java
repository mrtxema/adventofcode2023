import common.parser.SimpleParser;
import common.parser.StringUtils;

import java.util.Arrays;

public class HailstoneParser extends SimpleParser<Hailstone> {

    @Override
    protected Hailstone nonNullParse(String line) {
        var split = StringUtils.split(line, "@", true);
        long[] pos = parseNumbers(split.head());
        long[] vel = parseNumbers(split.tail());
        return new Hailstone(new Position3D(pos[0], pos[1], pos[2]), new Velocity3D(vel[0], vel[1], vel[2]));
    }

    private long[] parseNumbers(String spec) {
        return Arrays.stream(spec.split(",", 3)).map(String::trim).mapToLong(Long::parseLong).toArray();
    }
}

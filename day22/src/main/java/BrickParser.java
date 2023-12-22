import common.parser.SimpleParser;
import common.parser.StringUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BrickParser extends SimpleParser<Brick> {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @Override
    protected Brick nonNullParse(String line) {
        var split = StringUtils.split(line, "~", true);
        return new Brick(COUNTER.incrementAndGet(), parsePosition(split.head()), parsePosition(split.tail()));
    }

    private Position3D parsePosition(String spec) {
        int[] coordinates = Arrays.stream(spec.split(",", 3)).mapToInt(Integer::parseInt).toArray();
        return new Position3D(coordinates[0], coordinates[1], coordinates[2]);
    }
}

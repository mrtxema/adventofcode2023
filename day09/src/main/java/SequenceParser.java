import common.parser.SimpleParser;

import java.util.Arrays;

public class SequenceParser extends SimpleParser<Sequence> {

    @Override
    protected Sequence nonNullParse(String line) {
        return new Sequence(Arrays.stream(line.split(" +")).map(Long::valueOf).toList());
    }
}

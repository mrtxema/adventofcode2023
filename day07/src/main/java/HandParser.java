import common.parser.SimpleParser;
import common.parser.SplitResult;
import common.parser.StringUtils;

public class HandParser extends SimpleParser<Hand> {

    @Override
    protected Hand nonNullParse(String line) {
        SplitResult split = StringUtils.split(line, " ", true);
        return new Hand(parseCards(split.head()), parseNumber(split.tail()));
    }

    private Card[] parseCards(String spec) {
        Card[] result = StringUtils.characters(spec).map(String::valueOf).flatMap(code -> Card.parse(code).stream()).toArray(Card[]::new);
        if (result.length != 5) {
            throw new IllegalArgumentException("Invalid cards hand: " + spec);
        }
        return result;
    }

    private long parseNumber(String spec) {
        return Long.parseLong(spec);
    }
}

import comm.BroadcasterModule;
import comm.CommModule;
import comm.ConjunctionModule;
import comm.FlipFlopModule;
import common.parser.SimpleParser;
import common.parser.StringUtils;
import java.util.Arrays;
import java.util.List;

public class CommModuleParser extends SimpleParser<CommModule> {
    private static final char FLIP_FLOP_SYMBOL = '%';
    private static final char CONJUNCTION_SYMBOL = '&';

    @Override
    protected CommModule nonNullParse(String line) {
        var split = StringUtils.split(line, "->", true);
        List<String> destinations = Arrays.stream(split.tail().split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
        return buildCommModule(line.charAt(0), split.head(), destinations);
    }

    private CommModule buildCommModule(char symbol, String name, List<String> destinations) {
        return switch (symbol) {
            case FLIP_FLOP_SYMBOL -> new FlipFlopModule(name.substring(1), destinations);
            case CONJUNCTION_SYMBOL -> new ConjunctionModule(name.substring(1), destinations);
            default -> new BroadcasterModule(name, destinations);
        };
    }
}

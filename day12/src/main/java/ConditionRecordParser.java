import common.parser.SimpleParser;
import common.parser.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ConditionRecordParser extends SimpleParser<ConditionRecord> {

    @Override
    protected ConditionRecord nonNullParse(String line) {
        var split = StringUtils.split(line, " ", true);
        return new ConditionRecord(split.head(), parseNumbers(split.tail()));
    }

    private List<Integer> parseNumbers(String s) {
        return Arrays.stream(s.split(",")).map(Integer::parseInt).toList();
    }
}

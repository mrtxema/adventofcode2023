import common.SimpleParser;
import common.StringUtils;

import java.util.Arrays;
import java.util.List;

public class CardParser extends SimpleParser<Card> {

    @Override
    protected Card nonNullParse(String line) {
        var idAndNumbers = StringUtils.split(line, ":", true);
        var winningAndMine = StringUtils.split(idAndNumbers.tail(), "|", true);
        return new Card(parseId(idAndNumbers.head()), parseNumbers(winningAndMine.head()), parseNumbers(winningAndMine.tail()));
    }

    private int parseId(String label) {
        return Integer.parseInt(StringUtils.split(label, " ", true).tail());
    }

    private List<Integer> parseNumbers(String numberListSpec) {
        return Arrays.stream(numberListSpec.split(" +")).map(String::trim).map(Integer::parseInt).toList();
    }
}

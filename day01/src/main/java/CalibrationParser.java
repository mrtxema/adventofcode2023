import common.SimpleParser;

import java.util.List;

public class CalibrationParser extends SimpleParser<Integer> {

    @Override
    public Integer nonNullParse(String line) {
        List<Integer> digits = extractAllDigits(line);
        return digits.get(0) * 10 + digits.get(digits.size() - 1);
    }

    protected List<Integer> extractAllDigits(String line) {
        return line.chars().filter(Character::isDigit).map(digit -> Character.digit(digit, 10)).boxed().toList();
    }
}

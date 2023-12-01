import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class EnhancedCalibrationParser extends CalibrationParser {
    private static final List<String> NUMBERS = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    @Override
    protected List<Integer> extractAllDigits(String line) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                result.add(Character.digit(c, 10));
            } else {
                findTextualNumber(line.substring(i)).ifPresent(result::add);
            }
        }
        return result;
    }

    private OptionalInt findTextualNumber(String lineTail) {
        for (int i = 0; i < NUMBERS.size(); i++) {
            String candidate = NUMBERS.get(i);
            if (lineTail.startsWith(candidate)) {
                return OptionalInt.of(i + 1);
            }
        }
        return OptionalInt.empty();
    }
}

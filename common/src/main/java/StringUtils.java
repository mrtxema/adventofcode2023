import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class StringUtils {

    private StringUtils() {
    }

    public static Stream<Character> characters(String s) {
        return IntStream.range(0, s.length()).mapToObj(s::charAt);
    }

    public static SplitResult split(String s, String separator, boolean trimmed) {
        int index = s.indexOf(separator);
        if (index == -1) {
            throw new IllegalArgumentException("Separator '%s' not found in string: %s".formatted(separator, s));
        }
        Function<String, String> mapperFunction = trimmed ? String::trim : Function.identity();
        return new SplitResult(mapperFunction.apply(s.substring(0, index)), mapperFunction.apply(s.substring(index + separator.length())));
    }
}

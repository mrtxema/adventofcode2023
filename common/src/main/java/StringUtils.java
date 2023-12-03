import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class StringUtils {

    private StringUtils() {
    }

    public static Stream<Character> characters(String s) {
        return IntStream.range(0, s.length()).mapToObj(s::charAt);
    }
}

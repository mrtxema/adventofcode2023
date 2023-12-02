import java.util.Arrays;
import java.util.Optional;

public enum Color {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public static Optional<Color> parse(String code) {
        return Arrays.stream(Color.values()).filter(color -> color.code.equals(code)).findAny();
    }
}

import java.util.List;

public class PlatformnParser {

    public Platform parse(List<String> lines) {
        return new Platform(lines.stream().map(String::toCharArray).toArray(char[][]::new));
    }
}

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CubeSubset {
    private final Map<Color, Integer> cubeCounts;

    public CubeSubset(Map<Color, Integer> cubeCounts) {
        this.cubeCounts = cubeCounts.isEmpty() ? new EnumMap<>(Color.class) : new EnumMap<>(cubeCounts);
    }

    public CubeSubset(int redCount, int greenCount, int blueCount) {
        this(Map.of(Color.RED, redCount, Color.GREEN, greenCount, Color.BLUE, blueCount));
    }

    public int getCount(Color color) {
        return cubeCounts.getOrDefault(color, 0);
    }

    public int power() {
        return Arrays.stream(Color.values()).mapToInt(this::getCount).reduce(1, (a, b) -> a * b);
    }

    public boolean isLowerThan(CubeSubset otherSubset) {
        return Arrays.stream(Color.values()).anyMatch(color -> getCount(color) < otherSubset.getCount(color));
    }

    public CubeSubset max(CubeSubset otherSubset) {
        Map<Color, Integer> cubeCounts = Arrays.stream(Color.values())
                .collect(Collectors.toMap(Function.identity(), color -> Math.max(getCount(color), otherSubset.getCount(color))));
        return new CubeSubset(cubeCounts);
    }
}

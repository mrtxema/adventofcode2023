import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConditionRecord {
    private static final char OPERATIONAL = '.';
    private static final char DAMAGED = '#';
    private static final char UNKNOWN = '?';
    private final String conditions;
    private final List<Integer> damagedGroupCounts;

    public ConditionRecord(String conditions, List<Integer> damagedGroupCounts) {
        this.conditions = conditions;
        this.damagedGroupCounts = damagedGroupCounts;
    }

    public long countArrangements() {
        return countValidArrangements(conditions, damagedGroupCounts, new HashMap<>());
    }

    private long countValidArrangements(String remainingSpec, List<Integer> remainingGroups, Map<Key, Long> cache) {
        Key key = new Key(remainingSpec, remainingGroups);
        Long cachedResult = cache.get(key);
        if (cachedResult != null) {
            return cachedResult;
        }
        long calculatedResult = countValidArrangementsInternal(remainingSpec, remainingGroups, cache);
        cache.put(key, calculatedResult);
        return calculatedResult;
    }

    private long countValidArrangementsInternal(String remainingSpec, List<Integer> remainingGroups, Map<Key, Long> cache) {
        if (remainingSpec.isEmpty()) {
            return remainingGroups.isEmpty() ? 1 : 0;
        }
        if (remainingGroups.isEmpty()) {
            return remainingSpec.indexOf(DAMAGED) == -1 ? 1 : 0;
        }
        int minRequiredChars = remainingGroups.stream().mapToInt(i -> i).sum() + remainingGroups.size() - 1;
        if (remainingSpec.length() < minRequiredChars) {
            return 0;
        }
        char first = remainingSpec.charAt(0);
        if (first == OPERATIONAL) {
            return countValidArrangements(trim(remainingSpec), remainingGroups, cache);
        }
        if (first == DAMAGED) {
            int firstGroupSize = remainingGroups.get(0);
            int maxGroupSize = Optional.of(remainingSpec.indexOf(OPERATIONAL)).filter(idx -> idx != -1).orElse(remainingSpec.length());
            if (maxGroupSize < firstGroupSize) {
                return 0;
            }
            if (remainingSpec.length() == firstGroupSize) {
                return remainingGroups.size() == 1 ? 1 : 0;
            }
            if (remainingSpec.charAt(firstGroupSize) == DAMAGED) {
                return 0;
            }
            return countValidArrangements(trim(remainingSpec.substring(firstGroupSize + 1)),
                    remainingGroups.subList(1, remainingGroups.size()), cache);
        }
        long asop = countValidArrangements(trim(remainingSpec.substring(1)), remainingGroups, cache);
        long asdam = countValidArrangements(DAMAGED + remainingSpec.substring(1), remainingGroups, cache);
        return asop + asdam;
    }

    private String trim(String s) {
        char c = OPERATIONAL;
        int startIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != c) {
                startIndex = i;
                break;
            }
        }
        int endIndex = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != c) {
                endIndex = i + 1;
                break;
            }
        }
        return startIndex == -1 || endIndex == -1 ? "" : s.substring(startIndex, endIndex);
    }

    public ConditionRecord unfold() {
        String unfoldedConditions = IntStream.range(0, 5).mapToObj(i -> conditions).collect(Collectors.joining("" + UNKNOWN));
        List<Integer> unfoldedCounts = IntStream.range(0, 5).boxed().flatMap(i -> damagedGroupCounts.stream()).toList();
        return new ConditionRecord(unfoldedConditions, unfoldedCounts);
    }

    private record Key(String spec, List<Integer> groups) {
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SupportEvaluator {
    private final Map<Integer, List<Support>> supportsBySupporter = new HashMap<>();
    private final Map<Integer, List<Support>> supportsBySupported = new HashMap<>();

    public void addSupport(int supporterId, int supportedId) {
        Support support = new Support(supporterId, supportedId);
        supportsBySupporter.computeIfAbsent(supporterId, x -> new ArrayList<>()).add(support);
        supportsBySupported.computeIfAbsent(supportedId, x -> new ArrayList<>()).add(support);
    }

    public boolean isNotUniqueSupporter(int id) {
        return supportsBySupporter.getOrDefault(id, List.of()).stream().map(Support::supportedId)
                .allMatch(supported -> getSupporters(supported).size() > 1);
    }

    public int countNestedUniqueSupported(int id) {
        Set<Integer> fallen = new HashSet<>(Set.of(id));
        while (true) {
            Set<Integer> newFallen = fallen.stream()
                    .flatMap(fallenId -> supportsBySupporter.getOrDefault(fallenId, List.of()).stream())
                    .map(Support::supportedId)
                    .filter(supported -> fallen.containsAll(getSupporters(supported)))
                    .collect(Collectors.toSet());
            boolean changed = fallen.addAll(newFallen);
            if (!changed) {
                return fallen.size() - 1;
            }
        }
    }

    private Set<Integer> getSupporters(int id) {
        return supportsBySupported.getOrDefault(id, List.of()).stream().map(Support::supporterId).collect(Collectors.toSet());
    }

    private record Support(int supporterId, int supportedId) {
    }
}

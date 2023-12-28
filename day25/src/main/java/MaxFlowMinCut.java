import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaxFlowMinCut {
    private final Set<String> vertices;
    private final Map<String, Set<String>> adjacencies;

    public MaxFlowMinCut(Diagram diagram) {
        this.vertices = diagram.getNodes();
        this.adjacencies = diagram.getConnections().stream()
                .flatMap(c -> Stream.of(c, c.reverse()))
                .collect(Collectors.groupingBy(Diagram.Connection::a, Collectors.mapping(Diagram.Connection::b, Collectors.toSet())));
    }

    public MaxFlowResult maxFlowMinCut(String source, String destination) {
        int pathFlow;
        Map<String, Map<String, Integer>> residualGraph = new HashMap<>();
        for (String s : vertices) {
            Map<String, Integer> sub = new HashMap<>();
            residualGraph.put(s, sub);
            for (String d : vertices) {
                sub.put(d, areAdjacent(s, d) ? 1 : 0);
            }
        }

        /*max flow*/
        int maxFlow = 0;
        while (true) {
            BfsResult bfsResult = bfs(source, destination, residualGraph);
            if (!bfsResult.reached()) {
                break;
            }
            Map<String, String> parents = bfsResult.parents();
            pathFlow = Integer.MAX_VALUE;
            for (String v = destination; !v.equals(source); v = parents.get(v)) {
                String u = parents.get(v);
                pathFlow = Math.min(pathFlow, residualGraph.get(u).get(v));
            }
            int newPathFlow = pathFlow;
            for (String v = destination; !v.equals(source); v = parents.get(v)) {
                String u = parents.get(v);
                residualGraph.get(u).computeIfPresent(v, (key, flow) -> flow - newPathFlow);
                residualGraph.get(v).computeIfPresent(u, (key, flow) -> flow + newPathFlow);
            }
            maxFlow += pathFlow;
        }
        return new MaxFlowResult(source, maxFlow, residualGraph);
    }

    public MinCut getMinCut(MaxFlowResult maxFlowResult) {
        Set<String> reachable = new HashSet<>();
        Set<String> unreachable = new HashSet<>();
        for (String vertex : vertices) {
            boolean reached = bfs(maxFlowResult.source(), vertex, maxFlowResult.residualGraph()).reached();
            Set<String> targetSet = reached ? reachable : unreachable;
            targetSet.add(vertex);
        }
        return new MinCut(reachable, unreachable);
    }

    private boolean areAdjacent(String s, String d) {
        return adjacencies.getOrDefault(s, Set.of()).contains(d);
    }

    private BfsResult bfs(String source, String goal, Map<String, Map<String, Integer>> graph) {
        Map<String, String> parents = new HashMap<>();
        Set<String> visited = new HashSet<>(Set.of(source));
        Queue<String> queue = new LinkedList<>(List.of(source));
        while (!queue.isEmpty()) {
            String element = queue.remove();
            for (String destination : vertices) {
                if (graph.get(element).get(destination) > 0 &&  !visited.contains(destination)) {
                    parents.put(destination, element);
                    queue.add(destination);
                    visited.add(destination);
                }
            }
        }
        return new BfsResult(visited.contains(goal), parents);
    }

    private record BfsResult(boolean reached, Map<String, String> parents) {
    }

    public record MaxFlowResult(String source, int maxFlow, Map<String, Map<String, Integer>> residualGraph) {
    }

    public record MinCut(Set<String> groupA, Set<String> groupB) {
    }
}

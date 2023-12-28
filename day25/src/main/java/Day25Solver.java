import common.parser.IOUtils;
import java.util.LinkedList;
import java.util.Queue;

public class Day25Solver {
    private final String fileName;
    private Diagram diagram;

    public Day25Solver(String fileName) {
        this.fileName = fileName;
    }

    public Day25Solver parseFile() {
        diagram = new DiagramParser().parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        return this;
    }

    public int part1() {
        MaxFlowMinCut maxFlowMinCut = new MaxFlowMinCut(diagram);
        Queue<String> pendingNodes = new LinkedList<>(diagram.getNodes());
        String source = pendingNodes.poll();
        while (!pendingNodes.isEmpty()) {
            String destination = pendingNodes.poll();
            var result = maxFlowMinCut.maxFlowMinCut(source, destination);
            if (result.maxFlow() == 3) {
                var minCut = maxFlowMinCut.getMinCut(result);
                return minCut.groupA().size() * minCut.groupB().size();
            }
        }
        throw new IllegalStateException("No min cut found");
    }
}

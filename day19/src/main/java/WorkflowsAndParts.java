import java.util.List;
import java.util.Map;

public record WorkflowsAndParts(Map<String, Workflow> workflows, List<Part> parts) {
}

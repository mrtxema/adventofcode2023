import java.util.List;

public record Workflow(String name, List<WorkflowRule> rules) {
}

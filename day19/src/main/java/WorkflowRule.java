import java.util.List;

public class WorkflowRule {
    private static final WorkflowRuleCondition EMPTY_CONDITION = new EmptyWorkflowRuleCondition();
    private static final String ACCEPTED = "A";
    private static final String REJECTED = "R";
    private final WorkflowRuleCondition condition;
    private final String destination;

    public WorkflowRule(String destination) {
        this(EMPTY_CONDITION, destination);
    }

    public WorkflowRule(WorkflowRuleCondition condition, String destination) {
        this.condition = condition;
        this.destination = destination;
    }

    public WorkflowRuleCondition getCondition() {
        return condition;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isAcceptRule() {
        return destination.equals(ACCEPTED);
    }

    public boolean isRejectRule() {
        return destination.equals(REJECTED);
    }

    public boolean hasEmptyCondition() {
        return condition == EMPTY_CONDITION;
    }

    private static class EmptyWorkflowRuleCondition implements WorkflowRuleCondition {

        @Override
        public boolean accepts(Part part) {
            return true;
        }

        @Override
        public List<State> splitState(State state) {
            return List.of(state);
        }
    }
}

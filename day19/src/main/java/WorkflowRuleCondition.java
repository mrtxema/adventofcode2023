import java.util.List;

public interface WorkflowRuleCondition {

    boolean accepts(Part part);

    List<State> splitState(State state);
}

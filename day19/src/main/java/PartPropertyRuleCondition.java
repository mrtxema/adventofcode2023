import java.util.List;
import java.util.stream.Stream;

public class PartPropertyRuleCondition implements WorkflowRuleCondition {
    private final String property;
    private final char operator;
    private final int value;

    public PartPropertyRuleCondition(String property, char operator, int value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean accepts(Part part) {
        int partValue = part.getValue(property);
        return switch (operator) {
            case '<' -> partValue < value;
            case '>' -> partValue > value;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    @Override
    public List<State> splitState(State state) {
        Stream<State.Range> ranges = switch (operator) {
            case '<' -> Stream.of(new State.Range(1, value - 1), new State.Range(value, 4000));
            case '>' -> Stream.of(new State.Range(value + 1, 4000), new State.Range(1, value));
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
        State.Range currentRange = state.get(property);
        return ranges.map(currentRange::intersect).map(newRange -> state.set(property, newRange)).toList();
    }
}

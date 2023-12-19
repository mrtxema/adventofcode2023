import common.parser.IOUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solver {
    private static final String START_WORKFLOW = "in";
    private final String fileName;
    private Map<String, Workflow> workflows;
    private List<Part> parts;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        var workflowsAndParts = new WorkflowsAndPartsParser().parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        workflows = workflowsAndParts.workflows();
        parts = workflowsAndParts.parts();
        return this;
    }

    public int part1() {
        return parts.stream().filter(this::isPartAccepted).mapToInt(Part::totalRating).sum();
    }

    private boolean isPartAccepted(Part part) {
        String nextWorkflowName = START_WORKFLOW;
        while (true) {
            Workflow workflow = workflows.get(nextWorkflowName);
            for (WorkflowRule rule : workflow.rules()) {
                if (rule.getCondition().accepts(part)) {
                    if (rule.isAcceptRule()) {
                        return true;
                    }
                    if (rule.isRejectRule()) {
                        return false;
                    }
                    nextWorkflowName = rule.getDestination();
                    break;
                }
            }
        }
    }

    public long part2() {
        ArrayDeque<StateAndWorkflow> queue = new ArrayDeque<>();
        queue.addLast(new StateAndWorkflow(
                new State(new State.Range(1, 4000), new State.Range(1, 4000), new State.Range(1, 4000), new State.Range(1, 4000)),
                START_WORKFLOW));
        List<State> acceptedStates = new ArrayList<>();
        StateAndWorkflow currentStateAndWorkflow;
        while ((currentStateAndWorkflow = queue.pollFirst()) != null) {
            Workflow workflow = workflows.get(currentStateAndWorkflow.workflowName());
            State currentState = currentStateAndWorkflow.state();
            for (WorkflowRule rule : workflow.rules()) {
                List<State> states = rule.getCondition().splitState(currentState);
                if (states.get(0).countCombinations() > 0) {
                    if (rule.isAcceptRule()) {
                        acceptedStates.add(states.get(0));
                    } else if (!rule.isRejectRule()) {
                        queue.addLast(new StateAndWorkflow(states.get(0), rule.getDestination()));
                    }
                }
                if (states.size() == 1 || states.get(1).countCombinations() == 0) {
                    break;
                }
                currentState = states.get(1);
            }
        }
        return acceptedStates.stream().mapToLong(State::countCombinations).sum();
    }

    private record StateAndWorkflow(State state, String workflowName) {
    }
}

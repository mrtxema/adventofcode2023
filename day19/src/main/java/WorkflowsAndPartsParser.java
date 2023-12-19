import common.parser.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WorkflowsAndPartsParser {
    public WorkflowsAndParts parse(List<String> lines) {
        List<List<String>> groups = StringUtils.groupLines(lines, String::isEmpty).filter(group -> !group.isEmpty()).toList();
        if (groups.size() != 2) {
            throw new IllegalArgumentException("Invalid file format. %d groups of data found".formatted(groups.size()));
        }
        return new WorkflowsAndParts(parseWorkflows(groups.get(0)), parseParts(groups.get(1)));
    }

    private Map<String, Workflow> parseWorkflows(List<String> lines) {
        return lines.stream().map(this::parseWorkflow).collect(Collectors.toMap(Workflow::name, Function.identity()));
    }

    private Workflow parseWorkflow(String spec) {
        var split = StringUtils.split(spec, "{", true);
        if (!split.tail().endsWith("}")) {
            throw new IllegalArgumentException("Invalid workflow spec: " + spec);
        }
        String rulesSpec = split.tail().substring(0, split.tail().length() - 1);
        List<WorkflowRule> rules = Arrays.stream(rulesSpec.split(",")).map(String::trim).map(this::parseRule).toList();
        if (rules.subList(0, rules.size() - 1).stream().anyMatch(WorkflowRule::hasEmptyCondition)) {
            throw new IllegalArgumentException("Found non final rules without conditions: " + spec);
        }
        if (!rules.get(rules.size() - 1).hasEmptyCondition()) {
            throw new IllegalArgumentException("Found final rule with conditions: " + spec);
        }
        return new Workflow(split.head(), rules);
    }

    private WorkflowRule parseRule(String spec) {
        int index = spec.indexOf(':');
        if (index == -1) {
            return new WorkflowRule(spec);
        }
        var split = StringUtils.split(spec, ":", true);
        return new WorkflowRule(parseCondition(split.head()), split.tail());
    }

    private PartPropertyRuleCondition parseCondition(String spec) {
        return new PartPropertyRuleCondition(spec.substring(0, 1), spec.charAt(1), Integer.parseInt(spec.substring(2)));
    }

    private List<Part> parseParts(List<String> lines) {
        return lines.stream().map(this::parsePart).toList();
    }

    private Part parsePart(String spec) {
        if (spec.charAt(0) != '{' || spec.charAt(spec.length() - 1) != '}') {
            throw new IllegalArgumentException("Invalid part spec: " + spec);
        }
        Map<String, Integer> values = Arrays.stream(spec.substring(1, spec.length() -1).split(","))
                .map(kvSpec -> kvSpec.split("=", 2))
                .collect(Collectors.toMap(kv -> kv[0].trim(), kv -> Integer.valueOf(kv[1].trim())));
        return new Part(values);
    }
}

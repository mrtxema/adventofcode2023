import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Solver {
    private static final char REMOVE = '-';
    private static final char PUT = '=';
    private final String fileName;
    private final HashService hashService;
    private List<String> steps;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.hashService = new HashService();
    }

    public Solver parseFile() {
        this.steps = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .toList();
        return this;
    }

    public int part1() {
        return steps.stream().mapToInt(hashService::hash).sum();
    }

    public int part2() {
        Map<String, Integer>[] boxes = initBoxes();
        var parsedSteps = steps.stream().map(this::parseStep).toList();
        for (Step step : parsedSteps) {
            Map<String, Integer> box = boxes[hashService.hash(step.label())];
            if (step.operation() == REMOVE) {
                box.remove(step.label());
            }
            if (step.operation() == PUT) {
                box.put(step.label(), step.focalLength());
            }
        }
        return IntStream.range(0, boxes.length)
                .map(boxNumber -> calculateBoxFocusingPower(boxes[boxNumber], boxNumber))
                .sum();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer>[] initBoxes() {
        Map<String, Integer>[] boxes = new Map[256];
        IntStream.range(0, boxes.length).forEach(i -> boxes[i] = new LinkedHashMap<>());
        return boxes;
    }

    private int calculateBoxFocusingPower(Map<String, Integer> box, int boxNumber) {
        int slotNumber = 1;
        int sum = 0;
        for (Map.Entry<String, Integer> entry : box.entrySet()) {
            sum += (boxNumber + 1) * slotNumber++ * entry.getValue();
        }
        return sum;
    }

    private Step parseStep(String spec) {
        if (spec.charAt(spec.length() - 1) == REMOVE) {
            return new Step(spec.substring(0, spec.length() - 1), REMOVE, null);
        } else {
            var split = StringUtils.split(spec, "" + PUT, true);
            return new Step(split.head(), PUT, Integer.valueOf(split.tail()));
        }
    }

    private record Step(String label, char operation, Integer focalLength) {
    }
}

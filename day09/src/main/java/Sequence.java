import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Sequence {
    private final List<Long> numbers;

    public Sequence(List<Long> numbers) {
        this.numbers = numbers;
    }

    public long extrapolateNextValue() {
        List<List<Long>> sequences = generateSequences();
        long lastAddedValue = 0;
        int index = sequences.size() - 2;
        while (index >= 0) {
            List<Long> currentSequence = sequences.get(index);
            lastAddedValue = currentSequence.get(currentSequence.size() - 1) + lastAddedValue;
            index--;
        }
        return lastAddedValue;
    }

    public long extrapolatePreviousValue() {
        List<List<Long>> sequences = generateSequences();
        long lastAddedValue = 0;
        int index = sequences.size() - 2;
        while (index >= 0) {
            List<Long> currentSequence = sequences.get(index);
            lastAddedValue = currentSequence.get(0) - lastAddedValue;
            index--;
        }
        return lastAddedValue;
    }

    private List<List<Long>> generateSequences() {
        List<List<Long>> sequences = new ArrayList<>();
        List<Long> lastSequence = numbers;
        sequences.add(numbers);
        while (lastSequence.stream().anyMatch(n -> n != 0)) {
            List<Long> s = lastSequence;
            lastSequence = IntStream.range(1, s.size()).mapToLong(index -> s.get(index) - s.get(index - 1)).boxed().toList();
            sequences.add(lastSequence);
        }
        return sequences;
    }
}

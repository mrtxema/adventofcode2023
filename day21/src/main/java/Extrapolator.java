import java.util.ArrayList;
import java.util.List;

public class Extrapolator {
    private static final int CYCLE = 262;
    private static final int INDEX = 65;
    private final int size;
    private final List<Long> totals = new ArrayList<>();
    private final List<Long> deltas = new ArrayList<>();
    private final List<Long> deltaDeltas = new ArrayList<>();
    private long totalReached = 0;

    public Extrapolator(int size) {
        this.size = size;
    }

    public int size() {
        return size;
    }

    public boolean registerResults(int steps, int newPositions) {
        if (steps % 2 == 1) {
            totalReached += newPositions;
            if (steps % CYCLE == INDEX) {
                totals.add(totalReached);
                int currTotals = totals.size();
                if (currTotals > 1) {
                    deltas.add(totals.get(currTotals - 1) - totals.get(currTotals - 2));
                }
                int currDeltas = deltas.size();
                if (currDeltas > 1) {
                    deltaDeltas.add(deltas.get(currDeltas - 1) - deltas.get(currDeltas - 2));
                }
                return deltaDeltas.size() > 1;
            }
        }
        return false;
    }

    public long extrapolate(long steps) {
        long neededLoopCount = steps / CYCLE - 1;
        long currentLoopCount = size / CYCLE - 1;
        long deltaLoopCount = neededLoopCount - currentLoopCount;
        long deltaLoopCountTriangular = (neededLoopCount * (neededLoopCount + 1)) / 2 - (currentLoopCount * (currentLoopCount + 1)) / 2;
        long deltaDelta = deltaDeltas.get(deltaDeltas.size() - 1);
        long initialDelta = deltas.get(0);
        return deltaDelta * deltaLoopCountTriangular + initialDelta * deltaLoopCount + totalReached;
    }
}

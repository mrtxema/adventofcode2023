import java.util.ArrayList;
import java.util.List;

public class Extrapolator {
    private static final int CYCLE = 262;
    private final int analyzedSteps;
    private final long totalSteps;
    private final int totalStepsModulo;
    private final List<Long> totals = new ArrayList<>();
    private final List<Long> deltas = new ArrayList<>();
    private final List<Long> deltaDeltas = new ArrayList<>();
    private long totalReached = 0;

    public Extrapolator(int analyzedSteps, long totalSteps) {
        this.analyzedSteps = analyzedSteps;
        this.totalSteps = totalSteps;
        this.totalStepsModulo = (int) (totalSteps % CYCLE);
    }

    public int getAnalyzedSteps() {
        return analyzedSteps;
    }

    public boolean registerResults(int steps, int newPositions) {
        if (steps % 2 == 1) {
            totalReached += newPositions;
            if (steps % CYCLE == totalStepsModulo) {
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

    public long extrapolate() {
        long neededLoopCount = totalSteps / CYCLE - 1;
        long currentLoopCount = analyzedSteps / CYCLE - 1;
        long deltaLoopCount = neededLoopCount - currentLoopCount;
        long deltaLoopCountTriangular = (neededLoopCount * (neededLoopCount + 1)) / 2 - (currentLoopCount * (currentLoopCount + 1)) / 2;
        long deltaDelta = deltaDeltas.get(deltaDeltas.size() - 1);
        long initialDelta = deltas.get(0);
        return deltaDelta * deltaLoopCountTriangular + initialDelta * deltaLoopCount + totalReached;
    }
}

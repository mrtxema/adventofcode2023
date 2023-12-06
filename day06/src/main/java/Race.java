import java.util.List;
import java.util.stream.LongStream;

public class Race {
    private final long time;
    private final long distanceRecord;

    public Race(long time, long distanceRecord) {
        this.time = time;
        this.distanceRecord = distanceRecord;
    }

    public List<Long> getHoldTimesToWin() {
        return LongStream.rangeClosed(1, time - 1).filter(holdTime -> calculateDistance(holdTime) > distanceRecord).boxed().toList();
    }

    private long calculateDistance(long holdTime) {
        return (time - holdTime) * holdTime;
    }
}

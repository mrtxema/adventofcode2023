package comm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConjunctionModule extends BaseCommModule {
    private final Map<String, Pulse> lastPulse;

    public ConjunctionModule(String name, List<String> destinations) {
        super(name, destinations);
        this.lastPulse = new HashMap<>();
    }

    @Override
    public void connectSourceModule(String sourceModuleName) {
        super.connectSourceModule(sourceModuleName);
        lastPulse.putIfAbsent(sourceModuleName, Pulse.LOW);
    }

    @Override
    public List<SentPulse> process(String sourceModule, Pulse pulse) {
        lastPulse.put(sourceModule, pulse);
        Pulse toSend = lastPulse.values().stream().allMatch(p -> p == Pulse.HIGH) ? Pulse.LOW : Pulse.HIGH;
        return getDestinations().stream().map(destination -> new SentPulse(getName(), toSend, destination)).toList();
    }
}

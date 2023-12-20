package comm;

import java.util.List;

public class FlipFlopModule extends BaseCommModule {
    private boolean state;

    public FlipFlopModule(String name, List<String> destinations) {
        super(name, destinations);
        this.state = false;
    }

    @Override
    public List<SentPulse> process(String sourceModule, Pulse pulse) {
        if (pulse == Pulse.LOW) {
            state = !state;
            Pulse toSend = state ? Pulse.HIGH : Pulse.LOW;
            return getDestinations().stream().map(destination -> new SentPulse(getName(), toSend, destination)).toList();
        }
        return List.of();
    }
}

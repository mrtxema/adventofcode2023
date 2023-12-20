package comm;

import java.util.List;

public class BroadcasterModule extends BaseCommModule {

    public BroadcasterModule(String name, List<String> destinations) {
        super(name, destinations);
    }

    @Override
    public List<SentPulse> process(String sourceModule, Pulse pulse) {
        return getDestinations().stream().map(destination -> new SentPulse(getName(), pulse, destination)).toList();
    }
}

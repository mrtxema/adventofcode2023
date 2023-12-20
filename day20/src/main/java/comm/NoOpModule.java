package comm;

import java.util.List;

public class NoOpModule extends BaseCommModule {

    public NoOpModule(String name) {
        super(name, List.of());
    }

    @Override
    public List<SentPulse> process(String sourceModule, Pulse pulse) {
        return List.of();
    }
}

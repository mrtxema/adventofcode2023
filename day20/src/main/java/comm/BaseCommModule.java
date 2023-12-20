package comm;

import java.util.ArrayList;
import java.util.List;

abstract class BaseCommModule implements CommModule {
    private final String name;
    private final List<String> sources;
    private final List<String> destinations;

    protected BaseCommModule(String name, List<String> destinations) {
        this.name = name;
        this.sources = new ArrayList<>();
        this.destinations = destinations;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void connectSourceModule(String sourceModuleName) {
        sources.add(sourceModuleName);
    }

    @Override
    public List<String> getSources() {
        return sources;
    }

    @Override
    public List<String> getDestinations() {
        return destinations;
    }
}

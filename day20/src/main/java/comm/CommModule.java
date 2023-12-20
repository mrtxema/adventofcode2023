package comm;

import java.util.List;

public interface CommModule {

    String getName();

    List<String> getSources();

    List<String> getDestinations();

    List<SentPulse> process(String sourceModule, Pulse pulse);

    void connectSourceModule(String sourceModuleName);
}

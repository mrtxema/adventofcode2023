import comm.CommModule;
import comm.ConjunctionModule;
import comm.NoOpModule;
import comm.Pulse;
import comm.SentPulse;
import common.math.MathUtils;
import common.parser.IOUtils;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20Solver {
    private static final String BUTTON_MODULE = "button";
    private static final String START_MODULE = "broadcaster";
    private static final String END_MODULE = "rx";
    private final String fileName;
    private Map<String, CommModule> modules;

    public Day20Solver(String fileName) {
        this.fileName = fileName;
    }

    public int part1() {
        parseFile();
        Map<Pulse, AtomicInteger> counters = new EnumMap<>(Pulse.class);
        Arrays.stream(Pulse.values()).forEach(p -> counters.put(p, new AtomicInteger(0)));
        Consumer<SentPulse> pulseListener = sentPulse -> counters.get(sentPulse.pulse()).incrementAndGet();
        IntStream.range(0, 1000).forEach(i -> pushButton(pulseListener));
        return counters.values().stream().mapToInt(AtomicInteger::get).reduce(1, (a, b) -> a * b);
    }

    public long part2() {
        parseFile();
        List<String> lastSources = modules.get(END_MODULE).getSources();
        if (lastSources.size() != 1) {
            throw new IllegalStateException();
        }
        CommModule previousModule = modules.get(lastSources.get(0));
        if (!(previousModule instanceof ConjunctionModule)) {
            throw new IllegalStateException();
        }

        AtomicLong counter = new AtomicLong(0);
        Map<String, Long> cycles = new HashMap<>();
        Consumer<SentPulse> pulseListener = p -> {
            if (p.pulse() == Pulse.HIGH && p.destinationModule().equals(previousModule.getName())) {
                cycles.putIfAbsent(p.sourceModule(), counter.get());
            }
        };
        while (cycles.size() < previousModule.getSources().size()) {
            counter.incrementAndGet();
            pushButton(pulseListener);
        }
        return cycles.values().stream().mapToLong(l -> l).reduce(1, MathUtils::lcm);
    }

    private void parseFile() {
        modules = IOUtils.readTrimmedLines(getClass().getResource(fileName), new CommModuleParser()).stream()
                .collect(Collectors.toMap(CommModule::getName, Function.identity()));
        List<String> missingModules = modules.values().stream()
                .flatMap(m -> m.getDestinations().stream())
                .filter(m -> !modules.containsKey(m))
                .toList();
        missingModules.forEach(missing -> modules.put(missing, new NoOpModule(missing)));
        modules.values().forEach(sourceModule -> sourceModule.getDestinations().stream()
                .map(modules::get).forEach(dest -> dest.connectSourceModule(sourceModule.getName())));
    }

    private void pushButton(Consumer<SentPulse> pulseListener) {
        Deque<SentPulse> pendingPulses = new ArrayDeque<>();
        pendingPulses.addLast(new SentPulse(BUTTON_MODULE, Pulse.LOW, START_MODULE));
        SentPulse currentPulse;
        while ((currentPulse = pendingPulses.pollFirst()) != null) {
            modules.get(currentPulse.destinationModule()).process(currentPulse.sourceModule(), currentPulse.pulse())
                    .forEach(pendingPulses::addLast);
            pulseListener.accept(currentPulse);
        }
    }
}

import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public long part1() {
        Almanac almanac = Almanac.parse(readFile(), false);
        return almanac.seeds().stream().mapToLong(almanac::process).min().orElseThrow();
    }

    public long part2() {
        Almanac almanac = Almanac.parse(readFile(), true);
        return new MinSeedFinder(almanac).process();
    }

    private List<String> readFile() {
        return IOUtils.readTrimmedLines(getClass().getResource(fileName));
    }
}

import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private List<String> lines;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        this.lines = IOUtils.readTrimmedLines(getClass().getResource(fileName));
        return this;
    }

    public int part1() {
        return 0;
    }

    public int part2() {
        return 0;
    }
}

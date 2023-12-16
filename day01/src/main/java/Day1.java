import common.parser.IOUtils;
import common.parser.Parser;

import java.util.List;

public class Day1 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day1().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String file = INPUT_FILE_NAME;
        printSum(1, readCalibrations(file, new CalibrationParser()));
        printSum(2, readCalibrations(file, new EnhancedCalibrationParser()));
    }

    private void printSum(int part, List<Integer> calibrations) {
        int sum = calibrations.stream().mapToInt(c -> c).sum();
        System.out.printf("[Part %d] Calibrations sum: %d%n", part, sum);
    }

    private List<Integer> readCalibrations(String fileName, Parser<Integer> parser) {
        return IOUtils.readTrimmedLines(getClass().getResource(fileName), parser);
    }
}

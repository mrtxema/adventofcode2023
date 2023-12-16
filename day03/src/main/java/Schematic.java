import common.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Schematic {
    private static final char EMPTY_CHAR = '.';
    private static final char GEAR_CHAR = '*';
    private final List<String> lines;

    private Schematic(List<String> lines) {
        this.lines = lines;
    }

    public static Schematic parse(List<String> lines) {
        return new Schematic(lines);
    }

    public List<Integer> getPartNumbers() {
        return getPartNumbersInternal()
                .map(PositionedNumber::number)
                .toList();
    }

    public List<Integer> getGearRatios() {
        List<PositionedNumber> partNumbers = getPartNumbersInternal().toList();
        return IntStream.range(0, lines.size()).boxed()
                .flatMap(lineIndex -> extractGears(lines.get(lineIndex), lineIndex, partNumbers))
                .map(Gear::getRatio)
                .toList();
    }

    private Stream<PositionedNumber> getPartNumbersInternal() {
        return IntStream.range(0, lines.size()).boxed()
                .flatMap(lineIndex -> extractNumbers(lines.get(lineIndex), lineIndex))
                .filter(this::isPartNumber);
    }

    private Stream<PositionedNumber> extractNumbers(String line, int lineIndex) {
        List<PositionedNumber> numbers = new ArrayList<>();
        StringBuilder activeNumber = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                activeNumber.append(line.charAt(i));
            } else if (!activeNumber.isEmpty()) {
                numbers.add(buildNumber(activeNumber, lineIndex, i));
                activeNumber.setLength(0);
            }
        }
        if (!activeNumber.isEmpty()) {
            numbers.add(buildNumber(activeNumber, lineIndex, line.length()));
        }
        return numbers.stream();
    }

    private PositionedNumber buildNumber(StringBuilder number, int lineIndex, int endPosition) {
        return new PositionedNumber(Integer.parseInt(number.toString()), lineIndex, endPosition - number.length(), endPosition - 1);
    }

    private boolean isPartNumber(PositionedNumber positionedNumber) {
        return IntStream.rangeClosed(-1, 1)
                .map(offset -> positionedNumber.line() + offset)
                .filter(line -> line >= 0)
                .filter(line -> line < lines.size())
                .mapToObj(lines::get)
                .map(line -> safeSubstring(line, positionedNumber.startColumn() - 1, positionedNumber.endColumn() + 2))
                .anyMatch(this::containsSymbol);
    }

    private String safeSubstring(String s, int beginIndex, int endIndex) {
        return s.substring(Math.max(0, beginIndex), Math.min(s.length(), endIndex));
    }

    private boolean containsSymbol(String s) {
        return StringUtils.characters(s).anyMatch(c -> !Character.isDigit(c) && c != EMPTY_CHAR);
    }

    private Stream<Gear> extractGears(String line, int lineIndex, List<PositionedNumber> partNumbers) {
        List<Gear> gears = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == GEAR_CHAR) {
                int column = i;
                var adjacentNumbers = partNumbers.stream().filter(number -> number.isAdjacent(lineIndex, column)).toList();
                if (adjacentNumbers.size() == 2) {
                    gears.add(new Gear(adjacentNumbers.get(0), adjacentNumbers.get(1)));
                }
            }
        }
        return gears.stream();
    }

    private record PositionedNumber(int number, int line, int startColumn, int endColumn) {

        private boolean isAdjacent(int line, int column) {
            return between(line, line() - 1, line() + 1) && between(column, startColumn() - 1, endColumn() + 1);
        }

        private static boolean between(int n, int low, int high) {
            return n >= low && n <= high;
        }
    }

    private record Gear(PositionedNumber part1, PositionedNumber part2) {

        public int getRatio() {
            return part1.number() * part2.number();
        }
    }
}

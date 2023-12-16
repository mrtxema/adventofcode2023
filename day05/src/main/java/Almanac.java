import common.parser.SplitResult;
import common.parser.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public record Almanac(List<Long> seeds, List<MappingGroup> mappings) {

    public static Almanac parse(List<String> lines, boolean inverse) {
        List<Long> seeds = new ArrayList<>();
        List<MappingGroup> mappings = new ArrayList<>();
        StringUtils.groupLines(lines, String::isEmpty).forEach(sectionLines -> {
            String header = sectionLines.get(0);
            if (header.startsWith("seeds:")) {
                seeds.addAll(Arrays.stream(header.split(": ", 2)[1].split(" ")).map(Long::valueOf).toList());
            } else {
                SplitResult categories = StringUtils.split(header.split(" ")[0], "-to-", true);
                List<Mapping> mappingList = sectionLines.stream()
                        .skip(1)
                        .filter(line -> !line.isEmpty())
                        .map(line -> parseMapping(line, inverse))
                        .toList();
                mappings.add(new MappingGroup(
                        inverse ? categories.tail() : categories.head(),
                        inverse ? categories.head() : categories.tail(),
                        mappingList));
            }
        });
        if (inverse) {
            int mappingsSize = mappings.size();
            var reversedMappings = IntStream.range(0, mappings.size()).map(i -> mappingsSize - i - 1).mapToObj(mappings::get).toList();
            return new Almanac(seeds, reversedMappings);
        }
        return new Almanac(seeds, mappings);
    }

    private static Mapping parseMapping(String line, boolean inverse) {
        var values = Arrays.stream(line.split(" ", 3)).map(Long::valueOf).toList();
        return new Mapping(inverse ? values.get(0) : values.get(1), inverse ? values.get(1) : values.get(0), values.get(2));
    }

    long process(long value) {
        long result = value;
        for (MappingGroup mapping : mappings) {
            result = mapping.mappedValue(result);
        }
        return result;
    }

    record MappingGroup(String sourceCategory, String destCategory, List<Mapping> mappings) {

        long mappedValue(long value) {
            return mappings.stream().flatMap(it -> it.mappedValue(value).stream()).findFirst().orElse(value);
        }
    }

    record Mapping(long source, long dest, long size) {
        Optional<Long> mappedValue(long value) {
            return Optional.of(value).filter(v -> v >= source && v < source + size).map(v -> dest + v - source);
        }
    }
}

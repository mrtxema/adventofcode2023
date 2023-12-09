import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FarmMapRangeTest {

    @Test
    void testSort() {
        var ranges = List.of(
                new FarmMapRange(44, 1, 2),
                new FarmMapRange(11, 3, 4),
                new FarmMapRange(99, 5, 6));
        var sorted = ranges.stream()
                .sorted(Comparator.comparing(FarmMapRange::destinationStart))
                .toList();
        assertThat(sorted).hasSize(3);
        assertThat(sorted.get(0).destinationStart()).isEqualTo(11);
        assertThat(sorted.get(1).destinationStart()).isEqualTo(44);
        assertThat(sorted.get(2).destinationStart()).isEqualTo(99);
    }
}

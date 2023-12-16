import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1WithTestFile() {
        long result = new Solver("test.txt").part1();
        assertThat(result).isEqualTo(35);
    }

    @Test
    void testPart1WithInputFile() {
        long result = new Solver("input.txt").part1();
        assertThat(result).isEqualTo(579_439_039);
    }

    @Test
    void testPart2WithTestFile() {
        long result = new Solver("test.txt").part2();
        assertThat(result).isEqualTo(46);
    }

    @Test
    void testPart2WithInputFile() {
        long result = new Solver("input.txt").part2();
        assertThat(result).isEqualTo(7_873_084);
    }
}

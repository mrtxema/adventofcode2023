import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        int result = initSolver("test.txt").part1();
        assertThat(result).isEqualTo(102);
    }

    @Test
    void testPart1WithInputFile() {
        int result = initSolver("input.txt").part1();
        assertThat(result).isEqualTo(1238);
    }

    @Test
    void testPart2() {
        int result = initSolver("test.txt").part2();
        assertThat(result).isEqualTo(94);
    }

    @Test
    void testPart2WithInputFile() {
        int result = initSolver("input.txt").part2();
        assertThat(result).isEqualTo(1362);
    }

    private Solver initSolver(String fileName) {
        return new Solver(fileName).parseFile();
    }
}

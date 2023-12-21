import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day21SolverTest {

    @Test
    void testPart1() {
        int result = initSolver("test.txt").part1(6);
        assertThat(result).isEqualTo(16);
    }

    @Test
    void testPart2() {
        assertThat(initSolver("input.txt").part2(26501365)).isEqualTo(628206330073385L);
    }

    private Day21Solver initSolver(String fileName) {
        return new Day21Solver(fileName).parseFile();
    }
}

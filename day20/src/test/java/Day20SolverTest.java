import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day20SolverTest {

    @Test
    void testPart1WithTest1() {
        assertThat(initSolver("test1.txt").part1()).isEqualTo(32000000);
    }

    @Test
    void testPart1WithTest2() {
        assertThat(initSolver("test2.txt").part1()).isEqualTo(11687500);
    }

    @Test
    void testPart2WithInput() {
        assertThat(initSolver("input.txt").part2()).isEqualTo(221453937522197L);
    }

    private Day20Solver initSolver(String fileName) {
        return new Day20Solver(fileName);
    }
}

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day24SolverTest {

    @Test
    void testPart1() {
        int result = initSolver().part1(7, 27);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testPart2() {
        long result = initSolver().part2();
        assertThat(result).isEqualTo(47);
    }

    private Day24Solver initSolver() {
        return new Day24Solver("test.txt").parseFile();
    }
}

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        int result = initSolver().part1();
        assertThat(result).isEqualTo(1320);
    }

    @Test
    void testPart2() {
        int result = initSolver().part2();
        assertThat(result).isEqualTo(145);
    }

    private Solver initSolver() {
        return new Solver("test.txt").parseFile();
    }
}

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day25SolverTest {

    @Test
    void testPart1WithTestFile() {
        int result = initSolver("test.txt").part1();
        assertThat(result).isEqualTo(54);
    }

    @Test
    void testPart1WithInputFile() {
        int result = initSolver("input.txt").part1();
        assertThat(result).isEqualTo(559143);
    }

    private Day25Solver initSolver(String fileName) {
        return new Day25Solver(fileName).parseFile();
    }
}

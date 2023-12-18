import common.movement.Position;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testCalculateArea() {
        long area = initSolver().calculateArea(List.of(
                new Position(0, 0),
                new Position(6, 0),
                new Position(6, 2),
                new Position(0, 2),
                new Position(0, 0)
        ));
        assertThat(area).isEqualTo(21);
    }

    @Test
    void testPart1() {
        long result = initSolver().part1();
        assertThat(result).isEqualTo(62);
    }

    @Test
    void testPart2() {
        long result = initSolver().part2();
        assertThat(result).isEqualTo(952408144115L);
    }

    private Solver initSolver() {
        return new Solver("test.txt").parseFile();
    }
}

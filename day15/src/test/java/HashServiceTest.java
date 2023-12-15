import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashServiceTest {
    private final HashService hashService = new HashService();

    @Test
    void testHash() {
        int result = hashService.hash("HASH");
        assertThat(result).isEqualTo(52);
    }
}

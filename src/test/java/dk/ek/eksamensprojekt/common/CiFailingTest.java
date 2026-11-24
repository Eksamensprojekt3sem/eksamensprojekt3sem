package dk.ek.eksamensprojekt.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class CiFailingTest {

    @Test
    void
ci_failingTest_blocksMerge() {
        assertEquals(1, 2);
    }
}

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestOffByN {
    @Test
    public void testOffByN() {
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f')); // ==> true
        assertTrue(offBy5.equalChars('f', 'a')); // ==> true
        assertFalse(offBy5.equalChars('f', 'h')); // ==> false

        OffByN offByOne = new OffByN(1);
        assertTrue(offByOne.equalChars('a', 'b')); // ==> true
        assertTrue(offByOne.equalChars('r', 'q')); // ==> true
        assertFalse(offByOne.equalChars('a', 'e')); // ==> false
        assertFalse(offByOne.equalChars('z', 'a')); // ==> false
        assertFalse(offByOne.equalChars('a', 'a')); // ==> false
    }
}

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllCapsTest {
    @Test
    void AllCapsFunctionTest() {
        assertArrayEquals(AllCaps.allCapLocations("a@bAaCt99q"), new int[]{3, 5});
        assertArrayEquals(AllCaps.allCapLocations("@82968*%(*@^ttt)"), new int[]{});
        assertArrayEquals(AllCaps.allCapLocations("PQRssssS"), new int[]{0, 1, 2, 7});
    }
}

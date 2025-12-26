import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SLListTest {

    @Test
    void getFirstThrowsIndexOutOfBoundsException() {
        SLList foo = new SLList();
        assertThrows(IndexOutOfBoundsException.class, foo::getFirst);
    }

    @Test
    void getTest() {
        SLList foo = new SLList();

        foo.addFirst(7);
        foo.addFirst(2);
        foo.addFirst(9);
        foo.addFirst(10);

        assertEquals(foo.getFirst(), 10);
        assertEquals(foo.get(0), 10);
        assertEquals(foo.get(1), 9);
        assertEquals(foo.get(2), 2);
        assertEquals(foo.get(3), 7);

        // Out of bounds
        assertThrows(IndexOutOfBoundsException.class, () -> foo.get(4));
    }

}


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("aaab"));
    }

    @Test
    public void testOffByOnePalindrome() {
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue(palindrome.isPalindrome("egatubff", new OffByOne()));
        assertTrue(palindrome.isPalindrome("eeff", new OffByOne()));
        assertFalse(palindrome.isPalindrome("racecar", new OffByOne()));
        assertFalse(palindrome.isPalindrome("dsoop", new OffByOne()));
        assertFalse(palindrome.isPalindrome("discrete", new OffByOne()));
    }
}


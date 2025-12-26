public class Palindrome{
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            // Case for when a word is empty or only one character
            return true;
        }

        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size() > 1) {
            char first = wordDeque.removeFirst();
            char last = wordDeque.removeLast();
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            // Case for when a word is empty or only one character
            return true;
        }

        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size() > 1) {
            char first = wordDeque.removeFirst();
            char last = wordDeque.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Palindrome x = new Palindrome();
        System.out.println(x.isPalindrome("flake", new OffByOne()));
    }
}
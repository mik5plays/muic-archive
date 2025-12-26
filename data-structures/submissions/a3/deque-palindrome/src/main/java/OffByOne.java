public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y) {
        int difference = (int) x - (int) y;
        return (difference == 1 || difference == -1); // Don't feel like importing Math here
    }

}
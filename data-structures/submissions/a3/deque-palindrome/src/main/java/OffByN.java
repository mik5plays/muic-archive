public class OffByN implements CharacterComparator {
    int n;

    public OffByN(int n) {
        this.n = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int difference = (int) x - (int) y;
        return (difference == n || difference == -n);
    }
}
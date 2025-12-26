import java.util.Iterator;
import java.util.NoSuchElementException;

public class BoundedSkipper implements Iterable<Integer> {
    private class BoundedSkipperIterator implements Iterator<Integer> {
        private int ptr;
        private int value;

        BoundedSkipperIterator() { ptr = 0; value = 1; }
        @Override
        public boolean hasNext() {
            return (ptr < n);
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                while (!isValid(value)) {
                    value++;
                }
                ptr++;
                return value++; // Not incrementing this would result in the same value being checked over and over.
            } else {
                throw new NoSuchElementException();
            }
        }

        private boolean isValid(int x) {
            if (x % k == 0) {
                return false;
            }
            return !(String.valueOf(x).contains(String.valueOf(k)));
        }

    }
    public Iterator<Integer> iterator() {
        return new BoundedSkipperIterator();
    }

    private int k;
    private int n;

    public BoundedSkipper(int k, int n) {
        this.k = k;
        this.n = n;
    }

    public static void main(String[] args) {
        for (int v: new BoundedSkipper(3, 11)) {
            System.out.println(v); // prints out the above sequence on separate lines
        }
    }
}

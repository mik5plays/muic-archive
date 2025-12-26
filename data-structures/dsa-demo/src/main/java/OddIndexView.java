import java.util.Iterator;
import java.util.NoSuchElementException;

public class OddIndexView<T> implements Iterable<T> {
    private T[] array;

    private class OddIndexViewIter implements Iterator<T> {
        int curIndex;
        public OddIndexViewIter() { curIndex = 1 ; }
        public boolean hasNext() {
            return curIndex < array.length ;
        }
        public T next() {
            // if there is a next item to return, return it
            // otherwise raise an exception NoSuchElementException
            if (hasNext()) {
                T retVal = array[curIndex];
                curIndex += 2; // update curIndex
                return retVal;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }
    public OddIndexView(T[] array) { this.array = array; }

    public Iterator<T> iterator() {
        return new OddIndexViewIter();
    }

    public static void main(String[] args) {
        OddIndexView<String> view = new OddIndexView<>(new String[]{"ze", "ne", "wo", "ee", "hi"});
        for (String st : view) {
            System.out.println(st); // would print ne and ee on separate lines
        }
    }
}



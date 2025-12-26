import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A very primitive ArraySet implementation using
 * Java's builtin ArrayList.
 */

class ArraySet<T> implements Iterable<T>{
    class ArraySetIterator implements Iterator<T> {
        private int ptr; // Consider a pointer variable that denotes what index we are pointing at
        ArraySetIterator() { ptr = 0; }

        @Override
        public boolean hasNext() {
            return ptr < items.size();
        }

        @Override
        public T next() {
            if (hasNext()) {
                T Next = items.get(ptr);
                ptr++;
                return Next;
            }
            throw new NoSuchElementException();
        }
    }

    List<T> items;

    public ArraySet() { items = new ArrayList<>(); }

    public void add(T value) {
        if (!items.contains(value)) { items.add(value); }
    }

    public boolean contains(T value) {
        return (items.contains(value));
    }

    public Iterator<T> iterator() {
        return new ArraySetIterator();
        // we say new because we are expected to return
        // an instance that is the iterator.
    }

    public int size() { return items.size(); }

    public static void main(String[] args) {
        ArraySet<String> foo = new ArraySet();
        foo.add("bar");
        foo.add("foo");

        for (String item: foo) {
            System.out.println(item);
        }
    }
}                                                            

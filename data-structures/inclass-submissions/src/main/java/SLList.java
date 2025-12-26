import java.util.Iterator;
import java.util.NoSuchElementException;

class SLList<T> implements Iterable<T>{
    private class SLListIterator implements Iterator<T> {
        private Node ptr;
        SLListIterator() { ptr = sen.next; }

        @Override
        public boolean hasNext() {
            return (ptr != null);
        }

        @Override
        public T next() {
            if (hasNext()) {
                T Next = (T) ptr.head;
                ptr = ptr.next;
                return Next;
            }
            throw new NoSuchElementException();
        }
    }

    public Iterator<T> iterator() {
        return new SLListIterator();
    }

    private static class Node<T> {
        T head;
        Node next;
        Node(T head, Node next) {
            this.head = head;
            this.next = next;
        }
    }
    private Node sen; // Sentinel
    private int size;

    // Double constructor
    public SLList() {
        sen = new Node(-1, null);
        size = 0;
    }
    public SLList(T x) {
        Node first = new Node(x, null);
        sen = new Node(-1, first);
        size = 1;
    }
    public void addFirst(T x) {
        sen.next = new Node(x, sen.next);
        size++;
    }
    public void addLast(T x) {
        Node y = sen;
        while (y.next != null) {
            y = y.next;
        }
        y.next = new Node(x, null);
        size++;
    }
    public T getFirst() {
        return (T) sen.next.head;
    }
    public T getLast() {
        Node y = sen;
        while (y.next != null) {
            y = y.next;
        }
        return (T) y.head;
    }
    public int size() {
        return size;
    }

    // Exercise 1
    public String toString() {
        Node r = sen.next;
        String result = "";
        while (r != null) {
            if (r.next == null) {
                result = result + r.head;
            } else {
                result = result + r.head + ", ";
            }
            r = r.next;
        }
        return result;
    }
    // Exercise 2
    public void removeFirst() {
        if (size >= 1) {
            sen.next = sen.next.next;
            size--;
        }
    }
    // Exercise 3
    public void insert(T newValue, int k) {
        int index = 0;
        Node y = sen;
        if (k == 0) {
            addFirst(newValue);
        } else {
            while (index < k && index < size) {
                y = y.next;
                index++;
            }
            // Assuming that if index exceeds
            Node z = y.next;
            y.next = new Node(newValue, z);
            size++;
        }
    }
    // .equals() implementation

    public boolean equals(SLList other) {
        if (this.size() != other.size()) {
            // Lists are not the same size so we ignore
            return false;
        }
        if (this == other) { return true; } // Equal by reference
        if (this.sen.next != null && other.sen.next != null) {
            Node p = this.sen.next;
            Node q = other.sen.next;
            while (p != null && q != null) {
                if (p.head != q.head) { return false; }
                p = p.next;
                q = q.next;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        SLList<Integer> obj = new SLList();
        SLList<Integer> obj2;
        obj.addFirst(5);
        obj.addFirst(7);

        obj2 = obj;

        System.out.println(obj.equals(obj2));


    }
}

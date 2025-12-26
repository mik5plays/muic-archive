import java.util.Arrays;

class ArrayIntList {
    private int[] items;
    private int size;

    public ArrayIntList() {
        size = 0;
        items = new int[10]; // Let's say 10 is the default size
    }

    public int[] getItems() {
        return items;
    }

    public int size() {
        return size;
    }

    public int getLast() {
        return items[size - 1];
    }

    public int getFirst() {
        return items[0];
    }

    public void addLast(int x) {
        items[size] = x;
        size++;
    }

    public void removeLast() {
        size--;
        // Assume we need no garbage collection here
    }

    public void addFirst(int x) {
        size++;
        int[] newItems;
        if (size < 10) {
            newItems = new int[10];
        } else {
            newItems = new int[size*2];
            // Let's just double the size
        }
        newItems[0] = x;
        System.arraycopy(items, 0, newItems, 1, size - 1);
        items = newItems.clone();
    }
    // This creates a new array, copies the previous contents of the old array
    // into the new one.
    // It probably won't take that long to run. Of course, it scales with size.
    // and since I am doubling the array size when full, it would not be called that often.

}

public class AList<T> {
    private T[] items;
    private int size;

    public AList(int n) {
        this.items = (T[]) new Object[n];;
    }
    public AList() {
        this.items = (T[]) new Object[10]; // Default size
    }

    public void addFirst(T x) {
        size++;
        T[] newItems;
        if (size < 10) {
            newItems = (T[]) new Object[10];
        } else {
            newItems = (T[]) new Object[size++];
            // Let's just increment the size
        }
        newItems[0] = x;
        System.arraycopy(items, 0, newItems, 1, size - 1);
        items = newItems.clone();
    }

    public void addLast(T x) {
        items[size] = x;
        size++;
    }
// removeFirst from Exercise 2
    public T removeFirst() {
        T itemToRemove = items[0];
        size--;
        T[] newItems = (T[]) new Object[size];
        System.arraycopy(items, 1, newItems, 0, size);
        items = newItems.clone();

        return itemToRemove;
    }
    public T removeLast() { // Taken from the lesson
        T itemToRemove = items[size - 1];
        items[size - 1] = null;
        size--;

        return itemToRemove;
    }

    public T getFirst() { return items[0]; }
    public T getLast() { return items[size - 1]; }
    public int size() { return size; }

    // Assuming we also want to include methods made in SLList practice exercise
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                result = result + items[i];
            } else {
                result = result + items[i] + ", ";
            }
        }
        return result;
    }

    // Method is identical to L4 Practice, just adapted
    // Assuming that inserting does not replace the value at the index
    public void insert(T value, int index) {
        size++;
        // Failsafe
        if (size == 0) { addFirst(value); return; };
        T[] newItems = (T[]) new Object[size];
        System.arraycopy(items, 0, newItems, 0, index);
        newItems[index] = value;
        System.arraycopy(items, index, newItems, index + 1, size - index - 1);
        items = newItems.clone();
    }

    public static void main(String[] args) {
        AList foo = new AList();
        foo.addLast(12);
        foo.addLast("meow");
        foo.addLast(1.50);
        foo.insert('a', 0);
        System.out.println(foo.toString());
    }
}


// Exercise 3

//int sum(int x) { // precondition: x >= 0
//    int p = 0;
//    for (int i=0;i<x;i++) { // @loop_invariant: 0 <= i <= x and p == (2**i) - 1
//        p += pow(2, i); // suppose there is a powering function where pow(2, i) returns 2**i
//        // { p += 2**i }
//    }
//    { p = 2**x - 1 }
//    return p;
//}
// post-condition: return 2**x - 1
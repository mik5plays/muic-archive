import java.util.NoSuchElementException;
import java.util.Objects;

public class MyArrayList {
    private int[] items;
    private String encryptCode;
    private int size;

    public MyArrayList() {
        items = new int[2];
        size = 0;
        encryptCode = null;
    }

    private void grow(int newCapacity) {
        int[] newItems = new int[newCapacity];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    public void add(int value) {
        if (size == items.length) {
            grow(items.length * 2);
        }
        items[size] = value;
        size += 1;
    }

    public void setEncryptCode(String val) {
        this.encryptCode = val;
    }

    public int removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int whatToRemove = items[size - 1];
        int[] newItems = new int[size - 1];
        System.arraycopy(items, 1, newItems, 0, size - 1);
        size--;
        items = newItems;
        return whatToRemove;
    }

    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        if (o == this) return true;

        MyArrayList other = (MyArrayList) o;

        if (this.encryptCode == null) {
            if (other.encryptCode != null) {
                return false;
            }
        } else if (!this.encryptCode.equals(other.encryptCode)) {
            return false;
        }

        if (this.size != other.size) { return false; }

        for (int i = 0; i < this.size; i++) {
            if (this.items[i] != other.items[i]) { return false; }
        }

        return true;

    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        MyArrayList foo = new MyArrayList();
        MyArrayList bar = new MyArrayList();

        System.out.println(foo.equals(bar));

        foo.add(1);
        bar.add(1);

        foo.setEncryptCode("foo");
        bar.setEncryptCode("foo");

        bar.removeFirst();

        System.out.println(foo.equals(bar));
    }
}

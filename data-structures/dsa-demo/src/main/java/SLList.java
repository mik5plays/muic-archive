class SLList {
    private static class IntNode {
        int head;
        IntNode next;
        IntNode(int head, IntNode next) {
            this.head = head;
            this.next = next;
        }
    }
    private IntNode sen; // Sentinel
    private int size;

    // Double constructor
    public SLList() {
        sen = new IntNode(-1, null);
        size = 0;
    }
    public SLList(int x) {
        IntNode first = new IntNode(x, null);
        sen = new IntNode(-1, first);
        size = 1;
    }
    public void addFirst(int x) {
        sen.next = new IntNode(x, sen.next);
        size++;
    }
    public void addLast(int x) {
        IntNode y = sen;
        while (y.next != null) {
            y = y.next;
        }
        y.next = new IntNode(x, null);
        size++;
    }
    public int getFirst() {
        return sen.next.head;
    }
    public int getLast() {
        IntNode y = sen;
        while (y.next != null) {
            y = y.next;
        }
        return y.head;
    }
    public int size() {
        return size;
    }

    // Exercise 1
    public String toString() {
        IntNode r = sen.next;
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
    public void insert(int newValue, int k) {
        int index = 0;
        IntNode y = sen;
        if (k == 0) {
            addFirst(newValue);
        } else {
            while (index < k && index < size) {
                y = y.next;
                index++;
            }
            // Assuming that if index exceeds
            IntNode z = y.next;
            y.next = new IntNode(newValue, z);
            size++;
        }
    }

    public static void main(String[] args) {
        SLList obj = new SLList();
    }
}

public class SLList {
    private class IntNode {
        int value; // an int data item
        IntNode next; // ref to the next node

        public IntNode(int val, IntNode r) {
            this.value = val; this.next = r;
        }
    }

    private IntNode first;

    public SLList() { first = null; }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public int getFirst() {
        try {
            return first.value;
        } catch (NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    public int get(int index) {
        IntNode p = first;
        int curr = 0;
        while (p != null) {
            if (curr == index) {
                return p.value;
            }
            p = p.next;
            curr++;
        }
        throw new IndexOutOfBoundsException();
    }

    public static void main(String[] args) {
        SLList foo = new SLList();
        foo.addFirst(7);
        foo.addFirst(2);
        foo.addFirst(9);
        foo.addFirst(10);
    }
}
class IntNodeTest {
    // instance variables
    int head;
    IntNodeTest next;

    public IntNodeTest(int head, IntNodeTest next) {
        this.head = head;
        this.next = next;
    }

    public String toString() {
        if (this.next == null) {
            return "" + this.head;
        }
        else {
            return "" + this.head + ", "+ this.next.toString();
        }
    }

    public int size() {
        if (this.next == null)
            return 1;
        else
            return 1 + this.next.size();
    }

    public int iterativeSize() {
        IntNodeTest current = this;
        int totalSize = 0;

        while (current != null) {
            totalSize++;
            current = current.next;
        }

        return totalSize;
    }

    public static void main(String[] args) {
        IntNodeTest N3 = new IntNodeTest(10, null);
        IntNodeTest N2 = new IntNodeTest(9, N3);
        IntNodeTest N1 = new IntNodeTest(3, N2);
        IntNodeTest N = new IntNodeTest(5, N1);
        System.out.println(N.toString());
    }
}
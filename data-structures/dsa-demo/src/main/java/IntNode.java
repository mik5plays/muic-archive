public class IntNode {
    int head;
    IntNode next;
    // Constructor
    IntNode(int head, IntNode next) {
        this.head = head;
        this.next = next;
    }
    public String iterativeToString() {
        IntNode x = this;
        String result = "";
        while (x != null) {
            if (x.next != null) {
                result = result + x.head + ", ";
            } else {
                result = result + x.head;
            }
            x = x.next;
        }
        return result;
    }

    public int get(int i) {
        int index = 0;
        IntNode curr = this;
        while (i != index) {
            curr = this.next;
            index++;
        }
        return curr.head;
    }

    public void set(int i, int newValue) {
        int _index = 0;
        IntNode _curr = this;
        while (i != _index) {
            _curr = this.next;
            _index++;
        }
        _curr.head = newValue;
    }

    public IntNode incrList(int delta) {
        if (this.next == null)
            return new IntNode(this.head + delta, null);
        else
            return new IntNode(this.head + delta, this.next.incrList(delta));
    }

    public static void main(String[] args) {
        // Bunch of test cases
        IntNode N3 = new IntNode(10, null);
        IntNode N2 = new IntNode(9, N3);
        IntNode N1 = new IntNode(3, N2);
        IntNode N = new IntNode(5, N1);
        System.out.println(N.iterativeToString());
        System.out.println(N.get(1));
        N.set(1,32);
        System.out.println(N.get(1));
        System.out.println(N.iterativeToString());
        IntNode M = N.incrList(2);
        System.out.println(M.iterativeToString());
    }
}
import java.util.Arrays;

class Unknown {
    public static int w;
    private static int x;
    private int y;
    public int[] z = new int[5];
    public Unknown() {w += 1; y = w;}
    public static void setX(int i) {x += i;}
    public void setZ(int i) {z[y] = i; y += 1;}
    public int getW() {return w;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getZ(int i) {return z[i];}
}

class singly {
    private static class IntNode {
        int head; // an int data item
        IntNode rest; // ref to the next node
        public IntNode(int val, IntNode r) {
            this.head = val; this.rest = r;
        }
    }
    private IntNode sen;
    public singly() { sen = new IntNode(0, null); }
    public int getFirst() {
        return sen.rest.head;
    }
    public boolean isEmpty(){
        return sen.rest == null;
    }
    public void addFirst(int x) {
        if (sen.rest == null) {
            sen.rest = new IntNode(x, null);
        } else {
            sen.rest = new IntNode(x, sen.rest);
        }
    }
    public double getAverage() {
        IntNode p = sen.rest;
        double total = 0;
        double length = 0;
        while (p != null) {
            length++;
            total += p.head;
            p = p.rest;
        }
        return total / length;
    }
    public void print() {
        IntNode p = sen.rest;
        while (p != null) {
            System.out.println(p.head);
            p = p.rest;
        }
    }
}

public class quizOne {
    public static int[] len(String[] arr) {
        int[] lengths = new int[arr.length];
        for (int i=0;i<arr.length;i++) {
            lengths[i] = arr[i].length();
        }
        return lengths;
    }
    public static void main(String[] args) {
        singly test = new singly();
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.print();
        System.out.println(test.getAverage());
    }
}

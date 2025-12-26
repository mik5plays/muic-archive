// Uses a doubly-linked list to make a deque.

public class LinkedListDeque<T> {
    private class Node<T>{
        private Node prev;
        private Node next;
        private T value;

        // Constructor
        Node(Node x, T item, Node y){
            prev = x;
            value = item;
            next = y;
        }
        Node(T item) {
            prev = null;
            next = null;
            value = item;
        }
    }

    // Logic here is that the sentinel will point to the head and tail of the deque.
    // sen.next = head, null if none
    // sen.prev = tail, null if none

    private Node sen;
    private int size;

    public LinkedListDeque() {
        sen = new Node(null, null, null); // null since the sentinel does not hold any value
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque<T> other) {
        // Creates a deep copy
        sen = new Node(null, null, null);
        Node<T> p = other.sen.next;
        while (p.value != null) {
            this.addLast(p.value);
            p = p.next;
        }
    }

    // Adds an item of type T to the front of the deque.
    public void addFirst(T item) {
        size++;
        if (sen.next == null) { // If there is no head (i.e list should be empty)
            // Queue should loop back to the sentinel
            Node head = new Node(sen, item, sen);
            sen.next = head;
            sen.prev = head;
        } else { // Case for >1 items in the deque
            Node newItem = new Node(item);
            newItem.prev = sen;
            newItem.next = sen.next;
            sen.next.prev = newItem;
            sen.next = newItem;
        }
    }
    // Adds an item of type T to the back of the deque.
    public void addLast(T item){
        size++;
        if (sen.next == null) { // If there is no head (i.e list should be empty)
            // Queue should loop back to the sentinel
            Node tail = new Node(sen, item, sen);
            sen.next = tail;
            sen.prev = tail;
        } else {
            Node newItem = new Node(item);
            newItem.prev = sen.prev;
            newItem.next = sen;
            sen.prev.next = newItem;
            sen.prev = newItem;
        }

    }
    // Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        return ((sen.next == null && sen.prev == null) );
    }
    // Returns the number of items in the deque.
    public int size() {
        return size;
    }
    // Returns a string showing the items in the deque from first to last,
// separated by a space.
    public String toString() {
        if (!isEmpty()) {
            String result = "";
            Node p = sen.next;
            while (p.value != null) {
                if (p.next.value == null) {
                    result = result + p.value;
                } else {
                    result = result + p.value + " ";
                }
                p = p.next;
            }
            return result;
        } else {
            return "<empty>";
        }
    }
// Removes and returns the item at the front of the deque.
// If no such item exists, returns null.
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size--;
        Node itemToRemove = sen.next;
        if (sen.next.next != sen) {
            sen.next = sen.next.next;
        } else {
            sen.next = null;
            sen.prev = null;
        }
        return (T) itemToRemove.value;
    }
    // Removes and returns the item at the back of the deque.
// If no such item exists, returns null.
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size--;
        Node itemToRemove = sen.prev;
        if (sen.prev.prev != sen) {
            sen.prev = sen.prev.prev;
            sen.prev.next = sen;
        } else {
            sen.next = null;
            sen.prev = null;
        }
        return (T) itemToRemove.value;
    }
    // Gets the item at the given index, where 0 is the front, 1 is the next item,
// and so forth. If no such item exists, returns null. Must not alter the deque!
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        int currIndex = 0;
        Node p = sen.next;
        while (currIndex < index && p.value != null) {
            p = p.next;
            currIndex++;
        }
        return (T) p.value;
    }

    // Prints out the list as a debug tool
    public void printDeque() {
        String result = (isEmpty() ? "<empty>" : "");
        if (!isEmpty()) {
            Node p = sen.next;
            while (p.value != null) {
                if (p.next.value == null) {
                    result = result + "[ " + p.prev.value + " | " + p.value + " | " + p.next.value + " ]";
                } else {
                    result = result + "[ " + p.prev.value + " | " + p.value + " | " + p.next.value + " ]" + " -> ";
                }
                p = p.next;
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        LinkedListDeque foo = new LinkedListDeque();
    }


}

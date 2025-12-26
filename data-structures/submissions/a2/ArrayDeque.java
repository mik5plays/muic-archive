
/*
TLDR:
My implementation is a bit strange, but it does work.
A deque starts out as [...] of nulls.
Adding anything in as the first value leads to [T, ...]
Adding something behind, say 5, will look like [T,5,...]
Adding something in front, say 6, will look something like [T,5,...,6]
The pointer values then look like:
>> front value: 6
>> rear value: 5

I hope this correctly implements the circular queue/buffer 🙏
*/

public class ArrayDeque<T> {
    private T[] items;
    private int size, rear, front, maxSize; //maxSize used to keep track

    public ArrayDeque() {
        items = (T[]) new Object[8];
        maxSize = 8;
        size = 0;
        rear = -1;
        front = -1;
    }
    public ArrayDeque(ArrayDeque<T> other) {
         // Deep copy, gonna "cheat" a bit
        size = other.size;
        rear = other.rear;
        front = other.front;
        maxSize = other.maxSize;
        items = other.items.clone();
    }
    public void grow() {
        // Going to grow out the array by 8 each time it gets full.
        maxSize += 8;
        T[] newItems = (T[]) new Object[maxSize];
        int currOld, currNew;
        currOld = rear;
        currNew = rear;
        while (currOld != front) {
            if (currOld == -1 && currNew == -1) {
                currOld = maxSize - 9;
                currNew = maxSize - 1;
            }
            newItems[currNew] = items[currOld];
            currNew--;
            currOld--;
        }
        newItems[currNew] = items[front];
        front = currNew;
        items = newItems.clone();
    }

// Shrinking method to ensure a list always has >= 25% usage.
    public void shrink() {
        double usage = (double) size / maxSize;
        if (usage < 0.25 && maxSize >= 16) {
            // Implying array usage is less than 25%
            // Going to shrink to 75% of the original size
            // Small-size arrays I will bother shrinking
            int reduce = (int) (maxSize * 0.25);
            maxSize -= (int) reduce;
            T[] newItems = (T[]) new Object[maxSize];
            int currOld, currNew;
            currOld = rear;
            currNew = rear;
            while (currOld != front) {
                if (currOld == -1 && currNew == -1) {
                    currOld = maxSize + reduce - 1;
                    currNew = maxSize - 1;
                }
                newItems[currNew] = items[currOld];
                currNew--;
                currOld--;
            }
            newItems[currNew] = items[front];
            front = currNew;
            items = newItems.clone();
            System.out.println("Shrink operation succeeded.");
        }
        System.out.println("Shrink operation attempted.");
    }

    public boolean needsGrowing() {
        return (rear + 1 >= maxSize || rear + 1 == front || front - 1 == rear);
    }

    public void addLast(T item) {
        if (rear == -1) { // Nothing in the array
            size++;
            rear++;
            front++; // Only one element in the list, so points to itself
            items[0] = item;
        } else {
            size++;
            if (needsGrowing()) { // rearx has reached the end of the array.
                grow();
            }
            rear++;
            items[rear] = item;
        }
    }

    public void addFirst(T item) {
        if (front == -1) { // Special case for when array is empty
            size++;
            rear++;
            front++;
            items[0] = item;
        } else {
            if ((front == 0 && items[maxSize - 1] != null) || needsGrowing()) {
                grow();
            }
            if (front == 0) {
                front = maxSize;
            }
            front--;
            size++;
            items[front] = item;
//            size++;
//            if (front == 0) {
//                // Adding to the end of the array instead
//                front = maxSize - 1;
//                if (items[front] != null) {
//                    front = 0;
//                    size--;
//                    System.out.println("< Array full >");
//                } else {
//                    items[front] = item;
//                }
//            } else {
//                front--;
//                if (items[front] != null) {
//                    front++;
//                    size--;
//                    System.out.println("< Array full >");
//                } else {
//                    items[front] = item;
//                }
//            }
        }
    }
    // Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        for (T item: items) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }
    // Returns the number of items in the deque.
    public int size() {
        return size;
    }
    // Returns a string showing the items in the deque from first to last,
// separated by a space.
    public String toString() {
        if (isEmpty()) {
            return "<empty>";
        }
        int curr = rear;
        String result = "";
        while (true) {
            if (curr == -1) {
                curr = maxSize - 1;
            }
            if (curr == front) {
                if (rear == front) {
                    result = result + items[curr];
                } else {
                    result = result + " " + items[curr];
                }
                return result;
            }
            if (curr == rear) {
                result = result + items[curr];
            } else {
                result = result + " " + items[curr];
            }
            curr--;
        }
    }

    public T removeLast() {
        if (rear == -1) {
            return null;
        }

        T itemToRemove = items[rear];
        items[rear] = null;
        if (rear == front) { // Completely empty
            rear = -1;
            front = -1;
            return itemToRemove;
        }
        size--;
        rear--;

        shrink();
        return itemToRemove;
    }

    public T removeFirst() {
        if (front == -1) {
            return null;
        }
        T itemToRemove = items[front];
        items[front] = null;
        size--;
        if (front == maxSize - 1) {
            front = 0;
        } else {
            front++;
        }
        shrink();
        return itemToRemove;
    }
    // Gets the item at the given index, where 0 is the rearx, 1 is the next item,
// and so forth. If no such item exists, returns null. Must not alter the deque!
    public T get(int index) {
        return null;
    }

    public void printDeque() {
        String result = "[";
        for (T item: items) {
            if (item == null) {
                result = result + " " + "<null>";
            } else {
                result = result + " " + item;
            }
        }
        result = result + " ]";
        System.out.println(result);
        if (!isEmpty()) {
            System.out.println("front: " + items[front]);
            System.out.println("rear: " + items[rear]);
            System.out.println("size: " + size);
        } else {
            System.out.println("Deque is empty!");
        }
    }


    public static void main(String[] args) {
        ArrayDeque x = new ArrayDeque();
        for (int i = 0; i < 24; i++) {
            x.addFirst("foo");
        }
        x.addFirst("newest");

        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();
        x.removeFirst();

        // Should attempt shrink here.
        x.removeFirst();

        x.printDeque();

    }
}

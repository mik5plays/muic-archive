import java.util.*;

public class MyPriorityQueue<T> implements IPriorityQueue<T> {
    private List<T> queueItems;
    private final CompareWith<T> compare;

    private class pQueueReverseIterator implements Iterator<T> {
        int index;
        private final List<T> queueSorted;

        pQueueReverseIterator() {
            index = size() - 1;
            queueSorted = new ArrayList<T>(queueItems);
            queueSorted.sort( (a,b) -> { return compare.lessThan(a,b) ? -1: 1; }); // Assume more for equal elements
        }

        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T itemToReturn = queueSorted.get(index);
                index--;
                return itemToReturn;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    private class pQueueIterator implements Iterator<T> {
        int index;
        private final List<T> queueSorted;

        pQueueIterator() {
            index = 0;
            queueSorted = new ArrayList<T>(queueItems);
            queueSorted.sort( (a,b) -> { return compare.lessThan(a,b) ? -1: 1; }); // Assume more for equal elements
        }


        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public T next() {
            if (hasNext()) {
                T itemToReturn = queueSorted.get(index);
                index++;
                return itemToReturn;
            } else {
                throw new NoSuchElementException();
            }
        }

    }

    public MyPriorityQueue(CompareWith<T> cc) {
        queueItems = new ArrayList<>();
        compare = cc;
    }

    @Override
    public void add(T item) {
        queueItems.add(item);
    }

    @Override
    public void addAll(List<T> items) {
        queueItems.addAll(items);
    }

    @Override
    public T getMinimum() {
        T minimum = queueItems.get(0);
        for (T item: queueItems) {
            if (compare.lessThan(item, minimum)) {
                minimum = item;
            }
        }
        return minimum;
    }

    @Override
    public void removeMinimum() {
        queueItems.remove(getMinimum());
    }

    @Override
    public int size() {
        return queueItems.size();
    }

    @Override
    public Iterator<T> iterator() {
        return new pQueueIterator();
    }

    @Override
    public Iterator<T> revIterator() {
        return new pQueueReverseIterator();
    }

    public static void main(String[] args) {
        IPriorityQueue<Integer> priorityQueue = new MyPriorityQueue<>(
                new CompareWith<Integer>() {
                    @Override
                    public boolean lessThan(Integer a, Integer b) {
                        return a < b;
                    }
                }
        );

        priorityQueue.add(5);
        priorityQueue.add(2);
        priorityQueue.add(4);
        priorityQueue.add(3);
        priorityQueue.add(2);

        Iterator<Integer> iter = priorityQueue.revIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        priorityQueue.add(10);
    }
}

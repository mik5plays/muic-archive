import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyPriorityQueue<T> {
    private Comparator<T> cc;
    private List<T> items;
    public MyPriorityQueue(Comparator<T> cc) {
        this.cc = cc;
        items = new ArrayList<T>();
        items.add(null); // Sentinel at i=0
    }

    public void add(T x) {
        items.add(x);
        swim();
    }

    public T find() {
        return items.get(1);
    }

    void swim() {
        // Swim from deepest
        int index = items.size() - 1;
        while (index > 1 && (cc.compare(items.get(index / 2), items.get(index))) < 0) {
            T temp = items.get(index / 2);
            items.set(index / 2, items.get(index));
            items.set(index, temp);
            index = index / 2;
        }
    }

    void sink() {
        // Sink from root.
        T x = find();
        int height = (int) Math.ceil((Math.log(items.size()) / Math.log(2)));
        int currHeight = 1;
        T maxChild = null;
        while (currHeight < height) {
            if ((currHeight * 2) + 1 < items.size()) {
                if (cc.compare(items.get(currHeight * 2), items.get(currHeight * 2 + 1)) < 0)
                    maxChild = items.get(currHeight * 2 + 1);
                if (cc.compare(items.get(currHeight * 2), items.get(currHeight * 2 + 1)) > 0)
                    maxChild = items.get(currHeight * 2);
            } else {
                maxChild = items.get(currHeight * 2);
            }

            if (cc.compare(maxChild, items.get(currHeight)) < 0) { return; }

            T temp = maxChild;
            int xIndex = items.indexOf(x);
            items.set(items.indexOf(maxChild), x);
            items.set(xIndex, temp);
            x = maxChild;
            currHeight++;
        }
    }

    public void remove() {
        items.set(1, items.get(items.size() - 1 ));
        items.remove(items.size() - 1);
        sink();
    }

    public void stringRep() {
        for (T item: items) { System.out.println(item); }
    }
}

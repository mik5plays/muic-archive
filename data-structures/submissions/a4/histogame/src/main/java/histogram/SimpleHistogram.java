package histogram;

import java.util.*;

// TODO: Uncomment this and make sure to implement all the methods

public class SimpleHistogram<DT> implements Histogram<DT>, Iterable<DT> {

    private HashMap<DT, Integer> histogram;

    private class HistogramIterator implements Iterator<DT> {
        Set<DT> keys = histogram.keySet();
        Iterator<DT> keyIterator = keys.iterator();

        @Override
        public boolean hasNext() {
            return keyIterator.hasNext();
        }

        @Override
        public DT next() {
            if (hasNext()) {
                return keyIterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    // constructs an empty histogram (with no information)
    public SimpleHistogram() { histogram = new HashMap<DT, Integer>(); }

    // constructs a histogram from a list of items given by the parameter items
    public SimpleHistogram(DT items[]) {
        histogram = new HashMap<DT, Integer>();
        for (DT item: items) {
            if (histogram.get(item) == null) {
                histogram.putIfAbsent(item, 0);
            }
            setCount(item, histogram.get(item) + 1);
        }
    }
    // constructs a (new) histogram from an existing histogram, sharing nothing
    // internally
    public SimpleHistogram(Histogram<DT> hist) {
        histogram = new HashMap<DT, Integer>();

        Iterator<DT> histIterator = hist.iterator();
        while (histIterator.hasNext()) {
            DT curr = histIterator.next();
            setCount(curr, hist.getCount(curr));
        }
    }

    public HashMap<DT, Integer> getHistogram() { return histogram; }

    public int getTotalCount() {
        int sum = 0;
        for (HashMap.Entry<DT, Integer> entry: histogram.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public int getCount(DT item) {
        return histogram.getOrDefault(item, 0);
    }

    public void setCount(DT item, int count) {
        if (histogram.get(item) == null) {
            histogram.putIfAbsent(item, count);
        } else {
            histogram.put(item, count);
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<DT, Integer> entry: histogram.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
        }
        return result.toString();
    }

    public boolean equals(SimpleHistogram<DT> other) {
        Set<DT> keys = histogram.keySet();
        for (DT key: keys) {
            if (other.getHistogram().get(key) == null) {
                return false;
            } else if (!Objects.equals(other.getHistogram().get(key), histogram.get(key))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<DT> iterator() {
        return new HistogramIterator();
    }

    public static void main(String[] args) {
        Character[] target = {'a','b','c','a'};
        Histogram<Character> foo = new SimpleHistogram<>(target);
        Histogram<Character> bar = new SimpleHistogram<>(foo);
        foo.setCount('a', 100);
        System.out.println(foo.getCount('a'));
        System.out.println(bar.getCount('a'));

    }
}


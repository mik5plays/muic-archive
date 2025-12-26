import java.util.*;

public class sorts {
    /**
     * Mergesort for Integer arrays.
     * @param a - An unsorted List of Integers.
     * @runtime O(nlogn)
     */
    static void mergeInto(List<Integer> a, List<Integer> b, List<Integer> out) { // O(M+N) such that M = len(a) and N = len(b)
        int i=0, j=0;
        for (int k = 0; k < out.size(); k++) {
            if (i >= a.size()) // are we out of a?
                out.set(k, b.get(j++));
            else if (j >= b.size())  // are we out of b?
                out.set(k, a.get(i++));
            else if (a.get(i) < b.get(j)) // we are still within the bounds of a and b. ! for generic Comparable types, we can do a.compareTo(b) < 0
                out.set(k, a.get(i++)); // i will increment
            else
                out.set(k, b.get(j++)); // j will increment'
        }
    }

    static void mergeSort(List<Integer> a) {
        if (a.size() <= 1) { return; }

        List<Integer> left = new ArrayList<>(a.subList(0, a.size() / 2));
        List<Integer> right = new ArrayList<>(a.subList(a.size() / 2, a.size()));

        mergeSort(left); // recursive the left side
        mergeSort(right); // recursive the right side
        mergeInto(left, right, a); // merge the two recursed bits together into a
    }

    static void splitInto(List<List<String>> a, int pivot, List<List<String>> lt, List<List<String>> eq, List<List<String>> gt) {
        for (List<String> t : a) {
            if (Integer.parseInt(t.get(0)) > pivot)
                gt.add(t);
            else if (Integer.parseInt(t.get(0)) < pivot)
                lt.add(t);
            else
                eq.add(t);
        }
    }

    /**
     * Quicksort for string Lists. Slightly alter the comparisons to get a more Generic variant
     * @runtime O(nlogn)
     * @param a - an unsorted list of strings
     */
    static void quickSort(List<List<String>> a) {
        if (a.size() <= 1) { return; }

        List<List<String>> greaterThan = new ArrayList<>();
        List<List<String>> equalTo = new ArrayList<>();
        List<List<String>> lessThan = new ArrayList<>();

        Random random = new Random();
        int number = random.nextInt(a.size());
        int Pivot = Integer.parseInt(a.get(number).get(0));

        splitInto(a, Pivot, lessThan, equalTo, greaterThan);

        quickSort(lessThan);
        quickSort(greaterThan);


        int index = 0;
        for (List<String> t : lessThan) {
            a.set(index, t);
            index++;
        }
        for (List<String> t : equalTo) {
            a.set(index, t);
            index++;
        }
        for (List<String> t : greaterThan) {
            a.set(index, t);
            index++;
        }

    }

    /**
     * Insertion sort for generic arrays
     * @runtime O(n^2)
     *
     * @param a - the unsorted array
     * @param cc - the comparator used for the array
     * @param <T> - support for generic type
     *
     */

    static <T> void insertionSort(T[] a, Comparator<T> cc) {
        for (int i = 1; i < a.length; i++) { // loop_invariant: a[0:i] is sorted
            T x = a[i];
            int j = i;
            while (j > 0 && cc.compare(x, a[j - 1]) < 0) { // loop_invariant:
                a[j] = a[j - 1];
                j--;
            }
            a[j] = x;
        }
    }

    /**
     * Bubblesort for generic arrays
     * @runtime O(n^2)
     *
     * @param a - unsorted array
     * @param cc - comparator to use for array
     * @param <T> - support for generic types
     */

    static <T> void bubbleSort(T[] a, Comparator<T> cc) {
        T temp;
        for (int x = 0; x < a.length - 1; x++) {
            boolean swapped = false;
            for (int y = 0; y < a.length - x - 1; y++) {
                if (cc.compare(a[y+1], a[y]) < 0) {
                    temp = a[y];
                    a[y] = a[y+1];
                    a[y+1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) { break; }
        }
     }

    public static void main(String[] args) {

    }
}

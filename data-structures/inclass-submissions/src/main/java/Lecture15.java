import java.util.*;

public class Lecture15 {
    static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    static class IntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1, o2);
        }
    }

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

    static <T> void quickSort(T[] a, Comparator<T> cc) {
        if (a.length <= 1) { return; }

        List<T> greaterThan = new ArrayList<>();
        List<T> equalTo = new ArrayList<>();
        List<T> lessThan = new ArrayList<>();

        Random random = new Random();
        int number = random.nextInt(a.length);
        T Pivot = a[number];

        splitInto(a, Pivot, lessThan, equalTo, greaterThan, cc);

        T[] lt = listToPrim(lessThan);
        T[] gt = listToPrim(greaterThan);
        quickSort(lt, cc);
        quickSort(gt, cc);


        int index = 0;
        for (T t : lt) {
            a[index] = t;
            index++;
        }
        for (T t : equalTo) {
            a[index] = t;
            index++;
        }
        for (T t : gt) {
            a[index] = t;
            index++;
        }

    }

    static <T> T[] listToPrim(List<T> e) {
        T[] result = (T[]) new Object[e.size()];
        for (int i = 0; i < e.size(); i++)
            result[i] = e.get(i);
        return result;
    }

    static <T> void splitInto(T[] a, T pivot, List<T> lt, List<T> eq, List<T> gt, Comparator<T> cc) {
        for (T t : a) {
            if (cc.compare(t, pivot) > 0)
                gt.add(t);
            else if (cc.compare(t, pivot) < 0)
                lt.add(t);
            else
                eq.add(t);
        }
    }

    static <T> void mergeInto(T[] a, T[] b, T[] out, Comparator<T> cc) {
        int i=0, j=0;
        // Ajarn's method
        for (int k = 0; k < out.length; k++) {
            if (i >= a.length) // are we out of a?
                out[k] = b[j++];
            else if (j >= b.length)  // are we out of b?
                out[k] = a[i++];
            else if (cc.compare(a[i], b[j]) < 0) // we are still within the bounds of a and b.
                out[k] = a[i++]; // i will increment
            else
                out[k] = b[j++]; // j will increment'
        }
    }

    static <T> void mergeSort(T[] a, Comparator<T> cc) {
        if (a.length <= 1) { return; }

        T[] left = Arrays.copyOfRange(a, 0, a.length/2);
        T[] right = Arrays.copyOfRange(a, a.length/2, a.length);

        mergeSort(left, cc); // recursive the left side
        mergeSort(right, cc); // recursive the right side
        mergeInto(left, right, a, cc); // merge the two recursed bits together into a
    }

    public static void main(String[] args) {
        String[] foo = {"abcacus", "joe", "jake", "cool", "meow", "lame"};
        Integer[] bar = {3,1,4,5,7,8};

        String[] baz = {"abcacus", "joe", "jake", "cool", "meow", "lame"};
        Integer[] let = {3,1,4,5,7,8};

        String[] zyx = {"abcacus", "joe", "jake", "cool", "meow", "lame"};
        Integer[] wrz = {3,1,4,5,7,8};

        insertionSort(foo, new StringComparator());
        insertionSort(bar, new IntegerComparator());
        System.out.println(Arrays.toString(foo));
        System.out.println(Arrays.toString(bar));

        quickSort(baz, new StringComparator());
        quickSort(let, new IntegerComparator());
        System.out.println(Arrays.toString(baz));
        System.out.println(Arrays.toString(let));

        mergeSort(zyx, new StringComparator());
        mergeSort(wrz, new IntegerComparator());
        System.out.println(Arrays.toString(zyx));
        System.out.println(Arrays.toString(wrz));
    }
}

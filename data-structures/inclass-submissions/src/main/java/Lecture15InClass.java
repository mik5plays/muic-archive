import java.util.Arrays;
import java.util.Comparator;

public class Lecture15InClass {

    static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i+1]) { return false; }
        }
        return true;
    }

    static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) { // loop_invariant: a[0:i] is sorted
            int x = a[i], j = i;
            while (j > 0 && a[j-1] > x) { // loop_invariant:
                a[j] = a[j - 1];
                j--;
            }
            a[j] = x;
        }
    }

    static void mergeInto(int[] a, int[] b, int[] out) {
        int i=0, j=0;
        // Ajarn's method
        for (int k = 0; k < out.length; k++) {
            if (i >= a.length) // are we out of a?
                out[k] = b[j++];
            else if (j >= b.length)  // are we out of b?
                out[k] = a[i++];
            else if (a[i] < b[j]) // we are still within the bounds of a and b.
                out[k] = a[i++]; // i will increment
            else
                out[k] = b[j++]; // j will increment'
        }
    }

    static void mergeSort(int[] a) {
        if (a.length <= 1) { return; }

        int[] left = Arrays.copyOfRange(a, 0, a.length/2);
        int[] right = Arrays.copyOfRange(a, a.length/2, a.length);

        mergeSort(left); // recursive the left side
        mergeSort(right); // recursive the right side
        mergeInto(left, right, a); // merge the two recursed bits together into a
    }

    public static void main(String[] args) {
        System.out.println(isSorted(new int[]{1,2,3,4}));
        int[] foo = {3,1,4,2,8,5,7};
        insertionSort(foo);
        System.out.println(Arrays.toString(foo));

        int[] a = {1,2,4,6};
        int[] b = {3,5,7,9};
        int[] bar = new int[a.length + b.length];
        mergeInto(a,b,bar);

        System.out.println(Arrays.toString(bar));

        int[] daniel = {1,5,8,2,9,10,18,14};
        mergeSort(daniel);

        System.out.println(Arrays.toString(daniel));
    }
}



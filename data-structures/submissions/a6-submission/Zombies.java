import java.util.Arrays;
import java.util.List;

public class Zombies {
    // Inefficient code for testing
    public static int naiveCountBad(int[] hs) {
        int badPairs=0;
        for (int i=0;i<hs.length;i++)
            for (int j=i+1;j<hs.length;j++)
                if (hs[i] < hs[j]) badPairs++;
        return badPairs;
    }

    public static void mergeInto(int[] a, int[] b, int[] out, int[] x) {
        int i=0, j=0;
        for (int k = 0; k < out.length; k++) { // Returns sorted list
            if (i >= a.length) { // are we out of a?
                out[k] = b[j++];
            } else if (j >= b.length) { // are we out of b?
                out[k] = a[i++];
            } else if (a[i] < b[j]) { // bad pair
                out[k] = b[j++];
                x[0] += a.length - i; // everything else in a should be in the wrong order
            } else {
                out[k] = a[i++];
            }
        }
    }

    public static void mergeSort(int[] a, int[] x) {
        if (a.length <= 1) { return; }

        int[] left = Arrays.copyOfRange(a, 0, a.length/2);
        int[] right = Arrays.copyOfRange(a, a.length/2, a.length);

        mergeSort(left, x); // recursive the left side
        mergeSort(right, x); // recursive the right side
        mergeInto(left, right, a, x); // merge the two recursed bits together into a
    }

    public static int countBad(int[] hs) {
        int[] ans = {0};
        mergeSort(hs, ans);
        return ans[0];
    }

    public static void main(String[] args) {
        System.out.println(countBad(new int[]{35, 22, 10}) == naiveCountBad(new int[]{35, 22, 10}));
        System.out.println(countBad(new int[]{3,1,4,2}) == naiveCountBad(new int[]{3,1,4,2}));
        System.out.println(countBad(new int[]{1, 7, 22, 13, 25, 4, 10, 34, 16, 28, 19, 31}) == naiveCountBad(new int[]{1, 7, 22, 13, 25, 4, 10, 34, 16, 28, 19, 31}));
        System.out.println(countBad(new int[]{1,5,5,5,5}) == naiveCountBad(new int[]{1,5,5,5,5}));
    }
}

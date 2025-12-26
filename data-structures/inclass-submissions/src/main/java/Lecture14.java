import java.util.Arrays;

public class Lecture14 {
    // Exercise 1
    public static int linearSearch(String[] array, String targetKey) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(targetKey)) { return i; }
        }
        return -1;
    }
    // Exercise 2
    public static int binarySearch(String[] array, String targetKey) {
        if (array.length < 1) return -1;
        int s = 0;
        int t = array.length - 1;
        while (s <= t) {
            int mid = (t+s) / 2;
            if (array[mid].equals(targetKey)) { return mid; }
            if ((int) array[mid].charAt(0) > (int) targetKey.charAt(0)) { t = mid - 1; }
            if ((int) array[mid].charAt(0) < (int) targetKey.charAt(0)) { s = mid + 1; }
        }
        return -1;
    }

    // Exercise 3
    // Runtime is relative to xs.length, will call this n for simplicity
    int primSum(int[] xs) {
        if (xs.length == 1) return xs[0]; // Theta(1) constant
        if (xs.length == 2) return xs[0] + xs[1]; // Theta(1) constant
        else {
            int[] ys = Arrays.copyOfRange(xs, 1, xs.length); // Theta(n)
            return xs[0]+xs[1]+primSum(ys); // T(n - 2)
        }
    }
    // Total runtime = Theta(1) * Theta(1) * Theta(n) * T(n-2)
    // = T(n-2) * Theta(n)
    // => O(n^2) (using lookup table of common recurrences)

    int whazIt(int[] ys) {
        if (ys.length == 0) return 0; // Theta(1) constant
        if (ys.length == 1) return ys[0]; // Theta(1) constant
        int n = ys.length; // Theta(1)
        int m = n/2; // Theta(1)
        for (int i=0;i<n;i++) { // Theta( n(n-1)/2 ) => Theta(n^2)
            int theSum = 0; // Theta(1)
            for (int j=0;j<=i;j++) { theSum += ys[j]; } // Theta(i)
            ys[i] = theSum; // Theta(1)
        }

        int a = whazIt(Arrays.copyOfRange(ys, 0, m)); // T(n/2)
        int b = whazIt(Arrays.copyOfRange(ys, m, ys.length)); // T(n/2)
        return a + b; //Theta(1)
    }
    // Total runtime = 2 * T(n/2) * Theta(n^2)
    // = O(n^2) (using lookup table of common recurrences)

    public static void main(String[] args) {
        System.out.println(linearSearch(new String[]{"ab", "cc", "bex", "def"}, "cc")); // expected: 1
    }
}

import java.util.Arrays;

public class Happy {
    public static long sumOfDigitsSquared(long n) {
        long sum = 0;
        for (int i = 0; i < String.valueOf(n).length(); i++) {
            // Not sure how to make this shorter tbh
            long l = Long.parseLong(String.valueOf(String.valueOf(n).charAt(i)));
            sum += l * l;
        }
        return sum;
    }
    public static boolean isHappy(long n) {
        if (sumOfDigitsSquared(n) == 1) {
            return true;
        } else if (sumOfDigitsSquared(n) == 4) {
            return false;
        } else {
            return isHappy(sumOfDigitsSquared(n));
        }
    }
    public static boolean isHappyNR(long n) {
        while (n != 1 && n != 4) {
            n = sumOfDigitsSquared(n);
        }
        if (n == 1) {
            return true;
        } else {
            return false;
        }
    }
    public static long[] firstK(int k) {
        long[] ans = new long[k];
        long number = 1;
        int index = 0;
        while (index < k) {
            if (isHappyNR(number)) {
                ans[index] = number;
                index++;
            }
            number++;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(sumOfDigitsSquared(199));
        System.out.println(isHappy(100));
        System.out.println(isHappy(111));
        System.out.println(isHappy(1234));
        System.out.println(isHappy(989));
        System.out.println(Arrays.toString(firstK(20_000_000)));
    }
}

// Notes
// Recursive isHappy took 4:30 to run k=20 mil
// Non-recursive isHappy took 1:26
// Gonna keep the recursive one anyway, but not using

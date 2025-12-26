import java.math.BigInteger;
import java.util.Arrays;

public class Exercise2 {
    public static BigInteger fac(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i=1;i<=n;i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
    public static int numTrailingZeroes(int n) {
        String num = fac(n).toString();
        int trailing = 0;
        for (int i=num.length()-1;i>=0;i--) {
            if (num.charAt(i) == '0') {
                trailing++;
            } else {
                return trailing;
            }
        }

        return trailing;
    }
    public static void windowPosSum(int[] a, int n) {
        int len = a.length;
        int result = 0;
        for (int x=0;x<len;x++) {
            result = 0;
            if (a[x] > 0) {
                for (int y = x; y <= x + n; y++) {
                    if (y == len) {
                        break;
                    }
                    result += a[y];
                }
                a[x] = result;
            }
        }
    }
    public static void main(String[] args) {
        // Test Exercise 1
        int x = numTrailingZeroes(20);
        System.out.println(x);
        // Test Exercise II (1)
        int[] example = {1, 2, -3, 4, 5, 4};
        windowPosSum(example, 3);
        System.out.println(Arrays.toString(example));
        // Test Exercise II (2)
        int[] example2 = {1, -1, -1, 10, 5, -1};
        windowPosSum(example2, 2);
        System.out.println(Arrays.toString(example2));
    }
}

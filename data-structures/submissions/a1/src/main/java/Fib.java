import java.math.BigInteger;

public class Fib {
    // Helper function to find the nth Fibonacci number
    // Based off the formula given in the assignment
    public static BigInteger fib(int n) {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        } else {
            return fib(n - 1).add(fib(n - 2));
        }
    }
    public static int firstNDigit(int n) {
        int k = 1;
        while (k <= 40_000) {
            if (fib(k).toString().length() == n) {
                return k;
            }
            k++;
        }
        return k; // Failsafe
    }

    public static void main(String[] args) {
        System.out.println(firstNDigit(1));
        System.out.println(firstNDigit(2));
        System.out.println(firstNDigit(3));
        System.out.println(firstNDigit(4));
        System.out.println(firstNDigit(40000));
    }
}

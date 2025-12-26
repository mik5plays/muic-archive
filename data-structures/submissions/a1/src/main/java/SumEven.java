public class SumEven {
    // Approximately how many steps does this function need as a function of n?
    // >> n steps or Big O time complexity O(n)
    public static long sumEven(int n) {
        long result = 0;
        for (long i = 1; i <= n; i++) {
            result += 2 * (i-1);
        }
        return result;
    }
    public static void main(String[] args) {
        long a = sumEven(3);
        System.out.println(a);
    }
}

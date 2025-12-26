public class lesson7 {
    // This utility function returns the index of the largest number in an int array.
    // It is straightforward and it works.
    // However, this is DSA, so we want a function that accepts generic types instead.
    int maxIndex(int[] items) {
        if (items.length == 0) { return -1; }
        int maxDex = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] > items[maxDex]) {
                maxDex = i;
            }
        }
        return maxDex;
    }
    /*
    We have two ideas.

    1. Explicit Higher-Order Comparison Function
    def max(x, y, isLarger) {
        if isLarger(x,y)
            return x
        else
            return y

    2. Polymorphism
    def max(x, y) {
        if x.isLarger(y)
            return x
        else
            return y

    Both of these ideas are valid, so here is how to implement them.
     */
    interface IntUnaryFunction {
        int apply(int x);
    }

    static class foo implements IntUnaryFunction {
        public int apply(int x) {
            return 2 * x;
        }
    }

    static int repeat(IntUnaryFunction f, int x) {
        return f.apply(f.apply(x));
    }

        // Example usage
    public static void main(String[] args) {
        foo x = new foo();
        System.out.println(repeat(x, 5));
    }
}


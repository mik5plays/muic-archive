
public class CountIf<T>{

    private Predicate<T> pred;

    private static class isEven implements Predicate<Integer> {
        @Override
        public boolean test(Integer x) {
            return x%2 == 0;
        }
    }

    private static class isOdd implements Predicate<Integer> {
        @Override
        public boolean test(Integer x) {
            return x%2 != 0;
        }
    }

    public CountIf(Predicate<T> pred) {
        this.pred = pred;
    }

    public int count(T[] items) {
        int result = 0;
        for (T item: items) {
            if (pred.test(item)) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CountIf<Integer> countEven = new CountIf<>(new isEven());
        CountIf<Integer> countOdd = new CountIf<>(new isOdd());
        Integer[] numbers = new Integer[]{1,2,3,4,5};
        System.out.println(countEven.count(numbers));
        System.out.println(countOdd.count(numbers));
    }
}


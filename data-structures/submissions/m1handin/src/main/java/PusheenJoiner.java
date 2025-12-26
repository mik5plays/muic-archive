import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class PusheenJoiner<T> implements Iterable<T> {

    private Iterable<T> leftIn;
    private Iterable<T> rightIn;
    private Predicate<T> fill;

    public PusheenJoiner(Iterable<T> left, Iterable<T> right, Predicate<T> filter) {
        leftIn = left;
        rightIn = right;
        fill = filter;
    }
    public PusheenJoiner(Iterable<T> left, Iterable<T> right) {
        leftIn = left;
        rightIn = right;
        fill = new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return true;
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<T> left2 = leftIn.iterator();
            Iterator<T> right2 = rightIn.iterator();

            @Override
            public boolean hasNext() {
                return left2.hasNext() && right2.hasNext();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    if (left2.hasNext()) {
                        if (fill.test(left2.next())) {
                            return left2.next();
                        }
                    }
                    if (fill.test(right2.next())) {
                        return right2.next();
                    }
                    throw new NoSuchElementException();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 2, 3);
        List<Integer> b = List.of(4, 5, 6);
//        PusheenJoiner<Integer> joined = new PusheenJoiner<>(a, b);
//        for (Integer elt : joined)
//            System.out.println(elt); // ==> 1, 2, 3, 4, 5, 6

        PusheenJoiner<Integer> jFiltered = new PusheenJoiner<>(a, b, x -> x % 2 == 1);
        for (Integer elt : jFiltered)
            System.out.println(elt); // ==> 1, 3, 5
    }
}

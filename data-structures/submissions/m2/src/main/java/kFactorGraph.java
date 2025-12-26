import java.util.*;

// A faster kFactor, credit to some dude I watched on Youtube

public class kFactorGraph {
    static void find(List<Integer> result, List<Integer> factors, Integer start) {
        if (start == 1) { // there is a way to reach n
            result.add(1);
            return;
        }

        Iterator<Integer> iter = factors.iterator(); // dispose the invalid factors
        while (iter.hasNext()) {
            Integer next = iter.next();
            if (start % next != 0) { iter.remove(); }
        }

        if (factors.isEmpty()) { // there exists no way to reach n
            result.add(-1);
            return;
        }

        result.add(start);
        find(result, factors, start / factors.get(factors.size() - 1));
    }

    static List<Integer> kFactorization(int n, List<Integer> A) {
        Collections.sort(A); // sort to ensure we get the lexicographically smallest solution.
        List<Integer> result = new ArrayList<>();
        find(result, A, n);

        Collections.reverse(result); // approached the problem backwards so have to reverse the list
        return result;
    }

    public static void main(String[] args) {
        List<Integer> baz = new ArrayList<>();
        baz.add(3);
        baz.add(7);
        baz.add(5);
        System.out.println(kFactorization(45, baz));
    }
}

//        if (factors.isEmpty()) { // there exists no way to reach n
//        result.add(-1);
//            return;
//                    }
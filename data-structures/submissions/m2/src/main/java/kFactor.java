import java.util.*;

// Slow but working kFactorization that does not involve graphs.

public class kFactor {
    public static void findFactor(List<List<Integer>> result, List<Integer> factors, List<Integer> possibleFactors, Integer start) {
        if (start == 1) { // reached 1, so we have a list of factors
            result.add(new ArrayList<>(factors));
            return;
        }
        for (Integer factor: possibleFactors) {
            if (start % factor == 0) { // check if integer
                factors.add(factor);
                findFactor(result, factors, possibleFactors, start / factor);
                factors.remove(factors.size() - 1);
            }
        }
    }

    public static List<Integer> smallest(List<Integer> a, List<Integer> b) {
        // now we assume a and b are sorted & the same length.
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > b.get(i)) { return b; }
            if (b.get(i) > a.get(i)) { return a; }
        }
        return a; // safeguard, they are the exact same lexicographically
    }

    public static List<List<Integer>> removeDuplicates(List<List<Integer>> array) {
        List<List<Integer>> result = new ArrayList<>();
        Map<List<Integer>, List<Integer>> uniqueAndDupes = new HashMap<>();

        for (List<Integer> list : array) {
            // Create a canonical (sorted) version of the list
            List<Integer> sorted = new ArrayList<>(list);
            Collections.sort(sorted); // Sorting the list once

            // Track the lexicographically smallest list for each canonical key
            uniqueAndDupes.merge(sorted, list, kFactor::smallest);
        }

        // Add the unique lists to the result
        result.addAll(uniqueAndDupes.values());

        return result;
    }

    static List<Integer> kFactorization(int n, List<Integer> A) {
        // Write your code here
        List<Integer> lexiSmallest = new ArrayList<>();
        List<List<Integer>> valid = new ArrayList<>();
        findFactor(valid, new ArrayList<>(), A, n);
        if (valid.isEmpty()) { lexiSmallest.add(-1); return lexiSmallest; } // no possible factors

        valid = removeDuplicates(valid);

        List<List<Integer>> smallestPossibles = new ArrayList<>();
        int minState = Integer.MAX_VALUE;

        for (List<Integer> list: valid) {
            if (list.size() < minState) { minState = list.size(); }
        }
        for (List<Integer> list: valid) {
            if (list.size() == minState) { smallestPossibles.add(list); }
        }

        lexiSmallest = smallestPossibles.get(0);
        for (List<Integer> list: smallestPossibles) {
            lexiSmallest = smallest(lexiSmallest, list);
        }

        List<Integer> result = new ArrayList<>();
        int num = 1;
        result.add(num);
        for (Integer factor: lexiSmallest) {
            num = num * factor;
            result.add(num);
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> baz = new ArrayList<>();
        baz.add(2);
        baz.add(3);
        baz.add(4);
        System.out.println(kFactorization(12, baz));
    }
}

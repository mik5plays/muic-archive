import java.util.*;
import java.util.Set;
import java.util.HashSet;

public class codeTest {
    // I've noticed that by storing it as a set, it is WAY faster.
    // since there is no need to check for dupes.
    // For instance, this code where the Map stored List<List<Integer>> took 18 minutes to run AllRPals(224)
    // This code instead does the same operation in 1 second.

    private Map<Integer, Set<List<Integer>>> storageAllRPals = new HashMap<>();

    public static int sum(List<Integer> n) {
        int sum = 0;
        for (int x : n) {
            sum += x;
        }
        return sum;
    }

    public static int sum(int[] n) {
        int sum = 0;
        for (int x : n) {
            sum += x;
        }
        return sum;
    }

    public static boolean isRecursivelyPalindromic(List<Integer> x) {
        if (x.isEmpty()) {
            return true;
        }
        return isRecursivelyPalindromic(x.subList(0, x.size()/2)) && isPalindrome(x);
    }

    public static boolean isPalindrome(List<Integer> x) {
        int start = 0;
        int end = x.size() - 1;

        while (start < end) {
            if (!Objects.equals(x.get(start), x.get(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public Set<List<Integer>> AllRPals(int n) {
        if (storageAllRPals.containsKey(n)) {
            return storageAllRPals.get(n);
        } else {
            return computeAllRPals(n);
        }
//         storageAllRPals.computeIfAbsent(n, this::computeAllRPals);
//         return storageAllRPals.get(n);
//         Does not work for me for some reason
    }

    private Set<List<Integer>> computeAllRPals(int n) {

        if (n == 0) { // Catch if n = 0, return empty list
            Set<List<Integer>> result = new HashSet<List<Integer>>();
            storageAllRPals.put(n, result);
            return result;
        }

//         Automatically consider that [n] is a recursive palindrome
        Set<List<Integer>> result = new HashSet<List<Integer>>();
        List<Integer> sole = new ArrayList<>();
        sole.add(n);
        result.add(sole);


        if (n >= 2) {
            if (n % 2 == 0) {
                Set<List<Integer>> subList = AllRPals(n / 2);
                for (List<Integer> foo: subList) {
                    List<Integer> newList = new ArrayList<Integer>(foo);
                    newList.addAll(foo);
                    if (sum(newList) == n) {
                        result.add(newList);
                    }
                }
            }

            for (int i = (n % 2 != 0) ? 1: 2; i <= n - 2; i += 2) { // Our catch for 0 should help prevent errors here
                // Maximum value of i should be n-2
                Set<List<Integer>> sublist = AllRPals((n - i) / 2);
                for (List<Integer> foo : sublist) {
                    List<Integer> newList = new ArrayList<Integer>(foo);
                    newList.add(i);
                    newList.addAll(foo);
                    if (isRecursivelyPalindromic(newList) && sum(newList) == n) {
                        result.add(newList);
                    }
                }
            }
        }

        for (int i = (n % 2 == 0) ? 2 : 1; i <= n/2; i += 2) {
            if (n % i == 0) { // No remainder
                List<Integer> newList = new ArrayList<Integer>();
                for (int k = 0; k < n/i; k++) {
                    newList.add(i);
                }
                if (sum(newList) == n) { // No dupes
                    result.add(newList);
                }
            }
        }

        storageAllRPals.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        codeTest test = new codeTest();
        System.out.println(test.AllRPals(7));
        System.out.println(test.AllRPals(99).size());
        System.out.println(test.AllRPals(224).size());
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;

public class RPal2 {
    private Map<Integer, List<List<Integer>>> found = new HashMap<>();

    public List<List<Integer>> AllRPals(int n) {
        if (n <= 0) {
            return new ArrayList<List<Integer>>(); // Handle edge case
        }
        if (found.containsKey(n)) {
            return found.get(n);
        } else {
            return computeAllRPals(n);
        }
    }

    private List<List<Integer>> computeAllRPals(int n) {
        List<List<Integer>> result = new ArrayList<>();

        // Consider that [n] is a recursive palindromic list
        List<Integer> sole = new ArrayList<>();
        sole.add(n);
        result.add(sole);

        // Create palindromic lists
        for (int i = 1; i <= n / 2; i++) {
            List<List<Integer>> sublist = AllRPals(n - (2 * i));
            for (List<Integer> foo : sublist) {
                List<Integer> newList = new ArrayList<>(foo);
                newList.add(0, i);
                newList.add(i);
                result.add(newList);
            }
        }
        found.put(n, result); // Cache the result
        return result;
    }


    public static void main(String[] args) {
        RPal2 rpal = new RPal2();
        int n = 7;

        // Get all recursively palindromic partitions of n
        List<List<Integer>> palindromicPartitions = rpal.AllRPals(n);

        // Print the results
        for (List<Integer> partition : palindromicPartitions) {
            System.out.println(partition);
        }
    }
}

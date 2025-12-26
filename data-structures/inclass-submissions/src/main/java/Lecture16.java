import java.util.*;

class DisjointSet {
    List<Set<Integer>> disjointSets = new ArrayList<>();
    // Map<Element, # Set>
    // A new Map that maps a person to Set i
    Map<Integer, Integer> peopleToIndex = new HashMap<>();

    // Runtime = O(n)
    // For loop creates a List with n sets.
    DisjointSet(int n) {
        for (int i = 0; i < n; i++) {
            // Proposal A: We do not need disjointSet.
            disjointSets.add(new HashSet<>(List.of(i)));
            peopleToIndex.put(i, i); //
        }
        // Suppose we have "elements" 0,1,2,3
        // our constructor should take in n = 4
        // to create a List of Sets that look like [ {0}, {1}, {2}, {3} ]
    }


    // Returns the index into disjointSets where x belongs
    // Worst case = O(n) (have to go through all the sets)
    // Best case = O(1) (the first Set contains x)
//    int findSet(int x) {
//        for (int i = 0; i < disjointSets.size(); i++) {
//            if (disjointSets.get(i).contains(x)) {
//                return i;
//            }
//        }
//        throw new NoSuchElementException("no element found!");
//    }

    // A better findSet implementation, O(1)
    int findSet(int x) {
        return peopleToIndex.get(x);
    }

    // Bottleneck: old findSet runtime = O(n) (worst case)
    // Now, runtime should be constant O(1).
    boolean isConnected(int x, int y) {
        return findSet(x) == findSet(y);
    }

    // Joins two elements together
    // Runtime = O(n) !! (based on runtime of findSet) !!
    // Space = sum of size of all Sets --> n

//    void link(int x, int y) {
//        if (isConnected(x, y)) { return; } // Check if they already have a link
//        int indexOfX = findSet(x);
//        int indexOfY = findSet(y);
//        disjointSets.get(indexOfX).addAll(disjointSets.get(indexOfY)); // concatenate two Sets together
//        disjointSets.remove(indexOfY); // remove the extra Set
//    }

    // Joins two elements together
    // O(1) now since consider that HashMap takes constant time to look up.
    // Space = n
    void link(int x, int y) {
        if (isConnected(x, y)) { return; } // Check if they already have a link -> O(1);
        int indexOfX = findSet(x);
        int indexOfY = findSet(y);
        disjointSets.get(indexOfX).addAll(disjointSets.get(indexOfY)); // concatenate two Sets together

        // Map each element in Y's old Set to X's set.
        for (int elem: disjointSets.get(indexOfY)) { peopleToIndex.put(elem, indexOfX); }
        disjointSets.get(indexOfY).clear(); // clear the extra Set.
    }
}

public class Lecture16 {

}

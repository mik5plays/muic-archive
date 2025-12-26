import java.util.*;


/*
This DisjointSet implementation uses HashMap.
 */
class DisjointMap {
    // Map<Element, # Set>
    // A new Map that maps a person to Set i
    Map<Integer, Integer> peopleToIndex = new HashMap<>();

    // Runtime = O(n)
    // For loop creates a List with n sets.
    DisjointMap(int n) {
        for (int i = 0; i < n; i++) {
            // Proposal A: We do not need disjointSet.
            peopleToIndex.put(i, i); //
        }
        // Suppose we have "elements" 0,1,2,3
        // our constructor should take in n = 4
        // to create a List of Sets that look like [ {0}, {1}, {2}, {3} ]
    }

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
    // O(n) now since consider that HashMap takes constant time to look up.
    // Space = n
    void link(int x, int y) {
        if (isConnected(x, y)) { return; } // Check if they already have a link -> O(1);
        int indexOfX = findSet(x); // O(1)
        int indexOfY = findSet(y); // O(1)

        // Map each element in Y's old Set to X's set.

        // Move whatever element that is in Y's set to X's set, including Y.
        for (int i = 0; i < peopleToIndex.size(); i++) {
            if (peopleToIndex.get(i) == indexOfY) {
                peopleToIndex.put(i, indexOfX);
            }
        }
    }
}

/*
This further optimized DisjointSet uses primitive arrays instead of a Map.
However, this still has the same runtime as the one above.
 */
class DisjointPrim {
    int[] peopleToIndex;

    DisjointPrim(int n) {
        peopleToIndex = new int[n];
        for (int i = 0; i < n; i++) {
            peopleToIndex[i] = i;
        }
    }
    int findSet(int x) {
        return peopleToIndex[x];
    }

    boolean isConnected(int x, int y) {
        return findSet(x) == findSet(y);
    }

    void link(int x, int y) {
        if (isConnected(x, y)) { return; } // Check if they already have a link -> O(1);
        int indexOfX = findSet(x); // O(1)
        int indexOfY = findSet(y); // O(1)

        // Map each element in Y's old Set to X's set.

        // Move whatever element that is in Y's set to X's set, including Y.
        for (int i = 0; i < peopleToIndex.length; i++) {
            if (peopleToIndex[i] == indexOfY) {
                peopleToIndex[i] = indexOfX;
            }
        }
    }
}

public class optimizedDisjointSet {

}

// A "good" BST has height log_2(n)

// integer Node (for demonstration purposes)
class Node{
    int key;
    Node left, right;
}

public class Lecture19 {
    // returns the largest key in x
    // runtime proportional to height of tree x, so around O(logn)
    int lastKey(Node x) throws Exception {
        if (x == null) throw new Exception("What the hell!");
        if (x.right == null) return x.key;
        return lastKey(x.right);
    }

    // does x contain the key k?
    // also O(logn)
    boolean containsKey(Node x, int k) {
        if (x == null) return false; // base case: tree is empty
        if (x.key == k) { return true; }
        else if (x.key < k) { return containsKey(x.right, k); } // traverse right of tree
        else { return containsKey(x.left, k); } // traverse left of tree

    }

    // returns the largest key in x <= k
    //    null if none of the keys in x <= k
    //
    // runtime is proportional to the height of the tree, such that = O(logn) for "good" trees
    Integer floorKey(Node x, int k) throws Exception {
        if (x == null) return null;
        if (containsKey(x, k)) return k;
        else if (k < x.key) { return floorKey(x.left, k); }
        else {
            Integer rightFloor = floorKey(x.right, k);
            return (rightFloor == null) ? x.key: rightFloor;
        }
    }

}

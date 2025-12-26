import java.util.Arrays;
import java.util.Comparator;

public class MakeTree {

    static void mergeInto(int[] a, int[] b, int[] out) {
        int i=0, j=0;
        // Ajarn's method
        for (int k = 0; k < out.length; k++) {
            if (i >= a.length) // are we out of a?
                out[k] = b[j++];
            else if (j >= b.length)  // are we out of b?
                out[k] = a[i++];
            else if (a[i] < b[j]) // we are still within the bounds of a and b.
                out[k] = a[i++]; // i will increment
            else
                out[k] = b[j++]; // j will increment'
        }
    }
    static void mergeSort(int[] a) {
        if (a.length <= 1) { return; }

        int[] left = Arrays.copyOfRange(a, 0, a.length/2);
        int[] right = Arrays.copyOfRange(a, a.length/2, a.length);

        mergeSort(left); // recursive the left side
        mergeSort(right); // recursive the right side
        mergeInto(left, right, a); // merge the two recursed bits together into a
    }

    static BinaryTreeNode buildHelper(int[] keys, int start, int end) { // 2T(n/2) + O(1) = O(n) (recurrence table)
        if (start > end) { return null; }

        int mid = (start + end) / 2; // 0(1)

        // since the array is sorted, we know elements to the left and right
        // will be the first and second halves respectively:
        // we split in half until length = 1 so we do it log_2(n) times which is the max depth of the tree (excluding root)
        BinaryTreeNode node = new BinaryTreeNode(keys[mid]);
        node.left = buildHelper(keys, start, mid - 1); // T(n/2)
        node.right = buildHelper(keys, mid + 1, end); // T(n/2)

        return node;
    }

    public static BinaryTreeNode buildBST(int[] keys) { // nlogn dominates n so runtime = O(nlogn)
        BinaryTreeNode ans = new BinaryTreeNode(keys[0]);
        mergeSort(keys); // O(nlogn) average case for mergesort
        return buildHelper(keys, 0, keys.length - 1); // O(n)
    }

    static void inOrder(BinaryTreeNode tree) {
        if (tree == null) { return; }
        inOrder(tree.left);
        System.out.println(tree.key);
        inOrder(tree.right);
    }

    public static void main(String[] args) {
        int[] keys = {1,4,8,10,19,12,23};
        BinaryTreeNode keyTree = buildBST(keys);
        inOrder(keyTree);

    }

}

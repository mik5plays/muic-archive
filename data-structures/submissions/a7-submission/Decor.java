import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Decor {
    public static BinaryTreeNode mkTree(List<Integer> postOrder, List<Integer> inOrder) {
        if (postOrder.isEmpty() || inOrder.isEmpty()) { return null; }
        if (postOrder.size() == 1 || inOrder.size() == 1) { return new BinaryTreeNode(postOrder.get(0)); }

        // (1) determine the root’s key from the given sequences;
        // post order - left, right, root so naturally last element of postOrder is the root
        Integer root = postOrder.get(postOrder.size() - 1);

        BinaryTreeNode tree = new BinaryTreeNode(root);

        int rootIndex = inOrder.indexOf(root);
        int leftMostIndex = rootIndex - 1;

        // (2) carve out the post- and in- order traversal sequences for the left subtree;
        tree.left = mkTree(postOrder.subList(0, leftMostIndex + 1), inOrder.subList(0, rootIndex)); // (4) solve these recursively.
        // (3) carve out the post- and in- order traversal sequences for the right subtree;
        tree.right = mkTree(postOrder.subList(leftMostIndex + 1, postOrder.size() - 1), inOrder.subList(rootIndex + 1, inOrder.size())); // (4) solve these recursively.

        return tree;
    }

    public static void main(String[] args) {
        // based on example tree in the assignment but i made letters into numbers instead
        int[] x = {4,5,2,3,1};
        int[] y = {4,2,5,1,3};
        List<Integer> post = new ArrayList<>();
        for (int num: x) { post.add(num); }
        List<Integer> in = new ArrayList<>();
        for (int num: y) { in.add(num); }
        MakeTree.inOrder(mkTree(post, in));
    }


}

import java.lang.StringBuilder;

public class Lecture18 {
    static class TreeNode<E> {
        E key;
        TreeNode<E> left, right;

        TreeNode(TreeNode<E> left, E key, TreeNode<E> right) {
            this.left = left;
            this.key = key;
            this.right = right;
        }
    }

    public static int count(TreeNode<?> tree) {
        return (tree == null) ? 0 : 1 + count(tree.left) + count(tree.right);
    }

    public static void preOrder(TreeNode<?> tree, StringBuilder x) {
        if (tree == null) return;
        x.append(tree.key);
        preOrder(tree.left, x);
        preOrder(tree.right, x);
    }

    public static String concatPreorder(TreeNode<String> tree) {
        StringBuilder result = new StringBuilder();
        preOrder(tree, result);
        return result.toString();
    }

    public static void main(String[] args) {
        TreeNode<String> d = new TreeNode<String>(null, "D", null);
        TreeNode<String> e = new TreeNode<String>(null, "E", null);
        TreeNode<String> f = new TreeNode<String>(null, "F", null);
        TreeNode<String> c = new TreeNode<String>(d, "C", null);
        TreeNode<String> b = new TreeNode<String>(e, "B", f);
        TreeNode<String> a = new TreeNode<String>(c, "A", b);

        System.out.println(concatPreorder(a));
        System.out.println(count(a));
        System.out.println(count(null));
    }
}

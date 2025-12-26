import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Uproot {
    public static void treeToParentBuilder(BinaryTreeNode node, Integer parent, HashMap<Integer, Integer> parentMap) {
        if (node == null) { return; }
        // example in assignment doesn't put the root inside the map
        // so I will follow (a root's parent is itself in this case)
        if (node.key != parent)
            parentMap.put(node.key, parent);
        treeToParentBuilder(node.left, node.key, parentMap);
        treeToParentBuilder(node.right, node.key, parentMap);
    }
    public static HashMap<Integer, Integer> treeToParentMap(BinaryTreeNode T) {
        HashMap<Integer, Integer> ans = new HashMap<>();
        treeToParentBuilder(T, T.key, ans);
        return ans;
    }

    public static BinaryTreeNode parentMapToTree(Map<Integer, Integer> map) {
        Integer root = null;
        Map<Integer, BinaryTreeNode> nodes = new HashMap<>();

        // make all entries in the tree the node.
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            if (map.get(entry.getValue()) == null) { root = entry.getValue(); }
            if (nodes.get(entry.getKey()) == null)
                nodes.put(entry.getKey(), new BinaryTreeNode(entry.getKey()));
            if (nodes.get(entry.getValue()) == null)
                nodes.put(entry.getValue(), new BinaryTreeNode(entry.getValue()));
        }
        if (root == null) { return null; } // there is no root in the tree

        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            BinaryTreeNode parent = nodes.get(entry.getValue());
            BinaryTreeNode child = nodes.get(entry.getKey());

            // "You are free to choose which is your left node and which is your right node"
            if (parent.left == null) {
                parent.left = child;
            } else if (parent.right == null) {
                parent.right = child;
            }
        }

        return nodes.get(root);

    }


    public static void main(String[] args) {
        int[] keys = {1,20,9,14,2,18};
        BinaryTreeNode keyTree = MakeTree.buildBST(keys); // binary search tree

        System.out.println(treeToParentMap(keyTree));
        BinaryTreeNode theTree = parentMapToTree(treeToParentMap(keyTree));
        MakeTree.inOrder(theTree);

    }

}

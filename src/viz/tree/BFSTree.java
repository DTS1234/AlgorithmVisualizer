package viz.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTree {

    private static final Queue<TreeNode> stack = new LinkedList<>();

    public static TreeNode step(TreeNode tree) {

        if (tree.left != null) {
            stack.add(tree.left);
        }

        if (tree.right != null) {
            stack.add(tree.right);
        }

        if (stack.isEmpty()) {
            return null;
        }
        return stack.poll();
    }

    public static List<Integer> run(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {
            result.add(current.val);
            current = step(current);
        }
        return result;
    }
}

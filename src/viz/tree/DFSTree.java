package viz.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFSTree {

    private static final Stack<TreeNode> stack = new Stack<>();

    public static TreeNode step(TreeNode tree) {

        if (tree.left != null) {
            stack.push(tree.left);
        }

        if (tree.right != null) {
            stack.push(tree.right);
        }

        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
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

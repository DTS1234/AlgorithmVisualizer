import viz.Program;
import viz.tree.TreeNode;
import viz.tree.TreeVisualizer;

public class Main {

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1,
            new TreeNode(2,
                new TreeNode(4),
                new TreeNode(5)
            ),
            new TreeNode(3,
                null,
                new TreeNode(6, new TreeNode(7), new TreeNode(8, new TreeNode(9), null))
            )
        );

        new Program().run(root, "BFS");
    }
}
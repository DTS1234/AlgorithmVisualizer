package viz.tree;

import javax.swing.JFrame;
import java.util.List;

public class TreeVisualizer extends JFrame {

    public TreeVisualizer(TreeNode root, String algorithm) {
        setTitle("Binary Tree Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Integer> run = switch (algorithm){
            case "BFS" -> BFSTree.run(root);
            case "DFS" -> DFSTree.run(root);
            default -> DFSTree.run(root);
        };

        add(new TreePanel(root, run));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

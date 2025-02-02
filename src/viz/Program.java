package viz;

import viz.tree.TreeNode;
import viz.tree.TreeVisualizer;

import javax.swing.SwingUtilities;

public class Program {

    public void run(TreeNode root, String algorithm) {
        SwingUtilities.invokeLater(() -> {
            new TreeVisualizer(root, algorithm);
        });
    }

}

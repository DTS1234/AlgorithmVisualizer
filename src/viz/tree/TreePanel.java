package viz.tree;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TreePanel extends JPanel {

    private TreeNode root;
    private Map<TreeNode, Point> nodePositions; // stores the (x, y) for each node
    private int nodeSize = 30;                // diameter of each node (circle)
    private int horizontalSpacing = 50;       // horizontal space between nodes
    private int verticalSpacing = 50;         // vertical space between levels
    private int panelWidth, panelHeight;

    private Set<Integer> highlightedValues = new HashSet<>();

    private int highlightIndex = 0;

    private Timer timer;

    public TreePanel(TreeNode root, List<Integer> highlightSequence) {
        this.root = root;
        this.nodePositions = new HashMap<>();
        computeNodePositions();
        panelWidth = computePanelWidth();
        panelHeight = computePanelHeight();
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        timer = new Timer(1000, e -> {
            highlightedValues.clear();
            if (highlightIndex < highlightSequence.size()) {
                int targetVal = highlightSequence.get(highlightIndex);
                highlightedValues.add(targetVal);
                highlightIndex++;
                repaint();
            }

            if (highlightIndex == highlightSequence.size()) {
                timer.stop();
            }
        });

        timer.start();
    }

    // Computes the maximum width based on computed x-positions.
    private int computePanelWidth() {
        int maxX = 0;
        for (Point p : nodePositions.values()) {
            maxX = Math.max(maxX, p.x);
        }
        return maxX + nodeSize + horizontalSpacing;
    }

    // Computes the maximum height based on computed y-positions.
    private int computePanelHeight() {
        int maxY = 0;
        for (Point p : nodePositions.values()) {
            maxY = Math.max(maxY, p.y);
        }
        return maxY + nodeSize + verticalSpacing;
    }

    // Performs an in-order traversal to assign an x-coordinate to each node.
    // The y-coordinate is computed from the depth.
    private void computeNodePositions() {
        // Use a mutable "counter" for the x-coordinate.
        int[] xCounter = {horizontalSpacing};  // start with some margin
        assignCoordinates(root, 0, xCounter);
    }

    /**
     * Recursively assign coordinates to each node.
     *
     * @param node     The current tree node.
     * @param depth    The depth (level) of the current node.
     * @param xCounter A one-element array acting as a mutable counter for x.
     */
    private void assignCoordinates(TreeNode node, int depth, int[] xCounter) {
        if (node == null) {
            return;
        }

        // Visit left subtree first.
        assignCoordinates(node.left, depth + 1, xCounter);

        // Assign this node its x and y coordinates.
        int x = xCounter[0];
        int y = depth * (nodeSize + verticalSpacing) + verticalSpacing;
        nodePositions.put(node, new Point(x, y));
        xCounter[0] += nodeSize + horizontalSpacing;  // increment counter for the next node

        // Visit right subtree.
        assignCoordinates(node.right, depth + 1, xCounter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // First, draw the edges connecting nodes.
        drawConnections(g, root);
        // Then, draw each node.
        for (Map.Entry<TreeNode, Point> entry : nodePositions.entrySet()) {
            TreeNode node = entry.getKey();
            Point p = entry.getValue();
            drawNode(g, p.x, p.y, node.val);
        }
    }

    // Draws a line from the given parent node to its children.
    private void drawConnections(Graphics g, TreeNode node) {
        if (node == null) {
            return;
        }
        Point p = nodePositions.get(node);
        int startX = p.x + nodeSize / 2;
        int startY = p.y + nodeSize / 2;

        if (node.left != null) {
            Point leftPos = nodePositions.get(node.left);
            int endX = leftPos.x + nodeSize / 2;
            int endY = leftPos.y + nodeSize / 2;
            g.drawLine(startX, startY, endX, endY);
            drawConnections(g, node.left);
        }
        if (node.right != null) {
            Point rightPos = nodePositions.get(node.right);
            int endX = rightPos.x + nodeSize / 2;
            int endY = rightPos.y + nodeSize / 2;
            g.drawLine(startX, startY, endX, endY);
            drawConnections(g, node.right);
        }
    }

    // Draws a node as a circle with the node's value in the center.
    private void drawNode(Graphics g, int x, int y, int value) {

        // highlighting of the current node
        if (highlightedValues.contains(value)) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }

        g.fillOval(x, y, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, nodeSize, nodeSize);

        // Draw the value (centered).
        String s = String.valueOf(value);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(s);
        int textHeight = fm.getAscent();
        int textX = x + (nodeSize - textWidth) / 2;
        int textY = y + (nodeSize + textHeight) / 2 - 2;
        g.drawString(s, textX, textY);
    }
}

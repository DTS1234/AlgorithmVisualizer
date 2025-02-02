package viz.search;

import viz.search.Visualizer.GraphPanel.Cell;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualizer extends JFrame {

    // Grid dimensions and cell size (in pixels)
    public static final int ROWS = 20;
    public static final int COLS = 20;
    public static final int CELL_SIZE = 30;

    private GraphPanel graphPanel;

    public Visualizer(Algorithm algorithm) {
        setTitle("BFS Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add the graph panel (the drawing area)
        graphPanel = new GraphPanel(ROWS, COLS, CELL_SIZE, algorithm);
        add(graphPanel);
        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Always use the Event Dispatch Thread (EDT) to create and update GUI components
        SwingUtilities.invokeLater(() -> {
            Cell[][] grid = new Cell[ROWS][COLS];
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    grid[r][c] = new Cell(r, c);
                }
            }

            Algorithm algorithm = new DFSAlgorithm(grid, ROWS, COLS);

            algorithm.init();
            new Visualizer(algorithm);
        });
    }

    /**
     * GraphPanel is an inner class extending JPanel. It creates a grid of cells and runs the BFS simulation.
     */
    class GraphPanel extends JPanel {

        private int rows, cols, cellSize;
        private Timer timer;
        private Algorithm algorithm;

        public GraphPanel(int rows, int cols, int cellSize, Algorithm algorithm) {
            this.rows = rows;
            this.cols = cols;
            this.cellSize = cellSize;
            this.algorithm = algorithm;
            setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
            initTimer();
        }

        private void initTimer() {
            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!algorithm.step()) {
                        timer.stop();
                    }
                    repaint();
                }
            });
            timer.start();
        }

        // Override paintComponent to draw the grid and cell states.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Cell[][] grid = algorithm.getGrid();
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c].draw(g, cellSize);
                }
            }
        }

        /**
         * The Cell class represents each node (cell) in the grid.
         */
        public static class Cell {

            int row, col;
            boolean visited = false;
            boolean processing = false;  // Indicates the cell is in the BFS queue.
            Cell parent = null;          // Optional: can be used to trace the BFS path.

            public Cell(int row, int col) {
                this.row = row;
                this.col = col;
            }

            // Draw the cell with a color based on its state.
            public void draw(Graphics g, int size) {
                int x = col * size;
                int y = row * size;

                if (visited) {
                    g.setColor(Color.GREEN);
                } else if (processing) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x, y, size, size);

                // Draw cell borders.
                g.setColor(Color.BLACK);
                g.drawRect(x, y, size, size);
            }

            public void reset() {
                visited = false;
                processing = false;
                parent = null;
            }
        }
    }
}

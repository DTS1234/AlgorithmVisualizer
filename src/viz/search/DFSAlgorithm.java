package viz.search;

import viz.search.Visualizer.GraphPanel.Cell;

import java.util.*;

public class DFSAlgorithm implements Algorithm {
    private Cell[][] grid;
    private int rows, cols;
    private Stack<Cell> stack;

    public DFSAlgorithm(Cell[][] grid, int rows, int cols) {
        this.grid = grid;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void init() {
        stack = new Stack<>();
        // Reset grid state.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c].reset();
            }
        }
        // Start DFS at top-left cell.
        Cell start = grid[0][0];
        start.visited = true;
        start.processing = true;
        stack.push(start);
    }

    @Override
    public boolean step() {
        if (stack.isEmpty()) {
            return false; // Algorithm complete.
        }

        Cell current = stack.pop();
        current.processing = false;
        current.visited = true;

        // For DFS, push neighbors in reverse order (if you care about a specific traversal order).
        for (Cell neighbor : getNeighbors(current)) {
            if (!neighbor.visited && !neighbor.processing) {
                neighbor.processing = true;
                neighbor.parent = current; // Useful for tracing the DFS path.
                stack.push(neighbor);
            }
        }

        return !stack.isEmpty();
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.row;
        int c = cell.col;

        // Adjust the order of adding neighbors to influence DFS order.
        if (r > 0) neighbors.add(grid[r - 1][c]);       // Up
        if (c < cols - 1) neighbors.add(grid[r][c + 1]);  // Right
        if (r < rows - 1) neighbors.add(grid[r + 1][c]);  // Down
        if (c > 0) neighbors.add(grid[r][c - 1]);         // Left

        return neighbors;
    }

    @Override
    public Cell[][] getGrid() {
        return grid;
    }
}

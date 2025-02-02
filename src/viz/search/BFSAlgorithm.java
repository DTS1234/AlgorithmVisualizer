package viz.search;

import viz.search.Visualizer.GraphPanel.Cell;

import java.util.*;

public class BFSAlgorithm implements Algorithm {
    private Cell[][] grid;
    private int rows, cols;
    private Queue<Cell> queue;

    public BFSAlgorithm(Cell[][] grid, int rows, int cols) {
        this.grid = grid;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void init() {
        queue = new LinkedList<>();
        // Clear previous state in the grid.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c].reset();
            }
        }
        // Start BFS at top-left cell.
        Cell start = grid[0][0];
        start.visited = true;
        start.processing = true;
        queue.offer(start);
    }

    @Override
    public boolean step() {
        if (queue.isEmpty()) {
            return false; // Algorithm is complete.
        }

        Cell current = queue.poll();
        current.processing = false;
        current.visited = true;

        // Explore neighbors (up, down, left, right).
        for (Cell neighbor : getNeighbors(current)) {
            if (!neighbor.visited && !neighbor.processing) {
                neighbor.processing = true;
                neighbor.parent = current; // Useful for path tracing.
                queue.offer(neighbor);
            }
        }

        return !queue.isEmpty();
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.row;
        int c = cell.col;

        if (r > 0) neighbors.add(grid[r - 1][c]);       // Up
        if (r < rows - 1) neighbors.add(grid[r + 1][c]);  // Down
        if (c > 0) neighbors.add(grid[r][c - 1]);         // Left
        if (c < cols - 1) neighbors.add(grid[r][c + 1]);  // Right

        return neighbors;
    }

    @Override
    public Cell[][] getGrid() {
        return grid;
    }
}

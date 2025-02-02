package viz.search;

import viz.search.Visualizer.GraphPanel.Cell;

public interface Algorithm {

    boolean step();

    /**
     * Called to initialize the algorithm (if needed).
     */
    void init();

    /**
     * Returns the grid used by the algorithm (so the GUI can repaint it).
     */
    Cell[][] getGrid();
}

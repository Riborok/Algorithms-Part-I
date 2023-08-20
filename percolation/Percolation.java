package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int START_GRID_INDEX = 1;
    private static final int VIRT_TOP = 0;
    private static final int[][] OFFSETS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    private final int gridOrder;
    private final int virtBottom;
    private final WeightedQuickUnionUF topBottomUF;
    private final WeightedQuickUnionUF topUF;
    private final boolean[] grid;
    private int amountOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "The grid size must be greater than 0");
        }

        gridOrder = n;

        // two additional elements for virtual top and bottom
        int gridSize = n * n + 2;
        virtBottom = gridSize - 1;

        topBottomUF = new WeightedQuickUnionUF(gridSize);
        /*
            example of topBottomUF:
            0   1 2 3
                4 5 6
                7 8 9   10
            here 0 and 10 index are virtual top and bottom respectively
        */

        // here only virtual top
        topUF = new WeightedQuickUnionUF(gridSize - 1);
        /*
            example of topUF:
            0   1 2 3
                4 5 6
                7 8 9
            here 0 index is virtual top
        */

        // here no virtual elements
        grid = new boolean[gridSize - 2];
    }

    private boolean isOnGrid(int row, int col) {
        return row >= START_GRID_INDEX && row <= gridOrder &&
                col >= START_GRID_INDEX && col <= gridOrder;
    }

    private int getIndex(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IllegalArgumentException("The index is out of range");
        }
        return (row - 1) * gridOrder + col;
    }

    public void open(int row, int col) {
        final int index = getIndex(row, col);

        if (!grid[index - 1]) {
            grid[index - 1] = true;
            amountOfOpenSites++;

            for (int i = 0; i < OFFSETS.length; i++) {
                int newRow = row + OFFSETS[i][0];
                int newCol = col + OFFSETS[i][1];
                if (isOnGrid(newRow, newCol)) {
                    int neighborIndex = getIndex(newRow, newCol);
                    if (grid[neighborIndex - 1]) {
                        topUF.union(index, neighborIndex);
                        topBottomUF.union(index, neighborIndex);
                    }
                }
            }

            if (row == START_GRID_INDEX) {
                topUF.union(index, VIRT_TOP);
                topBottomUF.union(index, VIRT_TOP);
            }

            if (row == gridOrder) {
                topBottomUF.union(index, virtBottom);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return grid[getIndex(row, col) - 1];
    }

    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        return grid[index - 1] && topUF.find(VIRT_TOP) == topUF.find(index);
    }

    public int numberOfOpenSites() {
        return amountOfOpenSites;
    }

    public boolean percolates() {
        return topBottomUF.find(VIRT_TOP) == topBottomUF.find(virtBottom);
    }

    public static void main(String[] args) {
        int n = 420;
        Percolation percolation = new Percolation(n);

        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(1, n + 1),
                             StdRandom.uniform(1, n + 1));
        }

        double threshold = (double) percolation.numberOfOpenSites() / (n * n);
        String string = String.format("%.3f", threshold);
        System.out.println("Percolation threshold: " + string);
    }
}

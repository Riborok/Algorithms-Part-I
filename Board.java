import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int[][] OFFSETS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    private final int[][] tiles;
    private final int dimension;

    public Board(int[][] tiles) {
        dimension = tiles.length;
        this.tiles = copy(tiles);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension).append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                str.append(" ").append(tiles[i][j]);

            str.append("\n");
        }
        return str.toString();
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int hamming = 0;
        int value = 1;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != value)
                    hamming++;
                value++;
            }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                int value = tiles[i][j];
                if (value != 0) {
                    int row = (value - 1) / dimension;
                    int col = (value - 1) % dimension;
                    manhattan += Math.abs(i - row) + Math.abs(j - col);
                }
            }
        return manhattan;
    }

    public boolean isGoal() {
        int value = 1;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != value &&
                        (i != dimension - 1 || j != dimension - 1 || tiles[i][j] != 0))
                    return false;

                value++;
            }
        return true;
    }

    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass())
            return false;

        Board board = (Board) y;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        int blankRow = 0;
        int blankCol = 0;

        outerLoop:
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break outerLoop;
                }

        for (int i = 0; i < OFFSETS.length; i++) {
            int newRow = blankRow + OFFSETS[i][0];
            int newCol = blankCol + OFFSETS[i][1];
            if (newRow >= 0 && newCol >= 0 && newRow < dimension && newCol < dimension) {
                int[][] newTiles = copy(tiles);
                swap(newTiles, blankRow, blankCol, newRow, newCol);
                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
    }

    public Board twin() {
        int[][] twin = copy(tiles);

        int row = 0;
        int col = 0;
        int nextCol = col + 1;

        if (twin[row][col] == 0 || twin[row][nextCol] == 0)
            row++;

        swap(twin, row, col, row, nextCol);

        return new Board(twin);
    }

    private int[][] copy(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++)
            copy[i] = source[i].clone();
        return copy;
    }

    private void swap(int[][] array, int row1, int col1, int row2, int col2) {
        int temp = array[row1][col1];
        array[row1][col1] = array[row2][col2];
        array[row2][col2] = temp;
    }

    public static void main(String[] args) {

    }
}
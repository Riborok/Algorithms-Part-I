package puzzle8;

import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

public class Solver {
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int totalMovesManhattan;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.totalMovesManhattan = moves + board.manhattan();
        }

        public int compareTo(SearchNode searchNode) {
            return totalMovesManhattan - searchNode.totalMovesManhattan;
        }
    }

    private final Iterable<Board> solution;
    private final boolean solvable;
    private final int moves;

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial board cannot be null");

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        pq.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        while (true) {
            SearchNode lastNode = processNextNode(pq);
            SearchNode twinLastNode = processNextNode(twinPQ);

            if (lastNode.board.isGoal()) {
                solvable = true;
                moves = lastNode.moves;
                solution = buildSolution(lastNode);
                break;
            }

            if (twinLastNode.board.isGoal()) {
                solvable = false;
                moves = -1;
                solution = null;
                break;
            }
        }
    }

    private SearchNode processNextNode(MinPQ<SearchNode> pq) {
        SearchNode node = pq.delMin();

        if (node.previous == null)
            for (Board neighbor : node.board.neighbors())
                pq.insert(new SearchNode(neighbor, node.moves + 1, node));
        else
            for (Board neighbor : node.board.neighbors())
                if (!neighbor.equals(node.previous.board))
                    pq.insert(new SearchNode(neighbor, node.moves + 1, node));

        return node;
    }

    private Iterable<Board> buildSolution(SearchNode searchNode) {
        LinkedList<Board> path = new LinkedList<>();
        while (searchNode != null) {
            path.addFirst(searchNode.board);
            searchNode = searchNode.previous;
        }
        return path;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {

    }
}
/**
 * Created by Ishan on 15-01-2018.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;

        private SearchNode(Board passboard, int passmoves, SearchNode passprevious) {
            board = passboard;
            moves = passmoves;
            previous = passprevious;
        }

    }

    private class Hammingcompare implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return  a.board.hamming() + a.moves - b.board.hamming() - b.moves;
        }
    }

    private class Manhattancompare implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return a.board.manhattan() + a.moves - b.board.manhattan() - b.moves;
        }
    }
    private Board[] solutionboards;
    private int movescount;
    private boolean solvable = false;

    public Solver(Board initial) {
        solvable = false;
        SearchNode node = new SearchNode(initial, 0, null);
        SearchNode swapnode = new SearchNode(initial.twin(), 0, null);
        SearchNode currentnode = node;
        SearchNode currentswapnode = swapnode;
        MinPQ<SearchNode> boardque = new MinPQ<>(new Manhattancompare());
        boardque.insert(node);
        MinPQ<SearchNode> swapboardque = new MinPQ<>(new Manhattancompare());
        swapboardque.insert(swapnode);

        while (!currentnode.board.isGoal() || !currentswapnode.board.isGoal()) {
            currentnode = boardque.delMin();

            if (currentnode.board.isGoal()) {
                solvable = true;
                movescount = currentnode.moves;
                int tempcount = movescount;
                SearchNode pointer = currentnode;
                solutionboards = new Board[movescount+1];
                while (tempcount>=0) {
                    solutionboards[tempcount--] = currentnode.board;
                    currentnode = currentnode.previous;
                }
                break;
            } else {
                Iterator<Board> possible = currentnode.board.neighbors().iterator();
                while (possible.hasNext()) {
                    Board tempb = possible.next();
                    if (currentnode.previous==null || !tempb.equals(currentnode.previous.board)) {boardque.insert(new SearchNode(tempb, currentnode.moves+1, currentnode));}
                }
            }

            currentswapnode = swapboardque.delMin();
            if (currentswapnode.board.isGoal()) {
                solvable = false;
                movescount = -1;
                break;
            } else {
                Iterator<Board> possible = currentswapnode.board.neighbors().iterator();
                while (possible.hasNext()) {
                    Board tempb = possible.next();
                    if (currentswapnode.previous==null || !tempb.equals(currentswapnode.previous.board)) {swapboardque.insert(new SearchNode(tempb, currentswapnode.moves + 1, currentswapnode));}
                }
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return movescount;
    }

    public Iterable<Board> solution() {
        return new ArrayList<>(Arrays.asList(solutionboards));
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

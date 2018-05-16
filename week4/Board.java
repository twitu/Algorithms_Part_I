/**
 * Created by Ishan on 15-01-2018.
 */
import java.util.ArrayList;

public class Board {
    private int[][] arrayboard;
    private int hammingscore;
    private int manhattanscore;

    public Board(int[][] blocks) {
        int number = blocks.length;
        arrayboard = new int[number][number];
        int val;
        int squarelength = number*number;
        hammingscore = 0;
        manhattanscore = 0;

        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                arrayboard[i][j] = blocks[i][j];
                if (arrayboard[i][j] != 0) {
                    val = i*number + j;
                    if (arrayboard[i][j] != (val + 1)%squarelength) {
                        hammingscore++;
                    }
                    manhattanscore += Math.abs(i - ((arrayboard[i][j] - 1)/number)) + Math.abs(j - ((arrayboard[i][j] - 1)%number));
                }
            }
        }
    }

    public int dimension() {
        return arrayboard.length;
    }

    public int hamming() {
        return hammingscore;
    }

    public int manhattan() {
        return manhattanscore;
    }

    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) { return false; }
        Board testboard = (Board) y;
        if (testboard.dimension() != this.dimension()) { return false; }
        for (int i = 0; i < arrayboard.length; i++) {
            for (int j = 0; j < arrayboard.length; j++) {
                if (arrayboard[i][j] != testboard.arrayboard[i][j]) { return false; }
            }
        }
        return true;
    }

    public boolean isGoal() {
        return hamming()==0;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension());
        str.append("\n");
        for (int i = 0; i < arrayboard.length; i++) {
            for (int j = 0; j < arrayboard.length; j++) {
                str.append(" ");
                str.append(arrayboard[i][j]);
                str.append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    private int[][] swapTile(int[][] blocks, int startx, int starty, int endx, int endy) {
        int[][] tempblocks = copyTable(blocks);
        int temp = tempblocks[endx][endy];
        tempblocks[endx][endy] = tempblocks[startx][starty];
        tempblocks[startx][starty] = temp;
        return tempblocks;
    }

    private int[][] copyTable(int[][] blocks) {
        int[][] temp = new int[blocks.length][blocks.length];
        for (int i=0; i < blocks.length; i++) {
            for (int j=0; j < blocks.length; j++) {
                temp[i][j] = blocks[i][j];
            }
        }
        return temp;
    }

    public Board twin() {
        Board temp;
        for (int i = 0; i < arrayboard.length; i++) {
            for (int j = 0; j < arrayboard.length-1; j++) {
                if (arrayboard[i][j] != 0 && arrayboard[i][j + 1] != 0) {
                    return new Board(swapTile(arrayboard, i, j, i, j + 1));
                }
            }
        }
        return null;
    }

    private void findneighbours(ArrayList<Board> list, int[][] board, int val, int i, int j) {
        if (val==1) {
            list.add(new Board(swapTile(board, i, j, i+1, j)));
            list.add(new Board(swapTile(board, i, j, i, j+1)));
        } else if (val==2) {
            list.add(new Board(swapTile(board, i, j, i+1, j)));
            list.add(new Board(swapTile(board, i, j, i, j-1)));
        } else if (val==3) {
            list.add(new Board(swapTile(board, i, j, i-1, j)));
            list.add(new Board(swapTile(board, i, j, i, j-1)));
        } else if (val==4) {
            list.add(new Board(swapTile(board, i, j, i-1, j)));
            list.add(new Board(swapTile(board, i, j, i, j+1)));
        }
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> list =  new ArrayList<Board>(4);
        int i = 0;
        int j = 0;
        for (i = 0; i < arrayboard.length; i++) {
            for (j = 0; j < arrayboard.length; j++) {
                if (arrayboard[i][j] == 0) {
                    break;
                }
            }
            if (j<arrayboard.length && arrayboard[i][j] == 0) {
                break;
            }
        }

        if (i == 0) {
            if (j == 0) {
                findneighbours(list, arrayboard, 1, i, j);
            } else if (j == arrayboard.length-1) {
                findneighbours(list, arrayboard, 2, i, j);
            } else {
                findneighbours(list, arrayboard, 1, i, j);
                list.add(new Board(swapTile(arrayboard, i, j, i, j-1)));
            }
        } else if (i == arrayboard.length -1) {
            if (j == 0) {
                findneighbours(list, arrayboard, 4, i, j);
            } else if (j == arrayboard.length - 1) {
                findneighbours(list, arrayboard, 3, i, j);
            } else {
                findneighbours(list, arrayboard, 4, i, j);
                list.add(new Board(swapTile(arrayboard, i, j, i, j-1)));
            }
        } else {
            if (j==0) {
                findneighbours(list, arrayboard, 1, i, j);
                list.add(new Board(swapTile(arrayboard, i, j, i-1, j)));
            } else if (j==arrayboard.length-1) {
                findneighbours(list, arrayboard, 2, i, j);
                list.add(new Board(swapTile(arrayboard, i, j, i-1, j)));
            } else {
                findneighbours(list, arrayboard, 1, i, j);
                findneighbours(list, arrayboard, 3, i, j);
            }

        }
        return list;
    }
}

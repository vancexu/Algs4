import java.lang.StringBuilder;
import java.lang.Math;
import java.lang.NullPointerException;
    
public class Board {
    private int[][] blocks;
    private int N;
    private int d_hamming;
    private int d_manhattan;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        
        this.N = blocks.length;
        this.blocks = new int[N][N];
        d_hamming = 0;
        d_manhattan = 0;
        boolean guard_hamming = true;
        boolean guard_manhattan = true;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                this.blocks[i][j] = blocks[i][j];
                
                // compute hamming distance
                if (i == N-1 && j == N-1) guard_hamming = false;
                if (guard_hamming) {
                    if (blocks[i][j] != i * N + j + 1)
                        d_hamming++;
                }
                
                // compute manhattan distance
                guard_manhattan = true;
                if (blocks[i][j] == 0) guard_manhattan = false;
                if (guard_manhattan) {
                    int a = (blocks[i][j] - 1) / N;
                    int b = (blocks[i][j] - 1) % N;
                    d_manhattan += Math.abs(a - i) + Math.abs(b - j);
                }
            }
        }
    }
    
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {        
        return d_hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return d_manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (i == N-1 && j == N-1) continue;
                if (blocks[i][j] != i * N + j + 1)
                    return false;
            }
        }
        return true;
    }
    
    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] newBlocks = new int[N][N];
        copyToBlocks(newBlocks);
        int row = 0;
        for (int i = 0; i < N; ++i) {
            if (blocks[0][i] == 0) {
                row = 1;
                break;
            }
        }
        newBlocks[row][0] = blocks[row][1];
        newBlocks[row][1] = blocks[row][0];
        return new Board(newBlocks);
    }
    
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y instanceof Board) {
            Board other = (Board) y;
            if (N != other.dimension()) return false;
            for (int i = 0; i < N; ++i)
                for (int j = 0; j < N; ++j)
                    if (blocks[i][j] != other.blocks[i][j])
                        return false;
        } else {
            return false;
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        int[][] copy = new int[N][N];
        copyToBlocks(copy);
        int row = 0;
        int col = 0;
        boolean found = false;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (blocks[i][j] == 0) {
                    row = i;
                    col = j;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        if (row > 0) {
            swap(copy, row, col, row - 1, col);
            q.enqueue(new Board(copy));
            swap(copy, row, col, row - 1, col);
        }
        if (row < N - 1) {
            swap(copy, row, col, row + 1, col);
            q.enqueue(new Board(copy));
            swap(copy, row, col, row + 1, col);
        }
        if (col > 0) {
            swap(copy, row, col, row, col - 1);
            q.enqueue(new Board(copy));
            swap(copy, row, col, row, col - 1);
        }
        if (col < N - 1) {
            swap(copy, row, col, row, col + 1);
            q.enqueue(new Board(copy));
            swap(copy, row, col, row, col + 1);
        }
        return q;
    }
        
    private void swap(int[][] copy, int row, int col, int newRow, int newCol) {
        int tmp = copy[row][col];
        copy[row][col] = copy[newRow][newCol];
        copy[newRow][newCol] = tmp;
    }
    
    private void copyToBlocks(int[][] copy) {
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                copy[i][j] = blocks[i][j];
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
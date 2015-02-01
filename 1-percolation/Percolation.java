public class Percolation {
    private boolean[][] grid; // grid[i][j] == 0 for blocked, 1 for open
    private WeightedQuickUnionUF uf; // for percolates()
    private WeightedQuickUnionUF ufBack; // for isFull()
    private int sz;
    private int N;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) { 
        if (N <= 0) 
            throw new java.lang.IllegalArgumentException();
        
        grid = new boolean[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                grid[i][j] = false;
            }
        }
        this.N = N;
        int NSquare = N*N;
        sz = NSquare+2;     
        uf = new WeightedQuickUnionUF(sz); // create two virtual node on top and bottom
        ufBack = new WeightedQuickUnionUF(NSquare + 1); // one virtual node on top
        for (int i = 1; i <= N; ++i) {
            uf.union(0, i);
            uf.union(sz-1, NSquare+1-i);
            ufBack.union(0, i);
        }   
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validArgs(i,j);
        
        grid[i-1][j-1] = true;
        int idx = posToIndex(i, j);
        
        if (i > 1 && isOpen(i-1, j)) {
            uf.union(posToIndex(i-1, j), idx);
            ufBack.union(posToIndex(i-1, j), idx);
        }
        if (i < N && isOpen(i+1, j)) {
            uf.union(posToIndex(i+1, j), idx);
            ufBack.union(posToIndex(i+1, j), idx);
        }
        if (j > 1 && isOpen(i, j-1)) {
            uf.union(posToIndex(i, j-1), idx);
            ufBack.union(posToIndex(i, j-1), idx);
        }
        if (j < N && isOpen(i, j+1)) {
            uf.union(posToIndex(i, j+1), idx);
            ufBack.union(posToIndex(i, j+1), idx);
        }
        
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validArgs(i,j);
        return grid[i-1][j-1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validArgs(i,j);
        return isOpen(i,j) && ufBack.connected(posToIndex(i,j), 0);
    }
    
    // does the system percolate?
    public boolean percolates() {
        if (N == 1) 
            return isOpen(1,1); // corner case N = 1
        return uf.connected(0, sz-1);
    }
    
    private int posToIndex(int i, int j) {
        return (i - 1) * N + j;
    }
    
    private void validArgs(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            throw new java.lang.IndexOutOfBoundsException();
    }
    
}
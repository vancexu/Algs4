public class Test {
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("Initial Board");
        StdOut.println(initial);
        /*
        for (Board b : initial.neighbors()) {
            StdOut.println(b.manhattan());
            StdOut.println(b);
        }*/
        
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        //StdOut.println(solver.moves());
        
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
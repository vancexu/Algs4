import java.util.Comparator;
import java.lang.NullPointerException;

public class Solver {
    private MinPQ<Node> heap;
    private MinPQ<Node> heapTwin;
    private Stack<Board> solutionStack = new Stack<Board>();
    private boolean mSolvable = true;
    
    private class Node {
        Board board;
        int steps;
        Node parent;
        
        Node (Board b, int s, Node n) {
            board = b;
            steps = s;
            parent = n;
        }
    }
    
    private class Priority implements Comparator<Node> {
        public int compare(Node a, Node b) {
            int m1 = a.board.manhattan();
            int m2 = b.board.manhattan();
            int d1 = m1 + a.steps;
            int d2 = m2 + b.steps;
            if (d1 < d2) {
                return -1;
            } else if (d1 == d2) {
                if (m1 < m2) return -1;
                if (m1 > m2) return 1;
                return 0;
            } else {
                return 1;
            }
        }
    }
    
    private void makeSolutions(Node node) {
        while (node != null) {
            solutionStack.push(node.board);
            node = node.parent;
        }
    }
    
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        
        Node node = new Node(initial, 0, null);
        Node nodeTwin = new Node(initial.twin(), 0, null);
        heap = new MinPQ<Node>(new Priority());
        heapTwin = new MinPQ<Node>(new Priority());
        heap.insert(node);
        heapTwin.insert(nodeTwin);
        while (!heap.isEmpty()) {
            
            nodeTwin = heapTwin.delMin();
            if (nodeTwin.board.isGoal()) { 
                mSolvable = false;
                return;
            }
            
            node = heap.delMin();
            if (node.board.isGoal()) {
                makeSolutions(node);
                return;
            } else {
                Node child;
                Node childTwin;
                boolean guard_child = true;  // used for optimization
                boolean guard_childTwin = true;
                for (Board b : node.board.neighbors()) {
                    if (guard_child) {
                        if (node.parent != null && b.equals(node.parent.board)) {
                            guard_child = false;
                            continue;
                        } else {
                            child = new Node(b, node.steps + 1, node);
                            heap.insert(child);
                        }
                    } else {
                        child = new Node(b, node.steps + 1, node);
                        heap.insert(child);
                    }
                }
                for (Board b : nodeTwin.board.neighbors()) {
                    if (guard_childTwin) {
                        if (nodeTwin.parent != null && b.equals(nodeTwin.parent.board)) {
                            guard_childTwin = false;
                            continue;
                        } else {
                            childTwin = new Node(b, nodeTwin.steps + 1, nodeTwin);
                            heapTwin.insert(childTwin);
                        }
                    } else {
                        childTwin = new Node(b, nodeTwin.steps + 1, nodeTwin);
                        heapTwin.insert(childTwin);
                    }
                }
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return mSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return solutionStack.size() - 1;
        else 
            return -1;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable())
            return solutionStack;
        else 
            return null;
    }
        
}
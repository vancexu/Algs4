import java.util.TreeSet;
import java.lang.NullPointerException;

public class PointSET {
    private TreeSet<Point2D> tree;
    
    public PointSET() {
        tree = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        return tree.isEmpty();
    }
    
    public int size() {
        return tree.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (contains(p)) return;
        tree.add(p);
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return tree.contains(p);
    }
    
    // draw all points to standard draw 
    public void draw() {
        for (Point2D p : tree)
            p.draw();
    }
    
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D p : tree) {
            if (rect.contains(p))
                q.enqueue(p);
        }
        return q;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        if (isEmpty()) return null;
        
        Point2D res = tree.first();
        double min = res.distanceSquaredTo(p);
        for (Point2D point : tree) {
            double dis = point.distanceSquaredTo(p);
            if (min > dis) {
                min = dis;
                res = point;
            }
        }
        return res;
    }
}
import java.lang.NullPointerException;

public class KdTree {
    private Node root;
    private int sz;
        
    private class Node {
        Point2D point;
        Node left;
        Node right;
        boolean isVertical;
        RectHV rect; // the axis-aligned rectangle corresponding to this node
        
        public Node(Point2D p) {
            point = p;
        }
    }
    
    public KdTree() {
        sz = 0;
        root = null;
    }
    
    public boolean isEmpty() {
        return sz == 0;
    }
    
    public int size() {
        return sz;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        if (contains(p)) return; // do not insert duplicate point
        
        if (isEmpty()) {
            root = new Node(p);
            root.isVertical = true;
            root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        } else {
            Node node = root;
            Node parent = null;
            boolean isLeft = true;
            double x0 = 0.0;
            double y0 = 0.0;
            double x1 = 1.0;
            double y1 = 1.0;
            while (node != null) {
                parent = node;
                if (node.isVertical) {
                    if (p.x() < node.point.x()) {                     
                        node = node.left;
                        isLeft = true;
                    } else {
                        node = node.right;
                        isLeft = false;
                    }
                } else {
                    if (p.y() < node.point.y()) {
                        node = node.left;
                        isLeft = true;
                    } else {
                        node = node.right;
                        isLeft = false;
                    }
                }
            }
            Node newNode = new Node(p);
            newNode.isVertical = !parent.isVertical;
            if (isLeft) { 
                if (parent.isVertical) {
                    newNode.rect = new RectHV(parent.rect.xmin(), 
                                              parent.rect.ymin(),
                                              parent.point.x(), 
                                              parent.rect.ymax());
                } else {
                    newNode.rect = new RectHV(parent.rect.xmin(), 
                                              parent.rect.ymin(),
                                              parent.rect.xmax(), 
                                              parent.point.y());
                }
                parent.left = newNode;
            } else {
                if (parent.isVertical) {
                    newNode.rect = new RectHV(parent.point.x(),  
                                              parent.rect.ymin(),
                                              parent.rect.xmax(), 
                                              parent.rect.ymax());
                } else {
                    newNode.rect = new RectHV(parent.rect.xmin(), 
                                              parent.point.y(),
                                              parent.rect.xmax(), 
                                              parent.rect.ymax());
                }
                parent.right = newNode;
            }
        }
        sz++;
    }
    
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        if (isEmpty()) return false;
        
        Node node = root;
        while (node != null) {
            if (node.isVertical) {
                if (p.x() < node.point.x()) {
                    node = node.left;
                } else if (p.x() > node.point.x()) {
                    node = node.right;
                } else {
                    if (p.y() == node.point.y())
                        return true;
                    else 
                        node = node.right;
                }
            } else {
                if (p.y() < node.point.y()) {
                    node = node.left;
                } else if (p.y() > node.point.y()) {
                    node = node.right;
                } else {
                    if (p.x() == node.point.x())
                        return true;
                    else 
                        node = node.right;
                }
            }
        }
        return false;
    }
    
    private void drawPoint(Point2D p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(p.x(), p.y());
    }
    
    private void drawRedLine(double x, double y0, double y1) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(x,y0,x,y1);
    }
    
    private void drawBlueLine(double y, double x0, double x1) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();
        StdDraw.line(x0,y,x1,y);
    }
    
    private void drawAux(Node n, double x0, double y0, double x1, double y1) {
        if (n.isVertical) {
            // drawRedLine(n.point.x(), y0, y1);
            drawRedLine(n.point.x(), n.rect.ymin(), n.rect.ymax());
        } else {
            // drawBlueLine(n.point.y(), x0, x1);
            drawBlueLine(n.point.y(), n.rect.xmin(), n.rect.xmax());
        }
        drawPoint(n.point);
        if (n.left != null) drawAux(n.left, x0, y0, n.point.x(), y1);
        if (n.right != null) drawAux(n.right, n.point.x(), y0, x1, y1);
    }
    
    public void draw() {
        if (isEmpty()) return;
        
        drawAux(root, 0.0, 0.0, 1.0, 1.0);
    }
    
    private void rangeAux(Node n, RectHV rect, Queue<Point2D> queue) {
        if (n == null) return;
        if (n.rect.intersects(rect)) {
            if (rect.contains(n.point))
                queue.enqueue(n.point);
            if (n.left != null) rangeAux(n.left, rect, queue);
            if (n.right != null) rangeAux(n.right, rect, queue);
        } else {
            return;
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        
        Queue<Point2D> queue = new Queue<Point2D>();
        rangeAux(root, rect, queue);
        return queue;
    }
    
    private boolean onSameSide(Point2D p1, Point2D p2, Node parent) {
        if (parent.isVertical) {
            double x = parent.point.x();
            return (p1.x() - x) * (p2.x() - x) >= 0;
        } else {
            double y = parent.point.y();
            return (p1.y() - y) * (p2.y() - y) >= 0;
        }
    }
    
    private Point2D nearestAux(Node n, Point2D p, Point2D minPoint, double minDis) {
        if (n == null) return minPoint;
        Point2D res = minPoint;
        double dis = n.point.distanceSquaredTo(p);
        if (dis < minDis) {
            minDis = dis;
            res = n.point;
        }
        if (n.left != null && n.right != null) {
            if (onSameSide(n.left.point, p, n)) {
                res = nearestAux(n.left, p, res, minDis);
                minDis = res.distanceSquaredTo(p);
                if (n.right.rect.distanceSquaredTo(p) < minDis) 
                    res = nearestAux(n.right, p, res, minDis);
            } else {
                res = nearestAux(n.right, p, res, minDis);
                minDis = res.distanceSquaredTo(p);
                if (n.left.rect.distanceSquaredTo(p) < minDis)
                    res = nearestAux(n.left, p, res, minDis);
            }
        } else if (n.left == null) {
            res = nearestAux(n.right, p, res, minDis);
        } else if (n.right == null) {
            res = nearestAux(n.left, p, res, minDis);
        }
        return res;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        return nearestAux(root, p, null, 2.0);
    }
}
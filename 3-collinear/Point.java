import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    private class SlopOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            Point tmp = new Point(x,y);
            double slopeA = tmp.slopeTo(a);
            double slopeB = tmp.slopeTo(b);
            if (slopeA < slopeB) return -1;
            else if (slopeA == slopeB) return 0;
            else return 1;
        }
    }
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (x == that.x && y == that.y) return Double.NEGATIVE_INFINITY;
        if (y == that.y) return 0.0;
        if (x == that.x) return Double.POSITIVE_INFINITY;
        return (y - that.y) * 1.0 / (x - that.x);       
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (y < that.y) return -1;
        if (y == that.y && x < that.x) return -1;
        if (y == that.y && x == that.x) return 0;
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
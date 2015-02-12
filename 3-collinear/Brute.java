import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int lines = in.readInt();
        if (lines < 4) return;
        
        Point[] points = new Point[lines];
        for (int i = 0; i < lines; ++i) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        Arrays.sort(points);
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768); 
        for (Point p : points) {
            p.draw();
        }
        
        for (int i = 0; i < points.length - 3; ++i) {
            for (int j = i + 1; j < points.length - 2; ++j) {
                double slop1 = points[i].slopeTo(points[j]);
                boolean drawLine = false;
                for (int k = j + 1; k < points.length - 1; ++k) {
                    double slop2 = points[j].slopeTo(points[k]);
                    if (slop1 != slop2) continue;
                    for (int l = k + 1; l < points.length; ++l) {
                        double slop3 = points[k].slopeTo(points[l]);
                        if (slop1 != slop3) {
                            continue;                         
                        } else {
                            printLine(points,i,j,k,l);
                            points[i].drawTo(points[l]);
                        }
                    }
                }
            }
        }
    }
    
    private static void printLine(Point[] points, int i, int j, int k, int l) {
        System.out.print(points[i] + " -> " +
                         points[j] + " -> " + 
                         points[k] + " -> " + 
                         points[l] + "\n");
    }
}
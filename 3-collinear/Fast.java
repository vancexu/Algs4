import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int lines = in.readInt();
        if (lines < 4) return;
        
        Point[] points = new Point[lines];
        for (int i = 0; i < lines; ++i) 
            points[i] = new Point(in.readInt(), in.readInt());
        Arrays.sort(points);
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768); 
        for (Point p : points) {
            p.draw();
        }
        
        for (int i = 0; i < points.length; ++i) {
            Point[] others = new Point[points.length - 1];
            for (int j = 0, k = 0; j < others.length; ++j, ++k) {
                if (k == i) {
                    j--;
                    continue;
                }
                others[j] = points[k];
            }
            Arrays.sort(others, points[i].SLOPE_ORDER);
            
            int j = 1;
            while (j < others.length) {
                int len = 1;
                // deal with subsegments: 
                // draw the segment only if current point is lower than all the others collinear points
                boolean needDraw = points[i].compareTo(others[j-1]) < 0;
                while (j < others.length &&
                       points[i].slopeTo(others[j-1]) == points[i].slopeTo(others[j])) {
                    if (points[i].compareTo(others[j-1]) >= 0) 
                        needDraw = false;
                    j++;
                    len++;
                }
                if (len >= 3 && needDraw) {
                    System.out.print(points[i]);
                    int idx = -1;
                    for (int t = 0; t < len; ++t) {
                        idx = j - len + t;
                        System.out.print(" -> " + others[idx]);
                    }
                    points[i].drawTo(others[idx]);
                    System.out.println("");
                }
                j++;
            }
            
        }           
    }
}
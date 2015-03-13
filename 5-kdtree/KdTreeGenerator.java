/*************************************************************************
 *  Compilation:  javac KdTreeGenerator.java
 *  Execution:    java KdTreeGenerator N
 *  Dependencies: 
 *
 *  Creates N random points in the unit square and print to standard output.
 *
 *  % java KdTreeGenerator 5
 *  0.195080 0.938777
 *  0.351415 0.017802
 *  0.556719 0.841373
 *  0.183384 0.636701
 *  0.649952 0.237188
 *
 *************************************************************************/

public class KdTreeGenerator {

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        for (int i = 0; i < N; i++) {
            double x = Math.random();
            double y = Math.random();
            System.out.printf("%8.6f %8.6f\n", x, y);
        }
    }
}

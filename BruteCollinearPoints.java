import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Examines 4 points at a time and checks whether
 * they all lie on the same line segment,
 * returning all such line segments.
 * Uses Brute Force : n^4
 */
public class BruteCollinearPoints {


    private LineSegment[] segments;

    /**
     *
     * To check whether the 4 points p, q, r, and s are collinear
     * or not by comparing their slopes
     * @param points Array containing all the points.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null || nullPointInArray(points)
                || repeatedPointInArray(points)) {
            throw new java.lang.IllegalArgumentException("Bad Input");
        }

        ArrayList<LineSegment> listOfLineSegments = new ArrayList<>();

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double firstSlope = points[i].slopeTo(points[j]);
                        if (firstSlope != points[i].slopeTo(points[k])) {
                            break;
                        } else {
                            if (firstSlope == points[i].slopeTo(points[l])) {
                                Point[] temp = new Point[4];
                                temp[0] = points[i];
                                temp[1] = points[j];
                                temp[2] = points[k];
                                temp[3] = points[l];
                                Arrays.sort(temp);
                                listOfLineSegments.add(new LineSegment(temp[0], temp[3]));
                            }

                        }
                    }
                }
            }
        }
        this.segments = listOfLineSegments.toArray(new LineSegment[0]);
    }

    /**
     * Private method to check weather any point in the
     * array provided by the constructor has been repeated
     *
     * @param points Array of points provided by the constructor
     * @return <em>true</em> if any point has been repeated
     */
    private static boolean repeatedPointInArray(final Point[] points) {
        for (int i = 0; i < points.length-1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Private method to check whether any point in the
     * array in the constructor is null
     *
     * @param points Array of points provided by the constructor
     * @return <em>true</em> if any point is null
     */
    private static boolean nullPointInArray(final Point[] points) {
        for (Point p : points) {
            if (p == null) return true;
        }
        return false;
    }

    /**
     *
     * @return The number of line segments found
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     *
     * @return Array of line segments found
     */
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

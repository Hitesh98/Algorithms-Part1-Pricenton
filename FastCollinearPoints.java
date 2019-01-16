import java.util.ArrayList;
import java.util.Arrays;

/**
 * Examines 4 points at a time and checks whether
 * they all lie on the same line segment,
 * returning all such line segments.
 * Uses optimized solution : n^2
 */
public class FastCollinearPoints {

    /**
     * Private variable to temporarily store the line segments.
     */
    private LineSegment[] segments;

    /**
     * To check whether 4 or more points are collinear
     * or not by comparing their slopes.
     * @param points Array containing all the points.
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null || nullPointInArray(points)
                || repeatedPointInArray(points)) {
            throw new IllegalArgumentException("Bad Input");
        }

        ArrayList<LineSegment> listOfLineSegments = new ArrayList<>();
        Point[] copyOfPoints = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Point referencePoint = points[i];
            Arrays.sort(copyOfPoints, referencePoint.slopeOrder());4
            double previousSlope = referencePoint.slopeTo(copyOfPoints[1]);
            double currentSlope;
            for (int j = 1, k = 2; j < points.length; j = k, k = j + 1) {
                currentSlope = referencePoint.slopeTo(copyOfPoints[k]);
                while (k < points.length && currentSlope == previousSlope) {
                    k++;
                }
                if (k - j >= 3) {
                    addSegment(listOfLineSegments, findSegment(copyOfPoints, j, k));
                }
            }
        }
        this.segments = listOfLineSegments.toArray(new LineSegment[0]);
    }

    /**
     * Method to find maximum and minimum point
     * from an Array of points and the first index point.
     *
     * @return A line segment from the min to
     * max point in the specfied range.
     */
    private LineSegment findSegment(Point[] points, int start, int end) {
        Point maxPoint = points[0];
        Point minPoint = points[0];
        for (int i = start; i < end -1; i++) {
            int k = maxPoint.compareTo(points[i]);
            int m = minPoint.compareTo(points[i]);
            if (k < 0) {
                maxPoint = points[i];
            }
            if (m > 0) {
                minPoint = points[i];
            }
        }
        return new LineSegment(minPoint, maxPoint);
    }

    private void addSegment(ArrayList<LineSegment> listOfLineSegments, LineSegment toAdd) {
        String newString = toAdd.toString();
        int n =  listOfLineSegments.size();
        for (int i = 0;i < n; i++) {
            if (listOfLineSegments.get(i).toString().equals(newString)) {
                return;
            }
        }
        listOfLineSegments.add(toAdd);
    }


    /**
     *
     * @return The number of Line Segments found
     */
    public int numberOfSegments() {
        return 0;
    }

    /**
     *
     * @return Array of Line Segments found
     */
    public LineSegment[] segments() {
        return null;
    }

    /**
     * Private method to check weather any point in the
     * array provided by the constructor has been repeated.
     *
     * @param points Array of points provided by the constructor
     * @return <em>true</em> if any point has been repeated
     */
    private static boolean repeatedPointInArray(final Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Private method to check whether any point in the
     * array in the constructor is null.
     *
     * @param points Array of points provided by the constructor
     * @return <em>true</em> if any point is null
     */
    private static boolean nullPointInArray(final Point[] points) {
        for (Point p : points) {
            if (p == null) {
                return true;
            }
        }
        return false;
    }
}
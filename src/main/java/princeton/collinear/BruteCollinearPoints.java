package princeton.collinear;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("The argument is null");
        ifContainsNull(points);
        ifRepeatedPoint(points);

        segments = new LinkedList<LineSegment>();

        Point[] copyArray = new Point[points.length];

        System.arraycopy(points, 0, copyArray, 0, points.length);

        Arrays.sort(copyArray);
        //	System.out.println(Arrays.toString(copyArray));

        for (int i = 0; i < copyArray.length - 3; i++) // n to 4 order of growth
            for (int j = i + 1; j < copyArray.length - 2; j++)
                for (int k = j + 1; k < copyArray.length - 1; k++)
                    for (int h = k + 1; h < copyArray.length; h++) {

                        if (copyArray[i].slopeTo(copyArray[j]) == copyArray[i].slopeTo(copyArray[k]) &&
                                copyArray[i].slopeTo(copyArray[k]) == copyArray[i].slopeTo(copyArray[h])) {
                            segments.add(new LineSegment(copyArray[i], copyArray[h]));

                        }

                    }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    private void ifContainsNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("The array contains null item");
        }
    }

    private void ifRepeatedPoint(Point[] points) {
        for (int i = 0; i < points.length - 1; i++)
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("Repeated point in the array");
                }

            }

    }
}

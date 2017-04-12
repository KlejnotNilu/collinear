package princeton.collinear;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("The argument is null");
        ifContainsNull(points);

        Point[] arrayCopy = points.clone();

        Arrays.sort(arrayCopy);

        ifRepeatedPoint(arrayCopy);

        segments = new LinkedList<LineSegment>();

        for (Point tmp : arrayCopy) {

            int counter = 2; // number of points in one line

            Point[] arrayBySlope = arrayCopy.clone();

            Arrays.sort(arrayBySlope, tmp.slopeOrder());

            Point theSecondOne = arrayBySlope[0];    // corner case when array has 1 item inside
            if (points.length > 1) {
                theSecondOne = arrayBySlope[1];
            }

            for (int i = 2; i < arrayBySlope.length; i++) {

                if (arrayBySlope[0].slopeTo(arrayBySlope[i - 1]) == arrayBySlope[0].slopeTo(arrayBySlope[i])) {
                    counter++;
                } else {
                    if (counter >= 4 && arrayBySlope[0].compareTo(theSecondOne) < 0) {
                        segments.add(new LineSegment(arrayBySlope[0], arrayBySlope[i - 1]));
                    }

                    theSecondOne = arrayBySlope[i];
                    counter = 2;

                }

                if (i == arrayBySlope.length - 1 && counter >= 4
                        && arrayBySlope[0].compareTo(theSecondOne) < 0) {
                    segments.add(new LineSegment(arrayBySlope[0], arrayBySlope[i]));
                    counter = 2;
                }

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
            if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("Repeated point in the array");


    }


}


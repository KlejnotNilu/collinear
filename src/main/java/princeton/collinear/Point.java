package princeton.collinear;

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) //the same point
            return Double.NEGATIVE_INFINITY;
        if (this.y == that.y) // horizontal line
            return +0;
        if (this.x == that.x) // vertical line
            return Double.POSITIVE_INFINITY;

        return (double) (that.y - this.y) / (that.x - this.x); // 1 * (that.x - this.x) spierdala wynik, dlaczego?

    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) // less
            return -1;
        if (this.y == that.y) {
            if (this.x < that.x) //less
                return -1;
            else if (this.x == that.x) // equal
                return 0;
        }
        return 1; //greater
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new ComparatorSlope();
    }

    private class ComparatorSlope implements Comparator<Point> {

        public int compare(Point arg0, Point arg1) {
            return Double.compare(slopeTo(arg0), slopeTo(arg1));
        }

    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /*
            PROVIDE THE NAME OF FILE IN RESOURCES FOLDER AS A COMMAND-LINE ARGUMENT TO RUN THIS PROGRAM
         */

        String nameOfFile = args[0];
        if(nameOfFile.charAt(0) != '/')
            nameOfFile = "/" + nameOfFile;

        // read the n points from a file
        In in = new In(Point.class.getResource(nameOfFile).getFile());
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        // StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points); // you can change this object to BruteCollinearPoints to test O(n to the power of 4) time complexity
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    /*	Point first = new Point(3000, 4000);

    	Point second = new Point(20000, 21000);

    	System.out.println(first.slopeTo(second)); */
    }
}
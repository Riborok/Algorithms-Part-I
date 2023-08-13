import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            return Double.compare(slopeTo(p1), slopeTo(p2));
        }
    }

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (y > that.y || (y == that.y && x > that.x))
            return 1;

        if (y < that.y || x < that.x)
            return -1;

        return 0;
    }

    public double slopeTo(Point that) {
        if (x != that.x) {
            if (y != that.y)
                return (double) (that.y - y) / (that.x - x);

            return 0;
        }

        if (y != that.y)
            return Double.POSITIVE_INFINITY;

        return Double.NEGATIVE_INFINITY;
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }
}
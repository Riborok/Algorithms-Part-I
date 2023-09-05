package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.LinkedList;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points)
            point.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Rect cannot be null");

        LinkedList<Point2D> pointsInRect = new LinkedList<Point2D>();
        for (Point2D point : points)
            if (rect.contains(point))
                pointsInRect.add(point);

        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        if (isEmpty())
            return null;

        Point2D nearestPoint = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double distance = p.distanceTo(point);
            if (distance < nearestDistance) {
                nearestPoint = point;
                nearestDistance = distance;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}
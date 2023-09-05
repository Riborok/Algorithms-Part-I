package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;

public class KdTree {
    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left = null;
        private Node right = null;

        private Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    private Node root = null;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");

        if (root != null) {
            if (!contains(p))
                insert(root, p, true);
        }
        else {
            root = new Node(p, new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                                          Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
            size = 1;
        }
    }

    private void insert(Node node, Point2D p, boolean isX) {
        int cmp = isX
                  ? Double.compare(p.x(), node.point.x())
                  : Double.compare(p.y(), node.point.y());

        if (cmp < 0) {
            if (node.left != null)
                insert(node.left, p, !isX);
            else {
                RectHV rect = node.rect;
                RectHV newRect = isX
                                 ? new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax())
                                 :
                                 new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                node.left = new Node(p, newRect);
                size++;
            }
        }
        else {
            if (node.right != null)
                insert(node.right, p, !isX);
            else {
                RectHV rect = node.rect;
                RectHV newRect = isX
                                 ? new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax())
                                 :
                                 new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
                node.right = new Node(p, newRect);
                size++;
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");

        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean isX) {
        if (node == null)
            return false;

        if (node.point.equals(p))
            return true;

        int cmp = isX
                  ? Double.compare(p.x(), node.point.x())
                  : Double.compare(p.y(), node.point.y());

        if (cmp < 0)
            return contains(node.left, p, !isX);
        else
            return contains(node.right, p, !isX);
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null)
            return;

        node.point.draw();

        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Rect cannot be null");

        LinkedList<Point2D> pointsInRect = new LinkedList<>();
        if (root != null)
            range(root, rect, pointsInRect);
        return pointsInRect;
    }

    private void range(Node node, RectHV rect, LinkedList<Point2D> pointsInRange) {
        if (rect.contains(node.point))
            pointsInRange.add(node.point);

        if (node.left != null && rect.intersects(node.left.rect))
            range(node.left, rect, pointsInRange);

        if (node.right != null && rect.intersects(node.right.rect))
            range(node.right, rect, pointsInRange);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        if (isEmpty())
            return null;

        return nearest(p, root, root.point, true);
    }

    private Point2D nearest(Point2D p, Node node, Point2D nearestPoint, boolean isX) {
        if (node == null)
            return nearestPoint;

        double nearestDistance = nearestPoint.distanceSquaredTo(p);
        if (node.rect.distanceSquaredTo(p) >= nearestDistance)
            return nearestPoint;

        if (node.point.distanceSquaredTo(p) < nearestDistance)
            nearestPoint = node.point;

        int cmp = isX
                  ? Double.compare(p.x(), node.point.x())
                  : Double.compare(p.y(), node.point.y());

        if (cmp < 0) {
            nearestPoint = nearest(p, node.left, nearestPoint, !isX);
            nearestPoint = nearest(p, node.right, nearestPoint, !isX);
        }
        else {
            nearestPoint = nearest(p, node.right, nearestPoint, !isX);
            nearestPoint = nearest(p, node.left, nearestPoint, !isX);
        }

        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}

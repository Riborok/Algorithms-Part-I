package collinear;

import java.util.Arrays;

public class FastCollinearPoints {
    private class ArrayList {
        private static final int MIN_LENGTH = 4;
        private int size;
        private LineSegment[] array;

        public ArrayList() {
            array = new LineSegment[MIN_LENGTH];
            size = 0;
        }

        private void resize(int length) {
            LineSegment[] oldArray = array;
            array = new LineSegment[length];
            for (int i = 0; i < size; i++)
                array[i] = oldArray[i];
        }

        public int getSize() {
            return size;
        }

        public LineSegment[] getArray() {
            return array;
        }

        public void add(LineSegment element) {
            if (size == array.length)
                resize(array.length << 1);
            array[size++] = element;
        }
    }

    private final ArrayList segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Points cannot be null");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Array of points contains null");

            for (int j = 0; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("Array of points contains null");

                if (i != j && points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Array of points has identical points");
            }
        }

        segments = new ArrayList();
        Point[] copyPoints = points.clone();
        Arrays.sort(copyPoints);

        Point[] pointsBySlope = copyPoints.clone();
        for (Point point : copyPoints) {
            Arrays.sort(pointsBySlope, point.slopeOrder());

            int count = 1;
            for (int j = 1; j < copyPoints.length; j++) {
                if (point.slopeTo(pointsBySlope[j - 1]) != point.slopeTo(pointsBySlope[j])) {
                    if (count >= 3)
                        tryAddSegment(point, pointsBySlope, j - count, j - 1);

                    count = 1;
                }
                else
                    count++;
            }

            if (count >= 3)
                tryAddSegment(point, pointsBySlope, copyPoints.length - count,
                              copyPoints.length - 1);
        }
    }

    private void tryAddSegment(Point point, Point[] points, int left, int right) {
        Arrays.sort(points, left, right + 1);
        if (point.compareTo(points[left]) < 0)
            segments.add(new LineSegment(point, points[right]));
    }

    public int numberOfSegments() {
        return segments.getSize();
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments.getArray(), segments.getSize());
    }
}

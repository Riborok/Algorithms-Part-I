import java.util.Arrays;

public class BruteCollinearPoints {
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

    public BruteCollinearPoints(Point[] points) {
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

        Point[] copyPoints = points.clone();
        Arrays.sort(copyPoints);

        segments = new ArrayList();

        for (int i = 0; i < copyPoints.length - 3; i++)
            for (int j = i + 1; j < copyPoints.length - 2; j++)
                for (int k = j + 1; k < copyPoints.length - 1; k++)
                    for (int m = k + 1; m < copyPoints.length; m++) {
                        double slopeIJ = copyPoints[i].slopeTo(copyPoints[j]);
                        double slopeIK = copyPoints[i].slopeTo(copyPoints[k]);
                        double slopeIM = copyPoints[i].slopeTo(copyPoints[m]);

                        if (slopeIJ == slopeIK && slopeIJ == slopeIM)
                            segments.add(new LineSegment(copyPoints[i], copyPoints[m]));
                    }
    }

    public int numberOfSegments() {
        return segments.getSize();
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments.getArray(), segments.getSize());
    }
}

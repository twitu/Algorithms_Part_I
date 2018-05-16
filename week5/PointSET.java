import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        Iterator<Point2D> itrpoints = points.iterator();
        while (itrpoints.hasNext()) {
            itrpoints.next().draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> innerpoints = new ArrayList<>();
        Iterator<Point2D> itrpoints = points.iterator();
        while (itrpoints.hasNext()) {
            Point2D point = itrpoints.next();
            if (rect.contains(point)) { innerpoints.add(point); }
        }
        return innerpoints;
    }

    public Point2D nearest(Point2D p) {
        Double mindist = Double.POSITIVE_INFINITY;
        Point2D reqpoint = null;
        double dist = 0;
        Iterator<Point2D> itrpoints = points.iterator();
        while (itrpoints.hasNext()) {
            Point2D point = itrpoints.next();
            dist = point.distanceSquaredTo(p);
            if (dist < mindist) {
                mindist = dist;
                reqpoint = point;
            }
        }
        return reqpoint;
    }
}

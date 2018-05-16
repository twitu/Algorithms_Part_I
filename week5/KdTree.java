import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;

public class KdTree {
    private static final boolean VERT = true;
    private static final boolean HORI = false;
    private Node root;

    private class NearestPoint {
        private Node node;
        private double distance;

        private void setNode(Node node) {
            this.node = node;
        }

        private void setDistance(double d) {
            this.distance = d;
        }
    }

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private boolean orient;
        private int size;

        private Node() {
            this.point = null;
            this.left = null;
            this.right = null;
            this.orient = false;
            this.size = 1;
        }

        private Node(Point2D p, boolean orient) {
            this.point = p;
            this.left = null;
            this.right = null;
            this.orient = orient;
            this.size = 1;
        }

        private Node put(Node node, Point2D p, boolean orient) {
            if (node==null) { return new Node(p, orient); }
            if (node.point.compareTo(p)==0) { return node; }

            if (node.orient) {
                if (p.x() < node.point.x()) {
                    node.left = put(node.left, p, !node.orient);
                } else {
                    node.right = put(node.right, p, !node.orient);
                }
            } else {
                if (p.y() < node.point.y()) {
                    node.left = put(node.left, p, !node.orient);
                } else {
                    node.right = put(node.right, p, !node.orient);
                }
            }

            node.size = 1;
            if (node.right!=null) {
                node.size += node.right.size;
            }
            if (node.left!=null) {
                node.size += node.left.size;
            }
            return node;
        }

        private boolean check(Node node, Point2D p) {
            if (node==null) { return false; }
            if (node.point.compareTo(p)==0) { return true;}
            if (node.orient) {
                if (p.x() < node.point.x()) {
                    return check(node.left, p);
                } else {
                    return check(node.right, p);
                }
            } else {
                if (p.y() < node.point.y()) {
                    return check(node.left, p);
                } else {
                    return check(node.right, p);
                }
            }
        }

        private void draw(Node node) {
            if (node==null) { return; }
            node.point.draw();
            draw(node.left);
            draw(node.right);
        }

        private boolean sameRectangle(Point2D p1, Point2D p2) {
            if (this.orient) {
                return ((p1.x() - this.point.x()) * (p2.x() - this.point.x()) >= 0);
            } else {
                return ((p1.y() - this.point.y()) * (p2.y() - this.point.y()) >= 0);
            }
        }

        private void checkRange(ArrayList<Point2D> innerpoints,RectHV rect, Node node) {
            if (node==null) { return; }
            if (rect.contains(node.point)) { innerpoints.add(node.point); }
            if (node.orient) {
                if (rect.xmax() >= node.point.x()) { checkRange(innerpoints, rect, node.right); }
                if (rect.xmin() < node.point.x()) { checkRange(innerpoints, rect, node.left); }
            } else {
                if (rect.ymax() >= node.point.y()) { checkRange(innerpoints, rect, node.right); }
                if (rect.ymin() <  node.point.y()) { checkRange(innerpoints, rect, node.left); }
            }
        }

        private void nearestPoint(Node node, Point2D p, Double min, NearestPoint nearest) {
            if (node==null) { return; }
            double distance = node.point.distanceSquaredTo(p);
            if (distance < min) {
                nearest.setNode(node);
                nearest.setDistance(distance);
            }
            if (node.orient) {
                if (p.x() < node.point.x()) {
                    nearestPoint(node.left, p, nearest.distance, nearest);
                    if (!nearest.node.sameRectangle(node.point, p)) nearestPoint(node.right, p, nearest.distance, nearest);
                } else {
                    nearestPoint(node.right, p, nearest.distance, nearest);
                    if (!nearest.node.sameRectangle(node.point, p)) nearestPoint(node.left, p, nearest.distance, nearest);
                }
            } else {
                if (p.y() < node.point.y()) {
                    nearestPoint(node.left, p, nearest.distance, nearest);
                    if (!nearest.node.sameRectangle(node.point, p)) nearestPoint(node.right, p, nearest.distance, nearest);
                } else {
                    nearestPoint(node.right, p, nearest.distance, nearest);
                    if (!nearest.node.sameRectangle(node.point, p)) nearestPoint(node.left, p, nearest.distance, nearest);
                }
            }
        }
    }

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return (root==null);
    }

    public int size() {
        if (root==null) {
            return 0;
        } else {
            return root.size;
        }
    }

    public void insert(Point2D p) {
        if (root==null) {
            root = new Node(p, VERT);
            return;
        }
        root = root.put(root, p, VERT);
    }

    public boolean contains(Point2D p) {
        if (root==null) { return false; }
        return root.check(root, p);
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> innerpoints = new ArrayList<>();
        if (size()==0) { return innerpoints; }
        root.checkRange(innerpoints, rect, root);
        return innerpoints;
    }

    public Point2D nearest(Point2D p) {
        if (root==null) { return null; }
        NearestPoint nearest = new NearestPoint();
        root.nearestPoint(root, p, Double.POSITIVE_INFINITY, nearest);
        return nearest.node.point;
    }

    public void draw() {
        root.draw(root);
    }

    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        Out out = new Out();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        out.println(kdtree.nearest(new Point2D(0.696, 0.878)));
    }
}

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] points;
	private ArrayList<LineSegment> linesegments;

	public FastCollinearPoints(Point[] pointsin) {
		if (pointsin == null) { throw new IllegalArgumentException("Null arguments are not allowed."); }
		this.points = new Point[pointsin.length];
		for (int i = 0; i < pointsin.length; i++) {
			if (pointsin[i]==null) { throw new IllegalArgumentException("Null points not allowed."); }
			this.points[i] = pointsin[i];
		}

		Arrays.sort(this.points);
		for (int i=1; i < this.points.length; i++) {
			if (this.points[i-1].compareTo(this.points[i]) == 0) {
				throw new IllegalArgumentException("Repeated points are not allowed.");
			}
		}

		linesegments = new ArrayList<LineSegment>();
		for (int i = 0; i < points.length; i++) {
			Arrays.sort(points, i, points.length, points[i].slopeOrder());
			for (int j = i; j < points.length-2; j++){
				if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j+2])){
					int k = j + 2;
					double slope = points[i].slopeTo(points[j]);
					while (k < points.length && points[i].slopeTo(points[k++]) == slope) {;}
					linesegments.add(new LineSegment(points[i], points[k-1]));
				}
			}
		}
	}

	public int numberOfSegments() {
		return linesegments.size();
	}

	public LineSegment[] segments() {
		return linesegments.toArray(new LineSegment[linesegments.size()]);
	}
}
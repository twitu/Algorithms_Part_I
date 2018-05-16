import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private Point[] points;
	private ArrayList<LineSegment> linesegments;

	public BruteCollinearPoints(Point[] pointsin) {
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
		for (int i=0; i < this.points.length-3; i++) {
			for (int j=i+1; j < this.points.length-2; j++) {
				for (int k=j+1; k < this.points.length-1; k++) {
					for (int l=k+1; l < this.points.length; l++) {
						if (points[i].slopeTo(points[j])==points[i].slopeTo(points[k]) && points[i].slopeTo(points[k])==points[i].slopeTo(points[l])) {
							linesegments.add(new LineSegment(points[i], points[l]));
						}
					}
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
import java.io.*;
import java.util.*;
import java.awt.*;

@SuppressWarnings("serial")
class RanLineObj implements Serializable {

	Vector points = new Vector();
	Color color = null;
	int x0, y0, x1, y1;
	public static int type = 8;

	public RanLineObj(Color c) {
		color = c;
	}

	public void setPoints(int x, int y) {
		Point point = new Point(x, y);
		points.add(point);
	}

	public void paint(Graphics g) {
		g.setColor(color);
		Enumeration allpoints = points.elements();
		Point start = (Point) allpoints.nextElement();
		x0 = (int) start.getX();
		y0 = (int) start.getY();
		while (allpoints.hasMoreElements()) {
			Point end = (Point) allpoints.nextElement();
			x1 = (int) end.getX();
			y1 = (int) end.getY();
			g.drawLine(x0, y0, x1, y1);
			x0 = x1;
			y0 = y1;
		}
	}

}
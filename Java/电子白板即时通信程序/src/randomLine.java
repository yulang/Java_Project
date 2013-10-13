import java.io.*;
import java.util.*;
import java.awt.*;

@SuppressWarnings("serial")
class randomLine implements Serializable {

	Vector points = new Vector();
	Color color = null;
	int x0, y0, x1, y1;
	Point point, start, end;

	public randomLine(Color c) {
		this.color = c;
	}

	public void setPoints(int x, int y) {
		point = new Point(x, y);
		this.points.add(point);
	}

	public void paint(Graphics g) {
		g.setColor(color);
		Enumeration allPoints = points.elements();
		start = (Point) allPoints.nextElement();
		x0 = (int) start.getX();
		y0 = (int) start.getY();
		while (allPoints.hasMoreElements()) {
			end = (Point) allPoints.nextElement();
			x1 = (int) end.getX();
			y1 = (int) end.getY();
			g.drawLine(x0, y0, x1, y1);
			x0 = x1;
			y0 = y1;
		}
	}

}
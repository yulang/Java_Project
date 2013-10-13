import java.awt.*;

interface Element extends java.io.Serializable {

	public void setPara(int x, int y, int z, int r, Color c);

	public void paint(Graphics g);

	public Rectangle getBounds();

	public int getType();

	public Color getColor();

}

@SuppressWarnings("serial")
class RoundRecObj implements Element {

	public int x1, y1, width, height;
	public int xold, yold, wold, hold;
	public Rectangle bounds = null;
	public Color color;
	private static int type = 3;

	public RoundRecObj() {
	};

	public void setPara(int x, int y, int w, int h, Color c) {
		x1 = x;
		y1 = y;
		width = w;
		height = h;
		color = c;
		bounds = new Rectangle(x, y, w, h);
	}

	public int getType() {
		return type;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.drawRoundRect(x1, y1, width, height, 20, 20);
	}

	public Color getColor() {
		return color;
	}

}

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
class WhiteBoardPanel extends JPanel{
  public int x0, y0, x1, y1, width, height, topx, topy;
  public static Color color = Color.black;
  public int type;
  boolean isSelected = false;
  public static Object selectedIndex = null;
  public Element element, selected;
  public static PaintedObjects elements = null;
  public static Vector ranLineVector = null;
  public static RanLineObj ranLineObj = null;
  public static ObjectOutputStream outputStream;
  public WhiteBoardPanel() {}
  public WhiteBoardPanel(Socket socket) {
		init(socket);
	}
  public void init(Socket socket)
  {
	  try {
			this.addMouseListener(new WBMouseListener());
			this.addMouseMotionListener(new WBMouseMotionListener());
			outputStream = new ObjectOutputStream(new BufferedOutputStream(socket
					.getOutputStream()));
			outputStream.flush();
			WhiteBoardThread wbThread = new WhiteBoardThread(socket, this);
			wbThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
  }

	public void setType(int i) {
		type = i;
	}

	class WBMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			x0 = e.getX();
			y0 = e.getY();
//			switch (type) {
//			case 0:
//				Element oselected = selected;
//				selected = null;
//				Enumeration els = elements.elements();
//				while (els.hasMoreElements()) {
//					Element el = (Element) els.nextElement();
//					if ((el != null) && el.getBounds().contains(x0, y0)	&& el.getType() != 7) {
//						selected = el;
//						Object selectedIndex = elements.getObjectId(selected);
//						System.out.println("selected number is "
//								+ selectedIndex);
//					}
//				}
//				if (selected != oselected) {
//					switch (selected.getType()) {
//					case 1:
//						element = new RecObj();
//						break;
//					case 2:
//						element = new OvalObj();
//						break;
//					case 3:
//						element = new RoundRecObj();
//						break;
//					case 4:
//						element = new FillRecObj();
//						break;
//					case 5:
//						element = new FillOvalObj();
//						break;
//					case 6:
//						element = new FillRoundRecObj();
//						break;
//					case 7:
//						break;
//					}
//					if (element != null) {
//						element.setPara((int) selected.getBounds().getX() - 1,
//								(int) selected.getBounds().getY() - 1,
//								(int) selected.getBounds().getWidth() - 2,
//								(int) selected.getBounds().getHeight() - 2,
//								Color.red);
//						element.paint(getGraphics());
//					}
//				}
//				break;
//			case 1:
//				element = new RecObj();
//				break;
//			case 2:
//				element = new OvalObj();
//				break;
//			case 3:
//				element = new RoundRecObj();
//				break;
//			case 4:
//				element = new FillRecObj();
//				break;
//			case 5:
//				element = new FillOvalObj();
//				break;
//			case 6:
//				element = new FillRoundRecObj();
//				break;
//			case 7:
//				element = new LineObj();
//				break;
//			case 8:
//				ranLineObj = new RanLineObj(color);
//				ranLineObj.setPoints(x0, y0);
//				break;
//			}
		}

		public synchronized void mouseReleased(MouseEvent e) {
			x1 = e.getX();
			y1 = e.getY();
			switch (type) {
			case 0:
				if (selected != null && selected.getType() != 7) {
					System.out.println("type is " + type);
					Rectangle r = new Rectangle(selected.getBounds());
					element.setPara((int) (r.getBounds().getX() + (x1 - x0)),
							(int) (r.getBounds().getY() + (y1 - y0)), (int) r
									.getBounds().getWidth(), (int) r
									.getBounds().getHeight(), selected
									.getColor());
					synchronized (elements) {
						selectedIndex = elements.getObjectId(selected);
						System.out.println("First selectedIndex is "
								+ selectedIndex);
					}
					try {
						System.out.println("selectedIndex is " + selectedIndex);
						outputStream.writeObject(new ClientReplaceObjMsg(element,
								selectedIndex));
						outputStream.flush();
						System.out.println("sending replace");
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("failed to send replace");
					}
					selected = null;
				}
				selected = null;
				break;
			case 8:
				if (ranLineObj != null) {
					ranLineObj.setPoints(x1, y1);
					ranLineVector.add(ranLineObj);
					System.out.println("rlines size is " + ranLineVector.size());
					try {
						outputStream.writeObject(new ClientAddRanLineMsg(ranLineObj));
						outputStream.flush();
						System.out.println("send add rline message");
					} catch (Exception ex) {
						System.out.println("send add rline failed");
					}
					ranLineObj = null;
				}
				break;
			default:
				element.setPara(topx, topy, width, height, color);
				selected = null;
				try {
					outputStream.writeObject(new ClientAddObjMsg(element));
					outputStream.flush();
					if (element != null) {
						element.paint(getGraphics());
					}
					System.out.println("sending addElementMsg");
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("failed to send add");
				}
				break;
			}
			element = null;
			repaint();
		}
	}
	class WBMouseMotionListener extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			isSelected = true;
			x1 = e.getX();
			y1 = e.getY();
			if (type != 8) {
				switch (type) {
				case 0:
					if (selected != null && selected.getType() != 7) {
						Rectangle r = new Rectangle(selected.getBounds());
						r.translate(x1 - x0, y1 - y0);
						x1 = e.getX();
						y1 = e.getY();
						element.setPara((int) r.getBounds().getX(), (int) r
								.getBounds().getY(), (int) r.getBounds()
								.getWidth(), (int) r.getBounds().getHeight(),
								Color.red);
					}
					break;
				case 1:
					topx = Math.min(x0, x1);
					topy = Math.min(y0, y1);
					width = Math.abs(x0 - x1); //rect
					height = Math.abs(y0 - y1);
					element.setPara(topx, topy, width, height, color);
					break;
				case 2:
					width = 2 * Math.abs(x0 - x1); //oval
					height = 2 * Math.abs(y0 - y1);
					topx = x0 - Math.abs(x0 - x1);
					topy = y0 - Math.abs(y0 - y1);
					element.setPara(topx, topy, width, height, color);
					break;
				case 3:
					width = 2 * Math.abs(x0 - x1); //rrect
					height = 2 * Math.abs(y0 - y1);
					topx = x0 - Math.abs(x0 - x1);
					topy = y0 - Math.abs(y0 - y1);
					element.setPara(topx, topy, width, height, color);
					break;
				case 4:
					topx = Math.min(x0, x1);
					topy = Math.min(y0, y1);
					width = Math.abs(x0 - x1); //frect
					height = Math.abs(y0 - y1);
					element.setPara(topx, topy, width, height, color);
					break;
				case 5:
					width = 2 * Math.abs(x0 - x1); //foval
					height = 2 * Math.abs(y0 - y1);
					topx = x0 - Math.abs(x0 - x1);
					topy = y0 - Math.abs(y0 - y1);
					element.setPara(topx, topy, width, height, color);
					break;
				case 6:
					width = 2 * Math.abs(x0 - x1); //frrect
					height = 2 * Math.abs(y0 - y1);
					topx = x0 - Math.abs(x0 - x1);
					topy = y0 - Math.abs(y0 - y1);
					element.setPara(topx, topy, width, height, color);
					break;
				case 7:
					topx = x0; //line
					topy = y0;
					width = x1;
					height = y1;
					element.setPara(topx, topy, width, height, color);
					break;
				}
				if (isSelected)
					repaint();
				if (element != null)
					element.paint(getGraphics());
				isSelected = false;
			} else if (ranLineObj != null) {
				ranLineObj.setPoints(x1, y1);
				System.out.println(x1 + " " + y1 + " added");
				Graphics g = getGraphics();
				g.setColor(color);
				g.drawLine(x0, y0, x1, y1);
				x0 = x1;
				y0 = y1;
			}
		}
	}

	public static void setColor(Color c) {
		color = c;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Enumeration allElements = elements.elements();
		while (allElements.hasMoreElements()) {
			((Element) allElements.nextElement()).paint(g);
		}
		Enumeration storedlines = ranLineVector.elements();
		while (storedlines.hasMoreElements()) {
			((RanLineObj) storedlines.nextElement()).paint(g);
		}
	}
	

	
}
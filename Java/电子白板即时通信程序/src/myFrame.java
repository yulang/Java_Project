import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

@SuppressWarnings("serial")
class myPanel extends JPanel {

	private randomLine ranLine = new randomLine(Color.red);
	private ServerSocket s;
	private Socket ss;
	private ObjectOutputStream output;
	private OutputStream os;
	private BufferedOutputStream b;
	private Graphics g;
	public int x0, y0, x1, y1;

	public myPanel() {
		setBounds(0, 0, 400, 300);
		setBackground(Color.white);
		addMouseMotionListener(new mouseAction());
		addMouseListener(new mousemovpress());
		this.validate();
		new myThread().start();
	}

	public void paint(Graphics g) {
	}

	class mouseAction extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			g = getGraphics();
			g.setColor(Color.red);
			x1 = e.getX();
			y1 = e.getY();
			g.drawLine(x0, y0, x1, y1);
			x0 = x1;
			y0 = y1;
			ranLine.setPoints(x1, y1);
		}
	}

	class mousemovpress extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			x0 = e.getX();
			y0 = e.getY();
		}

		public void mouseReleased(MouseEvent e) {
			try {
				os = ss.getOutputStream();
				b = new BufferedOutputStream(os);
				output = new ObjectOutputStream(b);
				output.writeObject(ranLine);
				output.flush();
			} catch (IOException ex) {
			}
		}

	}

	class myThread extends Thread {

		public void run() {
			try {
				s = new ServerSocket(1234);
				ss = s.accept();
			} catch (IOException ex) {
			}
		}

	}

}

@SuppressWarnings("serial")
class myFrame extends JFrame {

	myPanel p = new myPanel();

	public myFrame() {
		super("·þÎñÆ÷");
		setLayout(null);
		setBounds(100, 100, 400, 300);
		add(p);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] ss) {
		new myFrame();
	}

}
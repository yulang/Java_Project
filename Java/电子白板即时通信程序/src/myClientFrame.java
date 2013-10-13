import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class myClientPanel extends JPanel {

	private randomLine ranLine;
	private Socket ss;
	private InputStream is;
	private BufferedInputStream b;
	private ObjectInputStream input;
	private JFrame f;

	public myClientPanel(JFrame f) {
		this.setF(f);
		setBounds(0, 0, 400, 300);
		setBackground(Color.white);
		this.validate();
		new myThread().start();
		try {
			ss = new Socket("127.0.0.1", 1234);
		} catch (IOException ex) {
			f.setTitle("error");
		}
	}

	public void paint(Graphics g) {
		this.ranLine.paint(g);
	}

	public void setF(JFrame f) {
		this.f = f;
	}

	public JFrame getF() {
		return f;
	}

	class myThread extends Thread {

		public void run() {
			while (true) {
				try {
					is = ss.getInputStream();
					b = new BufferedInputStream(is);
					input = new ObjectInputStream(b);
					ranLine = (randomLine) input.readObject();
					repaint();
				} catch (Exception ex) {
					// f.setTitle("error-errpr");
				}
			}
		}

	}

}

@SuppressWarnings("serial")
class myClientFrame extends JFrame {

	myClientPanel p = new myClientPanel(this);

	public myClientFrame() {
		super("ÎÒµÄ°×°å");
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
		new myClientFrame();
	}

}
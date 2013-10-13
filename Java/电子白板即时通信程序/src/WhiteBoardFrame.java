import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
class WhiteBoardFrame extends JFrame {

	private String serIp = null;
	private String serPort = null;
	private String myName = null;
	public boolean active = false;
	private BorderLayout borderLayout1 = new BorderLayout();
	private BorderLayout borderLayout2 = new BorderLayout();
	private BorderLayout borderLayout3 = new BorderLayout();
	private BorderLayout borderLayout4 = new BorderLayout();
	private BorderLayout borderLayout5 = new BorderLayout();
	private BorderLayout borderLayout6 = new BorderLayout();
	private BorderLayout borderLayout7 = new BorderLayout();
	private BorderLayout borderLayout8 = new BorderLayout();
	private BorderLayout borderLayout9 = new BorderLayout();
	private BorderLayout borderLayout10 = new BorderLayout();
	private BorderLayout borderLayout11 = new BorderLayout();
	private BorderLayout borderLayout12 = new BorderLayout();
	private BorderLayout borderLayout13 = new BorderLayout();
	private BorderLayout borderLayout14 = new BorderLayout();
	private BorderLayout borderLayout15 = new BorderLayout();
	private GridLayout gridLayout1 = new GridLayout();
	private GridLayout gridLayout2 = new GridLayout();
	private GridLayout gridLayout3 = new GridLayout();
	private GridLayout gridLayout4 = new GridLayout();
	private JPanel jPanel1 = new JPanel();
	private JPanel jPanel2 = new JPanel();
	private JPanel jPanel3 = new JPanel();
	private JPanel jPanel4 = new JPanel();
	private JPanel jPanel5 = new JPanel();
	private JPanel jPanel6 = new JPanel();
	private JPanel jPanel7 = new JPanel();
	private JPanel jPanel8 = new JPanel();
	private JPanel jPanel9 = new JPanel();
	private JPanel selPane = new JPanel();
	private JPanel ranLinePane = new JPanel();
	private JPanel frectPanel = new JPanel();
	private JPanel rrecPanel = new JPanel();
	private JPanel fovalPanel = new JPanel();
	private JPanel ovalPanel = new JPanel();
	private JPanel frecPanel = new JPanel();
	private JPanel recPanel = new JPanel();
	private JPanel linePanel = new JPanel();
	private JPanel grayColorPanel = new JPanel();
	private JPanel blueColorPanel = new JPanel();
	private JPanel cyanColorPanel = new JPanel();
	private JPanel magentaColorPanel = new JPanel();
	private JPanel greenColorPanel = new JPanel();
	private JPanel orangeColorPanel = new JPanel();
	private JPanel redColorPanel = new JPanel();
	private JPanel blackColorPanel = new JPanel();
	private JButton selBut = new JButton();
	private JButton lineBut = new JButton();
	private JButton recBut = new JButton();
	private JButton frecBut = new JButton();
	private JButton ovalBut = new JButton();
	private JButton fovalBut = new JButton();
	private JButton rrecBut = new JButton();
	private JButton frrecBut = new JButton();
	private JButton rlineBut = new JButton();
	private JLabel jLabel2 = new JLabel();
	private JLabel jLabel3 = new JLabel();
	private JLabel jLabel4 = new JLabel();
	private JLabel jLabel5 = new JLabel();
	private JLabel jLabel6 = new JLabel();
	private JLabel jLabel7 = new JLabel();
	private String path = "./img/Image-ccsm.png";
	private ImageIcon lineimage = new ImageIcon(path);
	private String path1 = "./img/Image-ccsm.png";
	private ImageIcon rlinimage = new ImageIcon(path1);
	private String path2 = "./img/Image-ccsm.png";
	private ImageIcon rectimage = new ImageIcon(path2);
	private String path3 = "./img/Image-ccsm.png";
	private ImageIcon rrectimage = new ImageIcon(path3);
	private String path4 = "./img/Image-ccsm.png";
	private ImageIcon ovalimage = new ImageIcon(path4);
	private String path5 = "./img/Image-ccsm.png";
	private ImageIcon fovalimage = new ImageIcon(path5);
	private String path6 = "./img/Image-ccsm.png";
	private ImageIcon frectimage = new ImageIcon(path6);
	private String path8 = "./img/Image-ccsm.png";
	private ImageIcon frrectimage = new ImageIcon(path8);
	private String path9 = "./img/Image-ccsm.png";
	private ImageIcon selectimage = new ImageIcon(path9);
	static PrintStream ps = null;
	public Socket clientSocket = null;
	static String message = null;
	public static int i;
	public int colorindex;
	public Color color = null;
	public int currentTool = 0;
	public int x0, y0, x1, y1, width, height;
	public int xold, yold, wold, hold;
	public Class cl;
	public Element element = null;
	public Vector elements = new Vector();
	public WhiteBoardPanel whiteBoard = null;
	public static String name = "";
	JTextArea t1 = new JTextArea(myName + ":" + "Hello\n", 29, 30);
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JTextField tt1 = new JTextField(66);
	JButton b1 = new JButton("发送消息");
	JLabel l1 = new JLabel("消   息：");

	// JButton b2 = new JButton("登   陆");

	public WhiteBoardFrame() {
	}

	public WhiteBoardFrame(String serIp, String serPort, String name) {
		this.serIp = serIp;
		this.serPort = serPort;
		this.myName = name;
		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (ClassNotFoundException e1) {
		// e1.printStackTrace();
		// } catch (InstantiationException e1) {
		// e1.printStackTrace();
		// } catch (IllegalAccessException e1) {
		// e1.printStackTrace();
		// } catch (UnsupportedLookAndFeelException e1) {
		// e1.printStackTrace();
		// }
		init();
	}

	public void init() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		color = Color.gray;
		try {
			int port = Integer.parseInt(serPort);
			clientSocket = new Socket(serIp, port);
			whiteBoard = new WhiteBoardPanel();// clientSocket);
			whiteBoard.init(clientSocket);
			whiteBoard.setBackground(Color.white);
			whiteBoard.setType(currentTool);
			WhiteBoardPanel.setColor(color);

			jPanel2.add(whiteBoard, BorderLayout.CENTER);// ///*****************

			status(currentTool, 0);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("failed to connect to wbServer");
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		jPanel1.setLayout(borderLayout2);
		jPanel3.setLayout(borderLayout4);
		jPanel1.setBackground(Color.white);
		jPanel1.setPreferredSize(new Dimension(80, 300));
		jPanel3.setBorder(BorderFactory.createEtchedBorder());
		jPanel3.setPreferredSize(new Dimension(80, 300));
		jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
		});
		jPanel4.setLayout(borderLayout6);
		jPanel5.setLayout(gridLayout2);
		gridLayout2.setHgap(2);
		gridLayout2.setVgap(2);
		jPanel6.setBorder(BorderFactory.createEtchedBorder());
		jPanel6.setPreferredSize(new Dimension(10, 200));
		jPanel6.setLayout(borderLayout5);
		// jPanel7.setPreferredSize(new Dimension(10, 20));
		// jPanel7.setLayout(gridLayout3);
		// jLabel2.setForeground(SystemColor.desktop);
		// jLabel2.setPreferredSize(new Dimension(20, 18));
		jLabel2.setText("当前选择的颜色是：");
		// jLabel4.setForeground(SystemColor.desktop);
		jLabel4.setText("");
		// jLabel6.setForeground(SystemColor.desktop);
		jLabel6.setText("          当前选择的图形是：");
		jPanel9.setLayout(gridLayout1);
		jPanel8.setLayout(gridLayout4);
		gridLayout4.setColumns(2);
		gridLayout4.setHgap(1);
		gridLayout4.setRows(0);
		gridLayout4.setVgap(1);
		gridLayout1.setColumns(2);
		gridLayout1.setHgap(1);
		gridLayout1.setRows(0);
		gridLayout1.setVgap(1);
		selPane.setBorder(BorderFactory.createLineBorder(Color.black));
		selPane.setToolTipText("");
		selPane.setLayout(borderLayout7);

		selBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				select_mousePressed(e);
			}

		});

		frectPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		frectPanel.setLayout(borderLayout14);

		frrecBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frrect_mousePressed(e);
			}

		});
		ranLinePane.setBorder(BorderFactory.createLineBorder(Color.black));
		ranLinePane.setLayout(borderLayout15);

		rlineBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				rline_mousePressed(e);
			}

		});
		rrecPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rrecPanel.setLayout(borderLayout13);

		rrecBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				rrect_mousePressed(e);
			}
		});
		ovalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		ovalPanel.setLayout(borderLayout11);

		ovalBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oval_mousePressed(e);
			}
		});
		fovalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		fovalPanel.setLayout(borderLayout12);

		fovalBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				foval_mousePressed(e);
			}
		});

		recPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		recPanel.setLayout(borderLayout9);

		recBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				rect_mousePressed(e);
			}
		});

		frecPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		frecPanel.setLayout(borderLayout10);

		frecBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frect_mousePressed(e);
			}
		});

		linePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		linePanel.setLayout(borderLayout8);

		lineBut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				line_mousePressed(e);
			}
		});

		blueColorPanel.setBackground(Color.blue);
		blueColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		blueColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				blue_mousePressed(e);
			}
		});

		grayColorPanel.setBackground(Color.gray);
		grayColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		grayColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				gray_mousePressed(e);
			}
		});

		cyanColorPanel.setBackground(Color.cyan);
		cyanColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		cyanColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				cyan_mousePressed(e);
			}
		});

		magentaColorPanel.setBackground(Color.magenta);
		magentaColorPanel
				.setBorder(BorderFactory.createLineBorder(Color.black));

		magentaColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				magenta_mousePressed(e);
			}
		});

		greenColorPanel.setBackground(Color.green);
		greenColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		greenColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				green_mousePressed(e);
			}
		});

		orangeColorPanel.setBackground(Color.orange);
		orangeColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		orangeColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				orange_mousePressed(e);
			}
		});

		redColorPanel.setBackground(Color.red);
		redColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		redColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				red_mousePressed(e);
			}
		});

		blackColorPanel.setBackground(Color.black);
		blackColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		blackColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				black_mousePressed(e);
			}
		});

		jPanel2.setLayout(borderLayout3);
		jPanel2.setBorder(BorderFactory.createEtchedBorder());
		jPanel2.setPreferredSize(new Dimension(4, 30));
		jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
		});

		jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
		});

		lineBut.setForeground(Color.white);
		lineBut.setBorder(null);
		lineBut.setBorderPainted(false);
		lineBut.setIcon(lineimage);
		lineBut.setText("");
		recBut.setBackground(Color.white);
		recBut.setForeground(Color.white);
		recBut.setBorder(null);
		recBut.setBorderPainted(false);
		recBut.setContentAreaFilled(false);
		recBut.setIcon(rectimage);
		recBut.setText("");
		frecBut.setForeground(Color.white);
		frecBut.setBorder(null);
		frecBut.setBorderPainted(false);
		frecBut.setContentAreaFilled(false);
		frecBut.setIcon(frectimage);
		frecBut.setText("");
		ovalBut.setBackground(Color.white);
		ovalBut.setForeground(Color.white);
		ovalBut.setBorder(null);
		ovalBut.setBorderPainted(false);
		ovalBut.setContentAreaFilled(false);
		ovalBut.setIcon(ovalimage);
		ovalBut.setText("");
		fovalBut.setForeground(Color.white);
		fovalBut.setBorder(null);
		fovalBut.setIcon(fovalimage);
		fovalBut.setText("");
		rrecBut.setForeground(Color.white);
		rrecBut.setBorder(null);
		rrecBut.setIcon(rrectimage);
		rrecBut.setText("");
		frrecBut.setForeground(Color.white);
		frrecBut.setBorder(null);
		frrecBut.setIcon(frrectimage);
		frrecBut.setText("");
		rlineBut.setForeground(Color.white);
		rlineBut.setBorder(null);
		rlineBut.setBorderPainted(false);
		rlineBut.setContentAreaFilled(false);
		rlineBut.setIcon(rlinimage);
		rlineBut.setText("");
		jPanel9.setPreferredSize(new Dimension(40, 200));
		jPanel8.setPreferredSize(new Dimension(40, 80));
		jPanel4.setBackground(Color.white);
		jPanel4.setBorder(BorderFactory.createEtchedBorder());
		jPanel4.setMinimumSize(new Dimension(0, 482));
		jPanel4.setPreferredSize(new Dimension(40, 300));
		selBut.setBackground(Color.white);
		selBut.setForeground(Color.white);
		selBut.setBorder(null);
		selBut.setBorderPainted(false);
		selBut.setContentAreaFilled(false);
		selBut.setIcon(selectimage);
		/*
		 * this.add(jPanel1, BorderLayout.WEST); this.add(jPanel7,
		 * BorderLayout.SOUTH); this.add(jPanel2, BorderLayout.CENTER);
		 */

		this.add(jPanel1, BorderLayout.WEST);

		jPanel1.add(jPanel4, BorderLayout.CENTER);

		jPanel4.add(jPanel8, BorderLayout.SOUTH);

		jPanel8.add(grayColorPanel, null);
		jPanel8.add(blackColorPanel, null);
		jPanel8.add(redColorPanel, null);
		jPanel8.add(orangeColorPanel, null);
		jPanel8.add(greenColorPanel, null);
		jPanel8.add(magentaColorPanel, null);
		jPanel8.add(cyanColorPanel, null);
		jPanel8.add(blueColorPanel, null);

		jPanel4.add(jPanel9, BorderLayout.CENTER);

		jPanel9.add(selPane, null);
		jPanel9.add(linePanel, null);
		jPanel9.add(recPanel, null);
		jPanel9.add(frecPanel, null);
		jPanel9.add(ovalPanel, null);
		jPanel9.add(fovalPanel, null);
		jPanel9.add(rrecPanel, null);
		jPanel9.add(frectPanel, null);
		jPanel9.add(ranLinePane, null);

		jPanel1.add(jPanel5, BorderLayout.SOUTH);

		this.add(jPanel7, BorderLayout.SOUTH);

		jPanel7.add(jLabel2, null);
		jPanel7.add(jLabel3, null);
		jPanel7.add(jLabel4, null);
		jPanel7.add(jLabel5, null);
		jPanel7.add(jLabel6, null);
		jPanel7.add(jLabel7, null);

		// jPanel7.add(b2, null);

		this.add(jPanel2, BorderLayout.CENTER);

		selPane.add(selBut, BorderLayout.CENTER);
		linePanel.add(lineBut, BorderLayout.CENTER);
		recPanel.add(recBut, BorderLayout.CENTER);
		frecPanel.add(frecBut, BorderLayout.CENTER);
		ovalPanel.add(ovalBut, BorderLayout.CENTER);
		fovalPanel.add(fovalBut, BorderLayout.CENTER);
		rrecPanel.add(rrecBut, BorderLayout.CENTER);
		frectPanel.add(frrecBut, BorderLayout.CENTER);
		ranLinePane.add(rlineBut, BorderLayout.CENTER);
		this.setTitle("当前用户：" + myName + " 在线");

		// ==================

		p1.setBackground(Color.ORANGE);
		p2.setBackground(Color.LIGHT_GRAY);
		t1.setBackground(Color.LIGHT_GRAY);
		t1.setLineWrap(true);
		p1.add(t1);
		p2.add(l1);
		tt1.setFont(new Font("宋体", 0, 14));
		p2.add(tt1);
		p2.add(b1);
		jPanel2.add(p1, BorderLayout.EAST);
		jPanel2.add(p2, BorderLayout.SOUTH);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t1.append(myName + ":" + tt1.getText() + "\n");
			}
		});
		// ==================

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension(800, 600));
		this.setBounds(screenSize.width / 2 - 300, screenSize.height / 2 - 300,
				800, 600);
		this.setVisible(true);
		pack();
	}

	public void status(int Tool, int cindex) {
		switch (Tool) {
		case 0:
			jLabel7.setText("移 动 图  形");
			break;
		case 1:
			jLabel7.setText("矩        形");
			break;
		case 2:
			jLabel7.setText("椭        圆");
			break;
		case 3:
			jLabel7.setText("圆 角 矩  形");
			break;
		case 4:
			jLabel7.setText("填 充 矩  形");
			break;
		case 5:
			jLabel7.setText("填 充 椭  圆");
			break;
		case 6:
			jLabel7.setText("填充园角矩形");
			break;
		case 7:
			jLabel7.setText("直        线");
			break;
		case 8:
			jLabel7.setText("任 意 曲  线");
			break;
		}
		switch (cindex) {
		case 0:
			jLabel5.setText("灰色");
			break;
		case 1:
			jLabel5.setText("黑色");
			break;
		case 2:
			jLabel5.setText("红色");
			break;
		case 3:
			jLabel5.setText("橙色");
			break;
		case 4:
			jLabel5.setText("绿色");
			break;
		case 5:
			jLabel5.setText("粉色");
			break;
		case 6:
			jLabel5.setText("浅蓝");
			break;
		case 7:
			jLabel5.setText("蓝色");
			break;
		}
	}

	void select_mousePressed(MouseEvent e) {
		currentTool = 0;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void rect_mousePressed(MouseEvent e) {
		currentTool = 1;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void oval_mousePressed(MouseEvent e) {
		currentTool = 2;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void rrect_mousePressed(MouseEvent e) {
		currentTool = 3;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void gray_mousePressed(MouseEvent e) {
		colorindex = 0;
		WhiteBoardPanel.setColor(Color.gray);
		status(currentTool, colorindex);
	}

	void black_mousePressed(MouseEvent e) {
		colorindex = 1;
		status(currentTool, colorindex);
		WhiteBoardPanel.setColor(Color.black);
	}

	void red_mousePressed(MouseEvent e) {
		colorindex = 2;
		WhiteBoardPanel.setColor(Color.red);
		status(currentTool, colorindex);
	}

	void orange_mousePressed(MouseEvent e) {
		colorindex = 3;
		WhiteBoardPanel.setColor(Color.orange);
		status(currentTool, colorindex);
	}

	void green_mousePressed(MouseEvent e) {
		colorindex = 4;
		WhiteBoardPanel.setColor(Color.green);
		status(currentTool, colorindex);
	}

	void magenta_mousePressed(MouseEvent e) {
		colorindex = 5;
		WhiteBoardPanel.setColor(Color.magenta);
		status(currentTool, colorindex);
	}

	void cyan_mousePressed(MouseEvent e) {
		colorindex = 6;
		WhiteBoardPanel.setColor(Color.cyan);
		status(currentTool, colorindex);
	}

	void blue_mousePressed(MouseEvent e) {
		colorindex = 7;
		WhiteBoardPanel.setColor(Color.blue);
		status(currentTool, colorindex);
	}

	void frect_mousePressed(MouseEvent e) {
		currentTool = 4;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void foval_mousePressed(MouseEvent e) {
		currentTool = 5;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void frrect_mousePressed(MouseEvent e) {
		currentTool = 6;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void line_mousePressed(MouseEvent e) {
		currentTool = 7;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	void rline_mousePressed(MouseEvent e) {
		currentTool = 8;
		whiteBoard.setType(currentTool);
		status(currentTool, colorindex);
	}

	// �˳�ʱ�ص�ͨ�Ŷ˿�
	public void destroy() {
		try {
			WhiteBoardPanel.outputStream.writeObject(new ClientQuitMsg());
			WhiteBoardPanel.outputStream.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getLocalIP() {
		InetAddress addr = null;
		String ip = null;
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static void main(String args[]) {
		 try {
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (ClassNotFoundException e1) {
		 e1.printStackTrace();
		 } catch (InstantiationException e1) {
		 e1.printStackTrace();
		 } catch (IllegalAccessException e1) {
		 e1.printStackTrace();
		 } catch (UnsupportedLookAndFeelException e1) {
		 e1.printStackTrace();
		 }
		new WhiteBoardFrame("127.0.0.1", "1234", "Moky");
	}
}
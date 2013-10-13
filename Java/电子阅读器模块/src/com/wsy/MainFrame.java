package com.wsy;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton; //import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree; //import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;

import com.sun.pdfview.FullScreenWindow;
import com.sun.pdfview.OutlineNode;
import com.sun.pdfview.PDFDestination;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFObject;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;
import com.sun.pdfview.PageChangeListener;
import com.sun.pdfview.PagePanel;
import com.sun.pdfview.ThumbPanel;
import com.sun.pdfview.action.GoToAction;
import com.sun.pdfview.action.PDFAction;
import com.util.CreatecdIcon;

public class MainFrame extends JFrame implements PageChangeListener,
		TreeSelectionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	//放置主内容的面板
	JPanel jpmain = new JPanel();
	//放置读取PDF文档内容的面板
	PagePanel jp;
	//全屏显示面板
	FullScreenWindow fullScreen;
	//缩位图面板
	ThumbPanel thumbs;

	JScrollPane documentScroller = new JScrollPane();
	//PDFRender包中获取PDF文档的PDFFile类
	PDFFile pdffile;
	//用户填写页码的文本框
	JTextField pageField;

	int curpage = -1;
	JTabbedPane tabbedPane;
	//用于获取大纲的OutlineNode对象
	OutlineNode outline = null;
	JButton smallButton;
	JButton fullScreenButton;
	PageFormat pformat = PrinterJob.getPrinterJob().defaultPage();
	String docName;

	private Timer activityMonitor;
	private SimulateActivity activity;
	PagePanel fspp;
	PageBuilder pb = new PageBuilder();

	private Point loc = null;
	private Point tmp = null;
	private boolean isDragged = false;

	public MainFrame() {
		// TODO 自动生成构造函数存根
		try {
			UIManager
					.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		setTitle("电子阅读器");
		this.setResizable(false);
		setSize(new Dimension(900, 700));
		getContentPane().setLayout(null);
		getContentPane().add(CreateMenuBar());
		/*
		 * 创建左边的缩位图面板
		 */
		thumbs = new ThumbPanel(null);
		thumbscroll = new JScrollPane(thumbs,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		thumbscroll.getViewport().setView(thumbs);
		thumbscroll.getViewport().setBackground(Color.gray);
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab(null, CreatecdIcon.add("02.GIF"), thumbscroll);
		tabbedPane.addTab(null, CreatecdIcon.add("05.GIF"), null);
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		getContentPane().add(tabbedPane);
		tabbedPane.setBounds(0, 63, 270, 600);
		getContentPane().add(thumbscroll);

		/*
		 *创建中间的内容面板 
		 */
		contentPanel = new JScrollPane();
		jp = new PagePanel();
		jp.addMouseListener(new JPMouseAction());
		jp.addMouseMotionListener(new JPMouseMotionAction());
		jpmain.setLayout(new GridLayout(0, 1));
		contentPanel.setViewportView(jpmain);
		contentPanel.setBounds(270, 63, 600, 600);
		getContentPane().add(contentPanel);

		/*
		 * 创建工具栏
		 */
		getContentPane().add(CreateToolBar());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - getWidth()) / 2;
		int y = (screen.height - getHeight()) / 2;
		setLocation(x, y);
	}

	public static void main(String[] args) {
		// TODO 自动生成方法存根
		new MainFrame().setVisible(true);
	}

	public JMenuBar CreateMenuBar() {

		final JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1000, 25);
		final JMenu newItemMenuItem1 = new JMenu();
		newItemMenuItem1.setText("文件(F)");

		menuBar.add(newItemMenuItem1);
		final JMenuItem newItemMenuItem_1 = new JMenuItem();
		newItemMenuItem_1.setText("打开");
		newItemMenuItem1.add(newItemMenuItem_1);
		newItemMenuItem_1.setToolTipText("Open PDF file");
		newItemMenuItem_1
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						// TODO 自动生成方法存根
						doOpen();
					}
				});
		final JMenuItem newItemMenuItem = new JMenuItem();
		newItemMenuItem.setText("页面设置");
		newItemMenuItem1.add(newItemMenuItem);
		newItemMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成方法存根
				doPageSetup();
			}
		});
		final JMenuItem newItemMenuItem_3 = new JMenuItem();
		newItemMenuItem_3.setText("打印");
		newItemMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成方法存根
				doPrint();
			}
		});
		newItemMenuItem1.add(newItemMenuItem_3);

		final JMenuItem newItemMenuItem_2 = new JMenuItem();
		newItemMenuItem_2.setText("退出");
		newItemMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成方法存根
				doClose();
				System.exit(0);
			}
		});
		newItemMenuItem1.add(newItemMenuItem_2);
		return menuBar;
	}

	Action nextAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) {
			doNext();
		}
	};

	Action prevAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) {
			doPrev();
		}
	};
	Action BigAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			PDFPage page = pdffile.getPage(curpage);
			Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()
					.getWidth(), (int) page.getBBox().getHeight());

			//generate the image
			Image img = page.getImage(rect.width * 2, rect.height * 2, //width & height
					rect, // clip rect
					null, // null for the ImageObserver
					true, // fill background with white
					true // block until drawing is done
					);
			if (jpmain != null) {
				jpmain.removeAll();
			}
			contentPanel.setViewportView(jpmain);
			contentPanel.setBounds(230, 60, 752, 600);
			jpmain.addMouseListener(new JPMouseAction());
			jpmain.addMouseMotionListener(new JPMouseMotionAction());
			jpmain.add(new JLabel(new ImageIcon(img)));
			validate();
			repaint();
		}
	};
	Action SmallAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成方法存根
			PDFPage page = pdffile.getPage(curpage);
			Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()
					.getWidth(), (int) page.getBBox().getHeight());

			//generate the image
			Image img = page.getImage(rect.width / 2, rect.height / 2, //width & height
					rect, // clip rect
					null, // null for the ImageObserver
					true, // fill background with white
					true // block until drawing is done
					);
			if (jpmain != null) {
				jpmain.removeAll();
			}
			contentPanel.setViewportView(jpmain);
			contentPanel.setBounds(230, 60, 600, 600);

			jpmain.add(new JLabel(new ImageIcon(img)));
			validate();
			repaint();
		}
	};
	Action fristAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			doFirst();
		}
	};
	Action lastAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			doLast();
		}
	};
	Action fullScreenAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) {
			//System.out.println("fullscreenTest"+((evt.getModifiers() & evt.SHIFT_MASK )!= 0));
			doFullScreen((evt.getModifiers() & ActionEvent.SHIFT_MASK) != 0);
		}
	};
	Action noteAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) {
			note();
		}
	};
	public void note(){
		 new NoteBook();
		 }

	public void doFirst() {
		gotoPage(0);
	}

	public void doNext() {
		gotoPage(curpage + 1);
	}

	public void doPrev() {
		gotoPage(curpage - 1);
	}

	public void doLast() {
		gotoPage(pdffile.getNumPages() - 1);
	}

	public void doFullScreen(boolean force) {
		setFullScreenMode(fullScreen == null, force);
	}

	private JToolBar CreateToolBar() { // 创建工具栏的方法
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 23, 1000, 40);
		toolBar.setFloatable(false);
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));

		fristButton = new JButton(fristAction);
		fristButton.setEnabled(false);
		fristButton.setIcon(CreatecdIcon.add("frist.gif"));
		fristButton.setToolTipText("首页");
		toolBar.add(fristButton);

		nextButton = new JButton(nextAction);
		nextButton.setEnabled(false);
		nextButton.setIcon(CreatecdIcon.add("next.gif"));
		nextButton.setToolTipText("下一页");
		//((Object) nextButton).setHideActionText(true);
		toolBar.add(nextButton);

		backButton = new JButton(prevAction);
		backButton.setEnabled(false);
		backButton.setIcon(CreatecdIcon.add("back.gif"));
		backButton.setToolTipText("上一页");
		//((Object) backButton).setHideActionText(true);
		toolBar.add(backButton);

		lastButton = new JButton(lastAction);
		lastButton.setEnabled(false);
		lastButton.setIcon(CreatecdIcon.add("last.gif"));
		lastButton.setToolTipText("尾页");
		toolBar.add(lastButton);

		pageField = new JTextField("-", 0);
		pageField.setEditable(true);
		pageField.setBounds(0, 0, 50, 10);
		pageField.addKeyListener(this);
		pageField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int pagenum = -1;
				try {
					pagenum = Integer.parseInt(pageField.getText()) - 1;
				} catch (NumberFormatException nfe) {
				}
				if (pagenum >= pdffile.getNumPages()) {
					pagenum = pdffile.getNumPages() - 1;
				}
				if (pagenum >= 0) {
					if (pagenum != curpage) {
						gotoPage(pagenum);
					}
				} else {
					pageField.setText(String.valueOf(curpage));
				}
			}
		});
		toolBar.add(pageField);
		jl = new JLabel();
		toolBar.add(jl);

		toolBar.addSeparator();
		bigButton = new JButton(BigAction);
		toolBar.add(bigButton);
		bigButton.setEnabled(false);
		bigButton.setToolTipText("页面放大");
		bigButton.setIcon(CreatecdIcon.add("big.gif"));

		smallButton = new JButton(SmallAction);
		smallButton.setEnabled(false);
		smallButton.setToolTipText("页面缩小");
		smallButton.setIcon(CreatecdIcon.add("small.gif"));
		toolBar.add(smallButton);
		toolBar.addSeparator();
		fullScreenButton = new JButton(fullScreenAction);
		fullScreenButton.setEnabled(false);
		fullScreenButton.setToolTipText("全屏");
		fullScreenButton.setIcon(CreatecdIcon.add("fullScreen.gif"));
		toolBar.add(fullScreenButton);
		noteButton = new JButton(noteAction);
		noteButton.setEnabled(false);
		noteButton.setToolTipText("读书笔记");
		noteButton.setIcon(CreatecdIcon.add("03.gif"));
		toolBar.add(noteButton);
		return toolBar;
	}

	private File prevDirChoice;

	FileFilter pdfFilter = new FileFilter() {
		@Override
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".pdf");
		}
		@Override
		public String getDescription() {
			return "Choose a PDF file";
		}
	};

	private JScrollPane contentPanel;

	private JScrollPane thumbscroll;

	private JButton nextButton;

	private JButton backButton;

	private JLabel jl;

	private JButton bigButton;
	private JButton fristButton;
	private JButton lastButton;
	private JButton noteButton;

	public void doOpen() {
		try {
			if (jp != null) {
				doClose();
			}
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(prevDirChoice);
			fc.setFileFilter(pdfFilter);
			fc.setMultiSelectionEnabled(false);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					prevDirChoice = fc.getSelectedFile();
					RandomAccessFile raf = new RandomAccessFile(prevDirChoice
							.getAbsolutePath(), "r");
					FileChannel channel = raf.getChannel();
					ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,
							0, channel.size());
					try {
						pdffile = new PDFFile(buf);
					} catch (IOException ioe) {
						return;
					}
					docName = prevDirChoice.getName();
					setTitle(docName);
					activityMonitor = new Timer(500, new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							// TODO 自动生成方法存根
							int current = activity.getCurrent();
							contentPanel.getVerticalScrollBar().setValue(
									current);
							if (current == activity.getTarget()) {
								activityMonitor.stop();

							}
						}
					});
					for (int i = 1; i < 10; i++) {
						contentPanel.setViewportView(jpmain);
						activity = new SimulateActivity(contentPanel
								.getVerticalScrollBar().getMaximum());
						new Thread(activity).start();
						activityMonitor.start();
						PDFPage page = pdffile.getPage(i);
						PagePanel jp2 = new PagePanel();
						jp2.addMouseListener(new JPMouseAction());
						jp2.addMouseMotionListener(new JPMouseMotionAction());
						jpmain.add(jp2);
						validate();
						jp2.showPage(page);
					}

					thumbs = new ThumbPanel(pdffile);
					thumbs.addPageChangeListener(this);
					thumbscroll.getViewport().setView(thumbs);
					thumbscroll.getViewport().setBackground(Color.gray);
					tabbedPane.setComponentAt(0, thumbscroll);

					try {
						outline = pdffile.getOutline();
					} catch (IOException ioe) {
					}
					if (outline != null) {
						if (outline.getChildCount() > 0) {
							JTree jt = new JTree(outline);
							jt.setRootVisible(false);
							jt.addTreeSelectionListener(this);
							JScrollPane jsp = new JScrollPane(jt);
							tabbedPane.setComponentAt(1, jsp);
						}

					}
				} catch (Exception ioe) {
					ioe.printStackTrace();
				}
				getContentPane().add(tabbedPane);
				tabbedPane.setBounds(0, 60, 230, 607);
				validate();
				backButton.setEnabled(true);
				nextButton.setEnabled(true);
				pageField.setEditable(true);
				jl.setText("(" + (curpage + 2) + "/" + pdffile.getNumPages()
						+ ")");
				bigButton.setEnabled(true);
				smallButton.setEnabled(true);
				lastButton.setEnabled(true);
				fristButton.setEnabled(true);
				fullScreenButton.setEnabled(true);
				noteButton.setEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gotoPage(int pagenum) {
		if (pagenum < 0) {
			pagenum = 0;
		} else if (pagenum >= pdffile.getNumPages()) {
			pagenum = pdffile.getNumPages() - 1;
		}
		forceGotoPage(pagenum);
	}

	public void forceGotoPage(int pagenum) {
		if (pagenum <= 0) {
			pagenum = 0;
		} else if (pagenum >= pdffile.getNumPages()) {
			pagenum = pdffile.getNumPages() - 1;
		}
		curpage = pagenum;

		// update the page text field
		pageField.setText(String.valueOf(curpage + 1));
		jl.setText("(" + (curpage + 1) + "/" + pdffile.getNumPages() + ")");
		// fetch the page and show it in the appropriate place
		PDFPage pg = pdffile.getPage(pagenum + 1);
		if (jpmain != null) {
			jpmain.removeAll();
		}
		jpmain.add(jp);
		validate();

		if (fspp != null) {//全屏面板
			fspp.showPage(pg);
			fspp.requestFocus();
		} else { //普通面板
			jp.showPage(pg);
			jp.requestFocus();
		}

		// update the thumb panel

		thumbs.pageShown(pagenum);
	}

	public void doClose() {
		if (jp != null) {
			jpmain.removeAll();
		}
		if (thumbs != null) {
			thumbs.stop();
		}
		thumbs = new ThumbPanel(null);
		thumbscroll.getViewport().setView(thumbs);
		pdffile = null;
	}

	public void doPageSetup() {
		PrinterJob pjob = PrinterJob.getPrinterJob();
		pformat = pjob.pageDialog(pformat);
	}

	public void doPrint() {
		PrinterJob pjob = PrinterJob.getPrinterJob();
		if (docName != null && docName.length() != 0) {
			pjob.setJobName(docName);
			Book book = new Book();
			PDFPrintPage pages = new PDFPrintPage(pdffile);
			book.append(pages, pformat, pdffile.getNumPages());
			pjob.setPageable(book);
			if (pjob.printDialog()) {
				new PrintThread(pages, pjob).start();
			}
		} else
			JOptionPane.showMessageDialog(MainFrame.this, "请您选择PDF文档后打印");
	}

	public void valueChanged(TreeSelectionEvent e) {
		if (e.isAddedPath()) {
			OutlineNode node = (OutlineNode) e.getPath().getLastPathComponent();
			if (node == null) {
				return;
			}

			try {
				PDFAction action = node.getAction();
				if (action == null) {
					return;
				}

				if (action instanceof GoToAction) {
					PDFDestination dest = ((GoToAction) action)
							.getDestination();
					if (dest == null) {
						return;
					}

					PDFObject page = dest.getPage();
					if (page == null) {
						return;
					}

					int pageNum = pdffile.getPageNumber(page);
					if (pageNum >= 0) {
						gotoPage(pageNum);
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private final class JPMouseMotionAction extends
			java.awt.event.MouseMotionAdapter {
		public void mouseDragged(java.awt.event.MouseEvent e) {
			if (isDragged) {// 在拖放事件中不断记下和改变位置                
				loc = new Point(jp.getLocation().x + e.getX() - tmp.x, jp
						.getLocation().y
						+ e.getY() - tmp.y);
				jp.setLocation(loc);
			}
		}
	}

	private final class JPMouseAction extends java.awt.event.MouseAdapter {
		public void mouseReleased(java.awt.event.MouseEvent e) {
			isDragged = false;// 鼠标释放后,不在拖动状态,改变鼠标的形状                  
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		public void mousePressed(java.awt.event.MouseEvent e) {
			tmp = new Point(e.getX(), e.getY());// 记处鼠标刚开始按下时的位置             
			isDragged = true;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	class PrintThread extends Thread {//打印线程

		PDFPrintPage ptPages;
		PrinterJob ptPjob;

		public PrintThread(PDFPrintPage pages, PrinterJob pjob) {
			ptPages = pages;
			ptPjob = pjob;
		}

		public void run() {
			try {
				ptPages.show(ptPjob);
				ptPjob.print();
			} catch (PrinterException pe) {
				JOptionPane.showMessageDialog(MainFrame.this,
						"Printing Error: " + pe.getMessage(), "Print Aborted",
						JOptionPane.ERROR_MESSAGE);
			}
			ptPages.hide();
		}
	}

	class SimulateActivity implements Runnable {
		private volatile int current;

		private int target;

		public SimulateActivity(int t) {
			// TODO 自动生成构造函数存根
			current = 0;
			target = t;
		}

		public int getTarget() {
			return target;
		}

		public int getCurrent() {
			return current;
		}

		public void run() {
			try {
				while (current < target) {
					Thread.sleep(100);
					current++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setFullScreenMode(boolean full, boolean force) {
		//	curpage= -1;
		if (full && fullScreen == null) {
			fullScreenAction.setEnabled(false);
			new Thread(new PerformFullScreenMode(force)).start();
			fullScreenButton.setSelected(true);
		} else if (!full && fullScreen != null) {
			fullScreen.close();
			fspp = null;
			fullScreen = null;
			gotoPage(curpage);
			fullScreenButton.setSelected(false);
		}
	}

	class PerformFullScreenMode implements Runnable {

		boolean force;

		public PerformFullScreenMode(boolean forcechoice) {
			force = forcechoice;
		}

		public void run() {
			fspp = new PagePanel();
			fspp.setBackground(Color.CYAN);
			jp.showPage(null);
			fullScreen = new FullScreenWindow(fspp, force);
			fspp.addKeyListener(MainFrame.this);
			gotoPage(curpage);
			fullScreenAction.setEnabled(true);
		}
	}

	public void keyPressed(KeyEvent evt) {
		// TODO 自动生成方法存根
		int code = evt.getKeyCode();
		if (code == KeyEvent.VK_LEFT) {
			doPrev();
		} else if (code == KeyEvent.VK_RIGHT) {
			doNext();
		} else if (code == KeyEvent.VK_UP) {
			doPrev();
		} else if (code == KeyEvent.VK_DOWN) {
			doNext();
		} else if (code == KeyEvent.VK_HOME) {
			doFirst();
		} else if (code == KeyEvent.VK_END) {
			doLast();
		} else if (code == KeyEvent.VK_PAGE_UP) {
			doPrev();
		} else if (code == KeyEvent.VK_PAGE_DOWN) {
			doNext();
		} else if (code == KeyEvent.VK_SPACE) {
			doNext();
		} else if (code == KeyEvent.VK_ESCAPE) {
			setFullScreenMode(false, false);
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO 自动生成方法存根

	}

	class PageBuilder implements Runnable {

		int value = 0;
		long timeout;
		Thread anim;
		static final long TIMEOUT = 500;

		/** add the digit to the page number and start the timeout thread */
		public synchronized void keyTyped(int keyval) {
			value = value * 10 + keyval;
			timeout = System.currentTimeMillis() + TIMEOUT;
			if (anim == null) {
				anim = new Thread(this);
				anim.start();
			}
		}

		/**
		 * waits for the timeout, and if time expires, go to the specified
		 * page number
		 */
		public void run() {
			long now, then;
			synchronized (this) {
				now = System.currentTimeMillis();
				then = timeout;
			}
			while (now < then) {
				try {
					Thread.sleep(timeout - now);
				} catch (InterruptedException ie) {
				}
				synchronized (this) {
					now = System.currentTimeMillis();
					then = timeout;
				}
			}
			synchronized (this) {
				gotoPage(value - 1);
				anim = null;
				value = 0;
			}
		}
	}

	public void keyTyped(KeyEvent evt) {
		// TODO 自动生成方法存根
		char key = evt.getKeyChar();
		if (key >= '0' && key <= '9') {
			int val = key - '0';
			pb.keyTyped(val);
		}
	}
}

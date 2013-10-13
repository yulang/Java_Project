package mainFrame;

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
//import com.wsy.MainFrame.PageBuilder;
//import com.wsy.MainFrame.SimulateActivity;
import com.util.CreatecdIcon;
import com.wsy.MainFrame.JPMouseAction;
import com.wsy.MainFrame.JPMouseMotionAction;


public class MainFrame extends JFrame 
{
	/*
	 * 2012.11.24第一次修改
	 */
	//主要的UI框架
	private static final long serialVersionUID = 1L;
	MainPanel jpmain = new MainPanel();
	//放置主要内容的panel
	PDFPagePanel jp;
	//读取PDF内容的面板
	FullScreenWindow fullScreen;
	//全屏查看的面板
	ThumbPanel thumbs;
	//缩略图面板
	private JScrollPane contentPanel;

	private JScrollPane thumbscroll;

	private JButton nextButton;

	private JButton backButton;

	private JLabel jl;

	private JButton bigButton;
	private JButton fristButton;
	private JButton lastButton;
	
	JScrollPane documentScroller = new JScrollPane();
	
	PDFPagecontroller pc = new PDFPagecontroller();
	
	JTabbedPane tabbedPane;
	JButton smallButton;
	JButton fullScreenButton;
	PageFormat pformat = PrinterJob.getPrinterJob().defaultPage();
	String docName;
	
	private Timer activityMonitor;
//	private SimulateActivity activity;
	PagePanel fspp;
//	PageBuilder pb = new PageBuilder();
	public MainFrame() {
		// TODO 自动生成构造函数存根
		try {
			UIManager
					.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			//自动生成 catch 块
			e.printStackTrace();
		}
		titlesetter a = new titlesetter();
		a.title("PDFReader made by 余浪 11061105");
		//设置标题文字
		sizesetter b = new sizesetter();
		setSize(new Dimension(900, 700));
		contendcontroller c = new contendcontroller();
		c.layer();
		c.add();
		
		thumbs = new ThumbPanel(null);
		locationcontroller location = new locationcontroller();
		//控制页面显示的位置
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
		 * 创建左边的缩位图面板
		 */
		
		
		contentPanel = new JScrollPane();
		jp = new PDFPagePanel();
		jp.addMouseListener(new JPMouseAction());
		jp.addMouseMotionListener(new JPMouseMotionAction());
		jpmain.setLayout(new GridLayout(0, 1));
		contentPanel.setViewportView(jpmain);
		contentPanel.setBounds(270, 63, 600, 600);
		getContentPane().add(contentPanel);
		/*
		 *创建中间的内容面板 
		 */
		
		getContentPane().add(CreateToolBar());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//设置关闭时关闭程序

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - getWidth()) / 2;
		int y = (screen.height - getHeight()) / 2;
		setLocation(x, y);
		/*
		 * 创建工具栏
		 */

}
	
	public JMenuBar CreateMenuBar(){
		//创建上方菜单栏的方法
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0,0,1000,25);
		//设置菜单栏的范围
		//在页面上方显示
		final JMenu MenuItem1 = new JMenu();
		MenuItem1.setText("文件(F)");
		
		menuBar.add(MenuItem1);
		
		//下面设置在“文件”的下拉列表中的选项
		
		final JMenuItem newItemMenuItem1 = new JMenuItem();
		newItemMenuItem1.setText("打开");
		MenuItem1.add(newItemMenuItem1);
		newItemMenuItem1.setToolTipText("Open PDF File");
		//设置提示字符串
		newItemMenuItem1.addActionListener(
new java.awt.event.ActionListener() {
	public void actionPerformed(ActionEvent evt){
		doOpen();
		//调用打开文件的方法
	}
});
		final JMenuItem newItemMenuItem2 = new JMenuItem();
		newItemMenuItem2.setText("页面设置");
		newItemMenuItem1.add(newItemMenuItem2);
		//添加页面设置项
		newItemMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt2) {
				doPageSetup();
				//调用页面设置方法
			}
		});
		final JMenuItem newItemMenuItem3 = new JMenuItem();
		newItemMenuItem3.setText("打印");
		newItemMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt3) {
				doPrint();
				//调用打印方法
			}
		});
		newItemMenuItem1.add(newItemMenuItem3);

		final JMenuItem newItemMenuItem4 = new JMenuItem();
		newItemMenuItem4.setText("退出");
		newItemMenuItem4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt4) {
				doClose();
				System.exit(0);
				//执行关闭
			}
		});
		newItemMenuItem1.add(newItemMenuItem4);
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
	public void gotoPage(int pagenum) {
		if (pagenum < 0) {
			pagenum = 0;
		} else if (pagenum >= pc.pdffile.getNumPages()) {
			pagenum = pc.pdffile.getNumPages() - 1;
		}
		forceGotoPage(pagenum);
	}
	
	public void forceGotoPage(int pagenum) {
		if (pagenum <= 0) {
			pagenum = 0;
		} else if (pagenum >= pc.pdffile.getNumPages()) {
			pagenum = pc.pdffile.getNumPages() - 1;
		}
		pc.curpage = pagenum;

		// update the page text field
		pc.pageField.setText(String.valueOf(pc.curpage + 1));
		jl.setText("(" + (pc.curpage + 1) + "/" + pc.pdffile.getNumPages() + ")");
		// fetch the page and show it in the appropriate place
		PDFPage pg = pc.pdffile.getPage(pagenum + 1);
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
		thumbs.pageShown(pagenum);
		// update the thumb panel
	}
	
	public void doFirst() {
		gotoPage(0);
	}

	public void doNext() {
		gotoPage(pc.curpage + 1);
	}

	public void doPrev() {
		gotoPage(pc.curpage - 1);
	}

	public void doLast() {
		gotoPage(pc.pdffile.getNumPages() - 1);
	}

	public void doFullScreen(boolean force) {
		setFullScreenMode(fullScreen == null, force);
	}

class MainPanel extends JPanel {
}

class PDFPagePanel extends PagePanel{
	
}



class PDFPagecontroller {
	PDFFile pdffile;
	//PDFRender包中获取PDF文档的PDFFile类
	int curpage = -1;
	JTextField pageField;
	//用户填写页码的文本框
}

class outlinecontroller {
	OutlineNode outline = null;
	//获取大纲
	
}

class locationcontroller {
	//控制页面位置以及是否被拖拽
	private Point loc ;
	private Point tmp ;
	private boolean isDragged;
	locationcontroller (){
		loc = null;
		tmp = null;
		isDragged = false;
	}
}

class titlesetter {
	public void title(String s){
		setTitle(s);
	}
}

class sizesetter {
	public void size(Dimension x ){
		setSize(x);
		
	}
}
class contendcontroller{
	public void layer(){
		getContentPane().setLayout(null);
	}
	public void add(){
		getContentPane().add(CreateMenuBar());
	}
	//创建左侧缩略图面板
}
}

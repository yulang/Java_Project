package main;

import util.CreatecdIcon;
import util.Buttoninit;
import util.Fileoperator;
import util.Pagecontroller;
import util.Buttonactivator;
import util.GetPage;
import util.PrintThread;
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
import javax.swing.LookAndFeel;

import note.DateTime;
import note.LeftPane;
import note.MonthPane;
import note.Motto;
import note.NoteBook;
import note.NotePane;
import note.YearMonth;

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


import sun.java.swing.plaf.nimbus.*;
import activity.SimulateActivity;
/*
 * 2012.11.25第一次修改 
 * 2012.11.26第二次修改
 * 2012.12.1 third
 * @author 余浪
 * student number 11061105
 */

/*
 * 整个程序的主面板
 */
public class MainFrame extends JFrame implements PageChangeListener, 
TreeSelectionListener,KeyListener{
	//放置主内容的面板
	JPanel jpmain = new JPanel();
	//放置读取PDF文档内容的面板
	PagePanel jp;
	//放置全屏显示面板
	FullScreenWindow fullScreen;
	//缩略图面板
	ThumbPanel thumbs;
	
	//新建文件面板（带滚动条）
	JScrollPane documentScroller = new JScrollPane();
	//PDFRender包中获取PDF文档的PDFFile类
	PDFFile pdffile;
	//用户填写页码的文本框
	JTextField pageField;
	
	//curpage用于存当前的页码
	int curpage = 0;
	//放置左侧标签按钮的条
	JTabbedPane tabbedPane;
	//用于获取大纲的OutlineNode对象
	OutlineNode outline = null;
	//缩小页面的按钮
	JButton smallButton;
	//设置全屏的按钮
	JButton fullScreenButton;
	//设置页面格式
	PageFormat pformat = PrinterJob.getPrinterJob().defaultPage();
	//存放文件名的字符串
	String docName;
	
	private JScrollPane contentPanel;

	private JScrollPane thumbscroll;

	private JButton nextButton;

	private JButton backButton;

	//用于存储当前页码
	private JLabel jl;

	private JButton bigButton;
	private JButton firstButton;
	private JButton lastButton;
	private JButton noteButton;
	//用于保存最近打开的目录
	private File prevDirChoice;
	
	//监控事件的变量
	private Timer activityMonitor;
	//下面定义
	private SimulateActivity activity;
	PagePanel fspp;
	//页面构造变量，在后面变量
	PageBuilder pb = new PageBuilder();
	
	//用于确定页面位置
	private Point loc = null;
	//用于记录鼠标点击时的位置
	private Point tmp = null;
	//记录页面是不是被拖拽
	private boolean isDragged = false;
	
	/*
	 * mainframe构造函数
	 * 设置基本属性
	 * 创建基本面板
	 */
	public MainFrame(){
		try{
			//设置外观
			UIManager.setLookAndFeel(
//					new sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
					new javax.swing.plaf.metal.MetalLookAndFeel());
		}catch(UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		//设置标题显示文字
		setTitle("SmartReader        @author 余浪                            student number 11061105");
		//设置面板大小
		setSize(new Dimension(900,700));
		//不支持改变界面大小
		this.setResizable(false);
		//设置页面布局
		getContentPane().setLayout(null);
		//为内容面板添加组件
		//CreateMenuBar()为创建工具栏的方法
		//创建工具栏的方法在后面定义
		getContentPane().add(CreateMenuBar());
		/*
		 * 创建左边的缩略图面板
		 */
		//创建缩略图面板
		thumbs = new ThumbPanel(null);
		//分别设置竖直和水平的滚动条
		//竖直方向永远有滚动条
		//水平方向不需要滚动条
		//在下文定义，为私有
		thumbscroll = new JScrollPane(thumbs,
				                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		thumbscroll.getViewport().setView(thumbs);
		//设置背景颜色
		thumbscroll.getViewport().setBackground(Color.gray);
		tabbedPane = new JTabbedPane();
		//为左侧标签条添加图标
		tabbedPane.addTab(null, CreatecdIcon.add("02.gif"),thumbscroll);
		tabbedPane.addTab(null, CreatecdIcon.add("05.gif"),null);
		//设置放置方位
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//把标签条加到面板中
		getContentPane().add(tabbedPane);
		//设置标签条的大小
		tabbedPane.setBounds(0,63,270,600);
		//添加滚动条到面板中
		getContentPane().add(thumbscroll);
		
		/*
		 * 创建中间的内容面板
		 */
		//放置内容的面板
		contentPanel = new JScrollPane();
		jp = new PagePanel();
		//添加鼠标事件监听器
		//JPMouseAction为Adapter子类
		jp.addMouseListener(new JPMouseAction());
		//添加鼠标监听器
		//JPMouseMotionAction为adapter子类
		jp.addMouseMotionListener(new JPMouseMotionAction());
		//设置网格布局
		jpmain.setLayout(new GridLayout(0,1));
		
		contentPanel.setViewportView(jpmain);
		//设置内容面板位置与大小
		contentPanel.setBounds(270,63,600,600);
		//把面板加入到容器中
		getContentPane().add(contentPanel);
		
		/*
		 * 创建工具栏
		 */
		//添加工具栏到界面中
		getContentPane().add(CreateToolBar());
		//设置关闭操作
		//关闭时需要退出整个程序
		//因为这是主程序面板，而不是一个模块
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		/*
		 * 将程序界面放置在屏幕中间
		 */
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - getWidth())/2;
		int y = (screen.height - getHeight())/2;
		//设置位置
		setLocation(x,y);
	}
	
	/*
	 * 创建工具条的方法
	 * 返回一个JMenuBar
	 * 2012.11.26第二次修改
	 * @author 余浪
	 * student number 11061105
	 */
	
	public JMenuBar CreateMenuBar(){
		//声明最后返回的JMenuBar
		final JMenuBar menuBar = new JMenuBar();
		//设置工具栏位置与范围
		menuBar.setBounds(0,0,1000,25);
		//新建一个“文件”的按钮
		final JMenu newItemMenuItem1 = new JMenu();
		//设置按钮显示的文本
		newItemMenuItem1.setText("File(F)");
		
		/*
		 * 设置open选项
		 */
		//将按钮添加到工具条中
		menuBar.add(newItemMenuItem1);
		//新建File按钮下拉菜单的选项
		final JMenuItem newItemMenuItem_1 = new JMenuItem();
		//设置选项的文本
		newItemMenuItem_1.setText("Open(O)");
		//把选项加入到File按钮的下拉菜单中
		newItemMenuItem1.add(newItemMenuItem_1);
		//添加把鼠标放在选项上时显示的提示文字
		newItemMenuItem_1.setToolTipText("Open PDF File");
		//添加事件监听器
		

		//TODO 格式有些奇怪，记得修改 
		newItemMenuItem_1.addActionListener(
				new java.awt.event.ActionListener(){
					//触发事件则调用相应的方法
					public void actionPerformed(ActionEvent evt){
						//调用打开文件方法
		
						doOpen();
					}
				});
		/*
		 * 设置页面设置选项
		 */
		//新建一个在File按钮下拉菜单的选项
		final JMenuItem newItemMenuItem_2 = new JMenuItem();
		//设置选项显示的文本
		newItemMenuItem_2.setText("Page Setup(S)");
		//将选项添加到下拉菜单中
		newItemMenuItem1.add(newItemMenuItem_2);
		//添加把鼠标放在选项上时显示的提示文字
		newItemMenuItem_2.setToolTipText("Set the properties of the page");
		//添加事件监听器
		//相应用户操作调用相应方法
		newItemMenuItem_2.addActionListener(
				new ActionListener(){
					//触发事件则调用相应的方法
					public void actionPerformed(ActionEvent arg0){
						//调用页面设置方法
						//此方法在后面集中定义
						doPageSetup();
					}
				});
		/*
		 * 设置打印选项
		 */
		//新建一个在File按钮下拉菜单的选项
		final JMenuItem newItemMenuItem_3 = new JMenuItem();
		//设置选项显示的文本
		newItemMenuItem_3.setText("Print(P)");
		//将选项添加到下拉菜单中
		newItemMenuItem1.add(newItemMenuItem_3);
		//添加把鼠标放在选项上时显示的提示文字
		newItemMenuItem_3.setToolTipText("Print the file");
		//添加事件监听器
		//相应用户操作调用相应方法
		newItemMenuItem_3.addActionListener(
				new ActionListener(){
					//触发事件则调用相应的方法
					public void actionPerformed(ActionEvent arg1){
						//调用页面设置方法
						//此方法在后面集中定义
						doPrint();
					}
		});
		
		/*
		 * 设置退出选项
		 */
		//新建一个在File按钮下拉菜单的选项
		final JMenuItem newItemMenuItem_4 = new JMenuItem();
		//设置选项显示的文本
		newItemMenuItem_4.setText("Exit(E)");
		//将选项添加到下拉菜单中
		newItemMenuItem1.add(newItemMenuItem_4);
		//添加把鼠标放在选项上时显示的提示文字
		newItemMenuItem_4.setToolTipText("Exit and close the SmartReader");
		//添加事件监听器
		//相应用户操作调用相应方法
		newItemMenuItem_4.addActionListener(
				new ActionListener(){
					//触发事件则调用相应的方法
					public void actionPerformed(ActionEvent arg2){
						//调用页面设置方法
						//此方法在后面集中定义
						doClose();
						//关闭程序
						System.exit(0);
					}
		});
		return menuBar;
	}
	
	/*
	 * 定义动作
	 * 以及动作触发调用的响应方法
	 */
	
	Action firstAction = new AbstractAction(){
		/*
		 * 用户点击首页按钮时的动作
		 */
		
		public void actionPerformed(ActionEvent arg0){
			//跳转到首页的方法
			//后面统一定义
			doFirst();
		}
	};
	
	Action lastAction = new AbstractAction(){
		/*
		 * 用户点击末页按钮时的动作
		 */
		
		public void actionPerformed(ActionEvent arg0){
			//跳转到尾页的方法
			doLast();
		}
	};
	Action nextAction = new AbstractAction(){
		/*
		 * 用户点击下一页按钮时触发的动作
		 */
		
		public void actionPerformed(ActionEvent evt) {
			//下一页的方法
			doNext();
		}
	};
	
	Action prevAction = new AbstractAction(){
		/*
		 * 用户点击上一页时的动作
		 */
		
		public void actionPerformed(ActionEvent evt){
			//上一页的方法
			doPrev();
		}
		
		
	};
	
	/*
	 * 2012.11.26 debug
	 */
	Action BigAction = new AbstractAction(){
		/*
		 * 用户点击放大页面时的动作
		 */
		
		public void actionPerformed(ActionEvent evt){
			PDFPage page = pdffile.getPage(curpage);
			//先获取原本图片的宽和高
			Rectangle rect = new Rectangle(0,0,
					(int) page.getBBox().getWidth(),
					(int) page.getBBox().getHeight());
			
			//产生放大的图片
			Image img = page.getImage(
					//宽放大一倍
					rect.width*2,
					//长放大一倍
					rect.height*2,
					//clip rect
					rect,
					//Imageobserver设置为空
					null,
					//用白色填充背景
					true,
					//block until drawing is done
					true);
			//如果内容面板之前已经有内容，就先清空
			if(jpmain != null)
				jpmain.removeAll();
			
			/*
			 * 清空内容面板之后刷新内容
			 * 替换为放大后的图片
			 * 放大图片由上面的img保存
			 * 同时不要忘记重新添加鼠标事件侦听
			 */
			
			contentPanel.setViewportView(jpmain);
			//再次设置边界
			//在类的较前位置有
			contentPanel.setBounds(230,60,752,600);
			//添加鼠标时间侦听
			//JPMouseAction为Adapter子类
			jpmain.addMouseListener(new JPMouseAction());
			//添加鼠标监听器
			//JPMouseMotionAction为adapter子类
			jpmain.addMouseMotionListener(new JPMouseMotionAction());
			//重新将图片添加进主内容面板
			ImageIcon i = new ImageIcon(img);
			JLabel l = new JLabel(i);
			jpmain.add(l);
			/*
			 * 下为java内置方法
			 * 2012.11.26
			 * @yulang
			 */
			
			//生效
			validate();
			//刷新
			repaint();
		}
	};
	
	Action SmallAction = new AbstractAction(){
		/*
		 * 用户点击缩小页面按钮时的动作
		 * 与BigAction完全类似，不过长宽减半罢了
		 */
		public void actionPerformed(ActionEvent evt){
			PDFPage page = pdffile.getPage(curpage);
			//先获取原本图片的宽和高
			Rectangle rect = new Rectangle(0,0,
					(int) page.getBBox().getWidth(),
					(int) page.getBBox().getHeight());
			
			//产生缩小的图片
			Image img = page.getImage(
					//宽放大一倍
					rect.width/2,
					//长放大一倍
					rect.height/2,
					//clip rect
					rect,
					//Imageobserver设置为空
					null,
					//用白色填充背景
					true,
					//block until drawing is done
					true);
			//如果内容面板之前已经有内容，就先清空
			if(jpmain != null)
				jpmain.removeAll();
			
			/*
			 * 清空内容面板之后刷新内容
			 * 替换为放大后的图片
			 * 缩小图片由上面的img保存
			 * 同时不要忘记重新添加鼠标事件侦听
			 */
			
			contentPanel.setViewportView(jpmain);
			//再次设置边界
			//在类的较前位置有
			contentPanel.setBounds(230,60,600,600);
			//添加鼠标时间侦听
			//JPMouseAction为Adapter子类
			jpmain.addMouseListener(new JPMouseAction());
			//添加鼠标监听器
			//JPMouseMotionAction为adapter子类
			jpmain.addMouseMotionListener(new JPMouseMotionAction());
			//重新将图片添加进主内容面板
			ImageIcon i = new ImageIcon(img);
			JLabel l = new JLabel(i);
			jpmain.add(l);
			/*
			 * 下为java内置方法
			 * 2012.11.26
			 * @yulang
			 */
			
			//生效
			validate();
			//刷新
			repaint();
		}
	};
	
	
	Action fullScreenAction = new AbstractAction(){
		/*
		 * 用户点击全屏按钮时的动作
		 */
		
		public void actionPerformed(ActionEvent evt){
			//全屏显示的方法
			doFullScreen((evt.getModifiers() & ActionEvent.SHIFT_MASK) != 0);
		}
	};
	
	/*
	 * SmartReader的亮点之处
	 * 读书笔记功能
	 */
	
	Action noteAction = new AbstractAction(){
		/*
		 * 用户点击读书笔记按钮时的动作
		 */
		
		public void actionPerformed(ActionEvent evt){
			/*
			 * 打开读书笔记模块的方法
			 * 调用的NoteBook类中留给程序调用的接口方法
			 * public static void note()
			 * 利用此方法可以开启读书笔记模块
			 */
			note();
		}
	};
	
	/*
	 * 下面定义上面响应用户事件时调用的方法
	 * 2012.12.1 debug
	 */
	
	/*
	 * 打开读书笔记模块的方法
	 * 调用的NoteBook类中留给程序调用的接口方法
	 * public static void note()
	 * 利用此方法可以开启读书笔记模块
	 */
	public void note(){
		
		NoteBook.note();
	}
	
	/*
	 * 转到首页的方法
	 */
	public void doFirst(){
		//即转到第0页
		gotoPage(0);
	}
	
	/*
	 * 查看下一页的方法
	 */
	public void doNext(){
		//查看下页，即当前页码加一
		gotoPage(curpage + 1);
		
	}

	/*
	 * 查看前一页的方法
	 */
	
	public void doPrev(){
		//查看前一页，即当前页码减1
		gotoPage(curpage -1);
	}
	
	/*
	 * 转到末页的方法
	 */
	
	public void doLast(){
		//即转到最后一个页码
		//用方法得到文件的总页数
		
		gotoPage(pdffile.getNumPages()-1);
	}
	
	/*
	 * 全屏阅读的方法
	 */
	
	public void doFullScreen(boolean force){
		//调用其他方法完成全屏阅读
		
		setFullScreenMode(fullScreen == null, force);
	}
	
	/*
	 * 创建工具栏的方法
	 * 工具栏位于界面上方
	 * 工具栏存放各个功能按钮，
	 * 实现页码选择等等功能
	 * 返回一个JToolBar对象
	 * 
	 * 2012.12.1 debug
	 * @author 余浪
	 * student number 11061105
	 */
	
	private JToolBar CreateToolBar(){
		//toolbar是最终方法要返回的对象
		JToolBar toolBar = new JToolBar();
		//设置工具栏的位置大小
		toolBar.setBounds(0,23,800,40);
		//设置定死工具栏的位置
		//工具栏不能被移动
		toolBar.setFloatable(false);
		//设置边界
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		/*
		 * 添加跳转首页的按钮
		 */
		firstButton = new JButton(firstAction);
		//调用封装的方法初始化按钮
		Buttoninit.init(firstButton, "转到首页", "first.gif");
		
		//将按钮加入到工具条中
		toolBar.add(firstButton);
		
		/*
		 * 添加查看下页的按钮
		 */
		nextButton = new JButton(nextAction);
		//调用封装的方法初始化按钮
		Buttoninit.init(nextButton, "下一页", "next.gif");
		
		//((Object) nextButton).setHideActionText(true);
		//将按钮加入到工具条中
		toolBar.add(nextButton);
		
		/*
		 * 添加查看上一页的按钮
		 */
		backButton = new JButton(prevAction);
		//调用封装的方法初始化按钮
		Buttoninit.init(backButton, "上一页", "back.gif");
		
		//((Object) backButton).setHideActionText(true);
		//将按钮加入到工具条中
		toolBar.add(backButton);
		
		/*
		 * 添加转到尾页的按钮
		 */
		
		lastButton = new JButton(lastAction);
		//调用封装的方法初始化按钮
		Buttoninit.init(lastButton, "转到尾页", "last.gif");
		//将按钮加入到工具条中
		toolBar.add(lastButton);
		/*
		 * 创建用于填写页码的文本框
		 */
		//初始文本为空
		pageField = new JTextField("", 1);
		//文本框设置为可编辑的
		pageField.setEditable(true);
		//设置文本框边界
		pageField.setBounds(0,0,500,10);
		//添加监听器
		pageField.addKeyListener(this);
		//响应用户事件
		pageField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//先将要转到的页码初始化为-1
				int pagenum = -1;
				try{
					//从页码框读入用户输入的页码
					pagenum = Integer.parseInt(pageField.getText())-1;
				}catch(NumberFormatException nfe){
					
				}
				//如果输入页码超过文件的页码范围，则转到最后一页
				if (pagenum >= pdffile.getNumPages()){
					pagenum = pdffile.getNumPages()-1;
				}
				//输入页码如果是合法数字则跳转
				if (pagenum>=0){
					//如果输入页码不为当前页码则跳转
					if (pagenum != curpage ){
						gotoPage(pagenum);
					}
				}
				//如果页码框输入的是非法页码，则文本框显示当前页码，不执行跳转
				else {
					pageField.setText(String.valueOf(curpage));
				}
			}
		});
		//将页码框添加进工具条
		toolBar.add(pageField);
		jl = new JLabel();
		toolBar.add(jl);
		
		//添加分割线
		//到此页数处理按钮结束
		//下面为下一组功能按钮
		toolBar.addSeparator();
		//为放大按钮添加触发的事件
		//响应的事件在前面定义过
		bigButton = new JButton(BigAction);
		//初始化按钮
		Buttoninit.init(bigButton,"页面放大","big.gif");
		//添加按钮到工具条
		toolBar.add(bigButton);
		
		//为缩小按钮添加触发事件
		smallButton = new JButton(SmallAction);
		//初始化按钮
		Buttoninit.init(smallButton, "页面缩小", "small.gif");
		//添加按钮到工具条
		toolBar.add(smallButton);
		
		//添加分割线
		//到此页面放大缩小功能结束
		toolBar.addSeparator();
		
		/*
		 * 下面为下一组功能按钮
		 */
		
		fullScreenButton = new JButton(fullScreenAction);
		//初始化按钮
		Buttoninit.init(fullScreenButton, "全屏阅读", "fullScreen.gif");
		toolBar.add(fullScreenButton);
		
		/*
		 * 读数笔记按钮
		 */
		//为按钮添加触发的事件
		noteButton = new JButton(noteAction);
		//初始化按钮
		Buttoninit.init(noteButton, "读书笔记", "03.gif");
		//添加到工具条中
		toolBar.add(noteButton);
		//返回创建好的工具条
		return toolBar;
	}
	
	/*
	 * 添加一个文件过滤器
	 * 在选择文件时只显示文件夹和pdf文件
	 * 方便用户使用
	 * 2012.12.2
	 * @author 余浪
	 * student number 11061105
	 */
	
	FileFilter pdfFilter = new FileFilter(){
		//设定显示的文件类型
		public boolean accept(File f){
			//是文件夹或者是pdf文件都显示
			return  f.isDirectory()||f.getName().endsWith(".pdf");
		}
		
		//描述这个过滤器
		public String getDescription(){
			return "选择一个pdf文件";
		}
	};
	
		//用于打开文件的方法
	/*
	 * 打开文件的办法参考百度
	 * 文件接口等类和方法均参考网上
	 */
		public  void doOpen(){
			try{
				//如果已经打开了文件就先关闭原来的文件
				if(jp != null){
					doClose();
				}
				//声明选择文件的对象
				JFileChooser fc = new JFileChooser();
				//先默认到最近打开的文件夹
				fc.setCurrentDirectory(prevDirChoice);
				//不支持同时选择多个文件
				//因为一次只能打开一个文件
				fc.setMultiSelectionEnabled(false);
				//添加文件过滤器
				//只显示pdf文件
				fc.setFileFilter(pdfFilter);
				//弹出选择文件对话框
				int returnVal = fc.showOpenDialog(this);
				//如果选择的文件是合法的
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try{
						//更新最近打开的目录
						prevDirChoice = fc.getSelectedFile();
						RandomAccessFile raf = new RandomAccessFile(prevDirChoice.getAbsolutePath(),"r");
						//找到文件的接口
						FileChannel channel = raf.getChannel();
						ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 
								0, channel.size());
						try{
							pdffile = new PDFFile(buf);
						}catch(IOException ioe){
							//读文件错误则之间返回结束方法
							return;
						}
						docName = prevDirChoice.getName();
						//将阅读器标题栏设为所读取的文件名
						setTitle(docName);
						activityMonitor = new Timer(500, new ActionListener(){
							//触发事件的方法
							public void actionPerformed(ActionEvent arg0){
								int current = activity.getCurrent();
								contentPanel.getVerticalScrollBar().setValue(current);
								if(current == activity.getTarget()){
									activityMonitor.stop();
								}
							}
						});
						for(int i =1; i<10;i++){
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
						//换页监听器
						thumbs.addPageChangeListener(this);
						thumbscroll.getViewport().setView(thumbs);
						//设置背景颜色
						thumbscroll.getViewport().setBackground(Color.gray);
						tabbedPane.setComponentAt(0, thumbscroll);
						try{
							//获取所读文件的大纲
							outline = pdffile.getOutline();
						}catch(IOException ioe){
							if (outline != null){
								if(outline.getChildCount()>0){
									JTree jt = new JTree(outline);
									jt.setRootVisible(false);
									jt.addTreeSelectionListener(this);
									JScrollPane jsp = new JScrollPane(jt);
									tabbedPane.setComponentAt(1,jsp);
								}
							}
						}
					}catch(Exception ioe){
						ioe.printStackTrace();
					}
					//添加进内容面板
					getContentPane().add(tabbedPane);
					//设置边界范围
					tabbedPane.setBounds(0,60,230,607);
					validate();
					jl.setText(GetPage.showPage(curpage, pdffile));
					/*
					 * 打开文件后使所有按钮可用
					 */
					Buttonactivator.activator(backButton);
					Buttonactivator.activator(nextButton);
					Buttonactivator.activator(bigButton);
					Buttonactivator.activator(smallButton);
					Buttonactivator.activator(lastButton);
					Buttonactivator.activator(firstButton);
					Buttonactivator.activator(fullScreenButton);
					Buttonactivator.activator(noteButton);
					
					//设置页码填写区域为可用
					pageField.setEditable(true);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		public void gotoPage(int pagenum){
			pagenum = Pagecontroller.checkPage(pagenum, pdffile);
			forceGotoPage(pagenum);
		}
		
		/*
		 * 处理页面跳转
		 * 并更新相应内容
		 */
		public void forceGotoPage(int pagenum){
			pagenum = Pagecontroller.checkPage(pagenum, pdffile);
			//将当前页码更新为要跳转的页
			curpage = pagenum;
			
			//更新页码文本框
			pageField.setText(String.valueOf(curpage + 1));
			jl.setText(GetPage.showPage(curpage, pdffile));
			
			//获取跳转的页码内容
			PDFPage pg = pdffile.getPage(pagenum+1);
			//若主面板已经有内容，则先清空
			if(jpmain != null){
				jpmain.removeAll();
			}
			
			//更新内容
			jpmain.add(jp);
			validate();
			
			//操作全屏面板
			if(fspp != null){
				fspp.showPage(pg);
				//全屏后全屏面板为输入焦点
				fspp.requestFocus();
			}
			//如果是普通面板的话
			else {
				jp.showPage(pg);
				//普通面板获得输入焦点
				jp.requestFocus();
			}
			
			//更新thumb面板
			thumbs.pageShown(pagenum);
		}
		
		/*
		 * 控制关闭文件的方法
		 * 没有封装因为需要大量涉及mainframe里的对象
		 * 封装则要频繁传参数
		 */
		
		public void doClose(){
			//如果有内容则全部清除
			if (jp != null){
				jpmain.removeAll();
			}
			//不为空则停止后设为空
			if(thumbs!= null){
				thumbs.stop();
			}
			thumbs = new ThumbPanel(null);
			thumbscroll.getViewport().setView(thumbs);
			pdffile = null;
		}
		
		/*
		 * 以下处理pdf文件的很多方法参考自网络
		 */
		/*
		 * 页面设置方法
		 * 调用的方法封装在fileoperator中
		 */
		public void doPageSetup(){
			Fileoperator.setup(pformat);
		}
		
		/*
		 * 打印的方法
		 * 相关类的接口用法参考自网上（例如Book类）
		 */
		
		public void doPrint(){
			PrinterJob pjob = PrinterJob.getPrinterJob();
			//如果要打印的文件名是合法的
			if(docName != null && docName.length()!= 0){
				pjob.setJobName(docName);
				Book book = new Book();
				PDFPrintPage pages = new PDFPrintPage(pdffile);
				book.append(pages, pformat,pdffile.getNumPages());
				pjob.setPageable(book);
				if(pjob.printDialog()){
					new PrintThread(pages,pjob).start();
				}
				
			}
			//如果没有选择合法文件打印，则给一个提示对话框
			else JOptionPane.showMessageDialog(MainFrame.this, "请选择PDF文档后打印");
		}
		
		/*
		 * 此代码段借鉴自已有程序
		 */
		public void valueChanged(TreeSelectionEvent e){
			if(e.isAddedPath()){
				OutlineNode node = (OutlineNode) e.getPath().getLastPathComponent();
				if(node == null){
					return;
				}
				
				try{
					PDFAction action = node.getAction();
					if(action == null){
						return;
					}
					if (action instanceof GoToAction){
						PDFDestination dest = ((GoToAction) action).getDestination();
						if (dest == null){
							return;
						}
						
						PDFObject page = dest.getPage();
						if(page == null){
							return;
						}
						
						int pageNum = pdffile.getPageNumber(page);
						if(pageNum >=0){
							gotoPage(pageNum);
						}
					}
				}catch (IOException ioe){
					ioe.printStackTrace();
				}
		}
		}
		//相应鼠标动作的私有类
		private final class JPMouseMotionAction extends java.awt.event.MouseMotionAdapter{
			//处理鼠标拖放事件
			//在拖放事件中不断记下位置并改变位置
			public void mouseDragged(java.awt.event.MouseEvent e){
				if (isDragged){
					//如果鼠标有拖拽，则不断更新位置
					loc = new Point (jp.getLocation().x + e.getX() - tmp.x,
							jp.getLocation().y+ e.getY() - tmp.y);
					jp.setLocation(loc);
				}
			}
		}
		/*
		 * 控制鼠标操作的私有类
		 * 处理鼠标是否拖动面板
		 * 控制鼠标显示形状
		 * 2012.12.3
		 * @author 余浪
		 * student number 11061105
		 */
		
		private final class JPMouseAction extends java.awt.event.MouseAdapter{
			public void mouseReleased(java.awt.event.MouseEvent e){
				/*
				 * 鼠标释放的状态设置页面拖动状态isDragged为false
				 * 即页面没有被拖动
				 * 同时鼠标形状为正常形状
				 */
				isDragged = false;
				//设置鼠标形状
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mousePressed(java.awt.event.MouseEvent e){
				/*
				 * 鼠标点击的状态设置页面拖动状态isDragged为true
				 * 页面处在被拖动的状态
				 * 相应方法会不断刷新当前页面位置
				 * 同时将鼠标形状设置为手型
				 */
				
				//记录下鼠标按下的位置
				tmp = new Point(e.getX(),e.getY());
				//设置拖拽状态为true
				isDragged = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		
		/*
		 * 打开全屏视图的方法
		 */
		
		public void setFullScreenMode(boolean full, boolean force){
			//full记录的是全屏是否为空
			//传进的参数如果fullscreen为空则full为true
			if(full && fullScreen == null){
				fullScreenAction.setEnabled(false);
				new Thread(new PerformFullScreenMode(force)).start();
				fullScreenButton.setSelected(true);
			}
			//如果原本全屏面板有内容
			//关闭内容清空全屏面板
			else if(!full && fullScreen != null){
				fullScreen.close();
				fspp = null;
				fullScreen = null;
				gotoPage(curpage);
				fullScreenButton.setSelected(false);
			}
		}
		
		/*
		 * 执行全屏视图的方法
		 */
		
		class PerformFullScreenMode implements Runnable {
			boolean force;
			
			public PerformFullScreenMode(boolean forcechoice){
				force = forcechoice;
			}
			
			public void run(){
				fspp = new PagePanel();
				fspp.setBackground(Color.CYAN);
				jp.showPage(null);
				//浅蓝色背景
				fullScreen = new FullScreenWindow(fspp,force);
				fspp.addKeyListener(MainFrame.this);
				gotoPage(curpage);
				fullScreenAction.setEnabled(true);
			}
		}
		
		/*
		 * 下面是处理键盘动作的方法
		 */
		
		public void keyPressed(KeyEvent evt){
			//判断键盘敲进的是什么字符
			//根据键入的字符做出相应的响应
			int code = evt.getKeyCode();
			if (code == KeyEvent.VK_LEFT){
				doPrev();
			} else if(code == KeyEvent.VK_RIGHT) {
				doNext();
			} else if(code == KeyEvent.VK_UP){
				doPrev();
			} else if(code == KeyEvent.VK_DOWN){
				doNext();
			} else if(code == KeyEvent.VK_HOME){
				doFirst();
			} else if(code == KeyEvent.VK_END){
				doLast();
			} else if(code == KeyEvent.VK_PAGE_UP){
				doPrev();
			} else if(code == KeyEvent.VK_PAGE_DOWN){
				doNext();
			} else if(code == KeyEvent.VK_SPACE){
				doNext();
			} else if(code ==KeyEvent.VK_ESCAPE){
				//esc可以退出全屏视图
				setFullScreenMode(false,false);
			}
		}
		
		/*
		 * 实现接口规定的方法
		 * 没有实际内容
		 */
		
		public void keyReleased(KeyEvent arg0){
			return;
		}
		
		public void keyTyped(KeyEvent evt){
			char key = evt.getKeyChar();
			if (key>='0'&&key<='9'){
				//将字符转化为数字
				int val = key -'0';
				pb.keyTyped(val);
			}
		}
		
		/*
		 * 用于创建页面
		 * 参考网上代码
		 */
		public class PageBuilder implements Runnable{
			int value =0;
			long timeout;
			Thread anim;
			static final long TIMEOUT = 500;
			
			// add the digit to the page number and start the timeout thread 
			
			public synchronized void keyTyped(int keyval){
				value = value*10 + keyval;
				timeout = System.currentTimeMillis()+TIMEOUT;
				if (anim == null){
					anim = new Thread(this);
					anim.start();
				}
			}
			
			/*
			 * waits for the timeout, and if time expires, go to the specified
			 * page number
			 */
			
			public void run(){
				long now,then;
				synchronized(this){
					now = System.currentTimeMillis();
					then = timeout;
				}
				
				while(now<then){
					try{
						Thread.sleep(timeout - now);
					}catch(InterruptedException ie){
						
					}
					synchronized(this){
						now = System.currentTimeMillis();
						then = timeout;
					}
				}
				synchronized(this){
					gotoPage(value - 1);
					anim = null;
					value = 0;
				}
			}

		}


	
	/*
	 * 主程序的入口
	 */
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	

}

package note;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
//import javax.swing.JComponent;
import javax.swing.JButton; 
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
//import javax.swing.JWindow;
import javax.swing.JTree; 
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
//读书笔记模块的入口类
public class NoteBook extends JFrame implements ChangeListener,MouseListener{
	/*
	 * 读书笔记功能的主框架类
	 * 进行基本设置并提供整个程序调用读书笔记功能的接口方法
	 * 2012.11.26
	 * @ author 余浪
	 * student number 11061105
	 */
	int year, month, day;
	Calendar c;
	int first,days;
	LeftPane lp;
	NotePane np;
	public NoteBook(){
		//设置标题栏文字
		super("我的读书笔记 v1.0           @author 余浪            student number 11061105");
		Container con = getContentPane();
		//初始化日历及各个部件
		//此方法在下面定义
		bookInit();
		//加入响应影响日期的事件监听器
		//此方法在下面定义
		addEvent();
		//向容器中添加组件
		con.add(lp,"West");
		con.add(np);
		this.setBounds(150,250,630,400);
		//不支持改变大小
		this.setResizable(false);
		this.setVisible(true);
	}
	/*
	 * 初始化面板及日期的方法
	 */
	void bookInit(){
		c = Calendar.getInstance();
		lp = new LeftPane();
		np = new NotePane();
		//从DateTime方法获取年月日
		year = DateTime.getYear();
		month = DateTime.getMonth();
		day = DateTime.getDay();
	}
	/*
	 * 此方法加上影响日期的事件侦听
	 * 添加微调器，日期和文本框
	 * 微调器的数值改变事件名为ChangeEvent
	 */
	void addEvent(){
		//添加事件监听
		lp.ym.showMonth.addChangeListener(this);
		lp.ym.showYear.addChangeListener(this);
		for(int i=1; i<42;i++)
			MonthPane.showDay[i].addMouseListener(this);
	}
	
	//微调器的事件处理
	public void stateChanged(ChangeEvent e){
		int y = year;
		//m存年份
		//n存月份
		String m = lp.ym.showYear.getValue().toString();
		String n = lp.ym.showMonth.getValue().toString();
		year = Integer.parseInt(m);
		month = Integer.parseInt(n);
		//下翻到下一年
		//月份变为1，年份加一
		if(month>12){
			year+=1;
			month = 1;
			lp.ym.showMonth.setValue(new Integer(1));
			lp.ym.showYear.setValue(new Integer(y+1));
		}
		//上翻到上一年
		//月份变为12，年份减1
		else if(month<1){
			year-=1;
			month=12;
			lp.ym.showMonth.setValue(new Integer(12));
			lp.ym.showYear.setValue(new Integer(y-1));
		}
		lp.mp.arrangeNum(year, month);
		//刷新面板
		noteBookRefresh();
				
	}
	/*
	 * 年，月，日任何一个数据改变，均需刷新读书笔记
	 * 即修改月历，日期显示和日记
	 */
	void noteBookRefresh(){
		lp.mp.arrangeNum(year, month);
		np.setDateInfo(year, month, day);
		np.refreshContent(year, month, day);
		
		
		
		
		
		
		
	}
	/*
	 * 相应鼠标点击动作
	 */
	public void mouseClicked(MouseEvent e){
		JTextField source = (JTextField) e.getSource();
		try{
			day = Integer.parseInt(source.getText());
			noteBookRefresh();
		}catch(Exception ee){}
	}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	//读书笔记功能的入口
	public static void note(){
		new NoteBook();
	}

}

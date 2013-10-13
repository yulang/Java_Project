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
public class MonthPane extends JPanel{
	/*
	 * 用于实现日历的类
	 * 在调整月份的时候左边的日历显示不同的月历
	 */
	//用来显示当月的日期
		//不可修改的文本区域，显示月历中的日期
		static JTextField showDay[];
		//用于存储对应月有多少天，以及对应月的一号在星期几的变量
		int first,days;
		YearMonth ym;
		String[] week;
		//用于显示周几的JLabel
		JLabel[] title;
		int year,month,day;
		//生成月历的构造方法
		MonthPane(){
			//采用网格布局
			super(new GridLayout(7,7,3,3));
			//初始化周几，标题等等
			//方法在后面定义
			mcInit();
			for(int i=0;i<7;i++)
				add(title[i]);
			for(int i=0;i<42;i++)
				add(showDay[i]);
			//得到一个月的天数等信息
			//在后面定义
			arrangeNum(year,month);
			
		}
		//初始化相关礼拜几等JLabel及其他信息的方法
		void mcInit(){
			//月日及天数从DateTime方法获取
			year = DateTime.getYear();
			month = DateTime.getMonth();
			day = DateTime.getDay();
			//设置星期
			String week[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
			title = new JLabel[7];
			//循环初始化用于显示周几的JLabel
			//设置布局
			for(int j=0;j<7;j++){
				title[j] = new JLabel();
				title[j].setText(week[j]);
				title[j].setBorder(BorderFactory.createEmptyBorder());
				title[j].setFont(new Font("",1,18));
			}
			//为周末设置不同颜色
			//区别工作日及周末
			title[0].setForeground(Color.red);
			title[6].setForeground(Color.blue);
			showDay = new JTextField[42];
			for(int i =0; i<42; i++){
				showDay[i] = new JTextField();
				//设置格式
				showDay[i].setFont(new Font("",0,14));
				//日历的日期不能被修改
				//应该由DateTime方法获得日期后自动生成
				showDay[i].setEditable(false);
			}
		}
		/*
		 * 获得对应月天数及设置日期显示的方法
		 * 调用DateTime方法来得到具体每个月的月历
		 */
		public void arrangeNum(int year, int month){
			days = DateTime.getMonDay(year, month);
			Calendar c = Calendar.getInstance();
			c.set(year,month-1,1);
			//找到本月第一天在星期几
			first = c.get(Calendar.DAY_OF_WEEK)-1;
			//从第一天对应的星期数开始初始化本月的月历
			for(int i=first, n=1;i<first + days;i++,n++){
				showDay[i].setText(""+n);
				//对“今天”进行特殊显示
				if(n==day){
					showDay[i].setForeground(Color.green);
					showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));
				}
				//对其他的天进行普通设置
				else{
					showDay[i].setForeground(Color.black);
					showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12));
				}
			}
			//对本月没有的天数的文本框进行设置
			//对本月最后一天以后的文本框设置为空
			for(int i= days+first; i<42;i++)
				showDay[i].setText("");
			//对本月第一天以前的文本框设置为空
			for(int i= first -1;i>=0;i--)
				showDay[i].setText("");
		}

}

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
public class NotePane extends JPanel implements ActionListener{
	/*
	 * 用于生成读书笔记部分的类
	 * 实现读书笔记的导入导出删除功能
	 * 利用JPanel实现，分布在界面右侧
	 */
	//年月日将会在读书笔记区域的上访显示
		private int year, month, day;
		//note用于书写读书笔记并操作
		private JTextArea note;
		//实现存储删除到处功能的按钮
		private JButton save, delete, export;
		//用于关联读书笔记与其日期的hash表
		private Hashtable table;
		//用于显示日期的JLabel
		//用后面定义的setDateInfor方法设置显示的日期
		private JLabel dateInfo;
		//文件对象，用于保存删除导出等操作。
		private File file;
		private JPanel buttonpane;
		//note的构造方法，用于初始化对象并设置组件位置
		NotePane(){
			//采用边框布局
			super(new BorderLayout());
			//初始化读书笔记区域的方法，在后面定义
			noteInit();
			//初始化按钮的方法，在后面定义
			//初始化保存，删除，导出按钮并设置
			buttonInit();
			//添加按钮事件，用于监听
			addEvent();
			//将组件添加到容器中
			//设置组件的分布位置
			add(dateInfo,"North");
			//讲输入文本的区域加入容器
			add(new JScrollPane(note));
			//设置功能按钮在组件中的位置
			add(buttonpane,"South");
		}
		/*
		 * 2012.11.29 debug
		 */
		
		//note的初始化方法，进行基本设置
		//同时实现读书笔记和file变量的关联，使得可以进行保存删除导出操作
		private void noteInit(){
			//获取日期，用于显示在文本框上方
			year = DateTime.getYear();
			month = DateTime.getMonth();
			day = DateTime.getDay();
			//替换补充语句，进行颜色和字体设置
			dateInfo = new JLabel(year + "年" + month + "月" + day +"日",JLabel.CENTER);
			table = new Hashtable();
			//用于操作的读书笔记file
			file = new File("Note.txt");
			//初始化file类
			initFile();
			//声明一个文本区域用于读书笔记使用
			note = new JTextArea();
			//设置字体字号
			note.setFont(new Font("",0,14));
			//设置滚动条
			note.setLineWrap(true);
		}
		/*
		 * 初始化按钮对象的方法
		 * 设置文本并且添加到容器中
		 */
		private void buttonInit(){
			// TODO
			//创建按钮
			save = new JButton("保存笔记");
			delete = new JButton("删除笔记");
			export = new JButton("导出笔记");
			//将按钮加入到按钮面板
			buttonpane = new JPanel();
			buttonpane.add(save);
			buttonpane.add(delete);
			buttonpane.add(export);
		}
		/*
		 * 用于操作文件对象
		 */
		private void initFile(){
			// TODO
			//判断文件是否存在
			if(!file.exists()){
				try{
					//输出流
					FileOutputStream out = new FileOutputStream(file);
					ObjectOutputStream objectOut = new ObjectOutputStream(out);
					objectOut.writeObject(table);
					objectOut.close();
					out.close();
				}catch(IOException e){}
			}
		}
		
		/*
		 * 2012.11.26第二次修改
		 * 添加按钮的事件侦听方法
		 */
		private void addEvent(){
			//为保存，删除，导出按钮设置事件监听器
			save.addActionListener(this);
			delete.addActionListener(this);
			export.addActionListener(this);
		}
		/*
		 * 判断触发的事件是什么的方法
		 * 并调用相应方法相应用户操作
		 * 调用的方法在后面定义
		 */
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == save)
				//保存相应日期的读书笔记
				save(year,month,day);
			else if(e.getSource() == delete)
				//删除相应日期的读数笔记
				delete(year,month,day);
			else if(e.getSource() == export)
				//导出相应日期的读书笔记
				export();
		}
		/*
		 * 用于设置显示在文本框上面的日期信息的方法
		 */
		public void setDateInfo(int y, int m, int d){
			String s = y+"年"+m+"月"+d+"日";
			dateInfo.setText(s);
			year = y;
			month = m;
			day = d;
		}
		/*
		 * 用于获取日期的方法
		 * 返回一个格式为year/month/day的字符串
		 * 在之后对读书笔记的操作方法中会被反复用到
		 */
		public String getDateKey(){
			//为使日期格式统一，对于月份或者日期小于两位数的情况，单独判断，并在返回的字符串中填充0
			String s = ""+year;
			if(month<10) s+="/0"+month;
			else s+="/"+month;
			if(day<10) s+="/0"+day;
			else s+="/"+day;
			return s;
		}
		/*
		 * 更新文本框内容的方法
		 * 2012.11.30优化
		 */
		public void refreshContent(int year, int month, int day){
			//调用之前定义的方法，得到当前日期的字符串
			String key = this.getDateKey();
			//读取输入流（如果有的话）
			//table用于将读书笔记内容与其日期关联起来存放，并将相应日期的内容写入到note中（如果有的话）
			try{
				FileInputStream in1 = new FileInputStream(file);
				ObjectInputStream in2 = new ObjectInputStream(in1);
				//讲文本框读入的输入流写入table中
				table = (Hashtable) in2.readObject();
				in1.close();
				in2.close();
			}catch(Exception e){}
			
			//如果读入的有字符的话，就将字符写入读书笔记的文本框
			//如果没有，就继续保持文本框不添加任何字符
			if(table.containsKey(key))
				note.setText(table.get(key)+"");
			else note.setText("");
		}
		/*
		 * 下面来定义actionPerformed中调用的相应方法
		 * 用来相应用户操作
		 * 主要为保存日志，删除日志，导出日志
		 */
		/*
		 * 用来保存已从文本框写入的读书笔记
		 */
		public void save(int year, int month, int day){
			//用来保存输入进的字符
			String logContent = note.getText();
			//用来记录读书笔记所在日期的字符串
			String key = this.getDateKey();
			//保存读书笔记
			try{
				FileInputStream in1 = new FileInputStream(file);
				ObjectInputStream in2 = new ObjectInputStream(in1);
				table = (Hashtable) in2.readObject();
				in1.close();
				in2.close();
				table.put(key, logContent);
				FileOutputStream out = new FileOutputStream(file);
				ObjectOutputStream ObjectOut = new ObjectOutputStream(out);
				ObjectOut.writeObject(table);
				ObjectOut.close();
				out.close();
				
			}catch(Exception e){}
			//用于返回给用户一个成功保存的信息
			String message = year+"年"+month+"月"+day+"日"+"的读书笔记已经保存";
			//弹出一个对话框提示保存成功的信息
			JOptionPane.showMessageDialog(this, message);
		}
		/*
		 * 用于实现删除操作的方法
		 */
		
		public void delete(int year, int month, int day){
			String key = this.getDateKey();
			//如果对应日期存在读书笔记
			if(table.containsKey(key)){
				//向用户提示确认信息，避免误操作
				String message = "您确定要删除"+year+"年"+month+"月"+day+"日"+"的读书笔记吗？";
				//用JOptionPane弹出确认信息对话框
				if(JOptionPane.showConfirmDialog(this,message)==0){
					//确认要删除，则执行删除操作
					//清除Hash表中对应日期的内容
					table.remove(key);
					try{
				        FileOutputStream out=new FileOutputStream(file);
				        ObjectOutputStream objectOut=new ObjectOutputStream(out);
				        objectOut.writeObject(table);
				        objectOut.close();
				        out.close();
				        //清空相应的文本框内容
				        note.setText("");
					}catch(Exception e){}
				}
			}
				//如果要删除的日期根本就没有读书笔记存在，则不必执行确认等操作
				//直接返回给用户一个没有相应日期的读书笔记的提示
			else{
					String message1 = year+"年"+month+"月"+day+"日"+"没有读书笔记可删除";
					JOptionPane.showMessageDialog(this, message1,"提示",JOptionPane.WARNING_MESSAGE);
				}
		}
		/*
		 * 导出文件
		 * 将所有读书笔记到处到一个文本文件
		 */
		public void export(){
			File m;
			//选择要保存的文件
			JFileChooser n = new JFileChooser();
			n.showSaveDialog(null);
			m = n.getSelectedFile();
			try{
				FileInputStream in1 = new FileInputStream(file);
				ObjectInputStream in2 = new ObjectInputStream(in1);
				table = (Hashtable) in2.readObject();
				in1.close();
				in2.close();
				
			}catch(Exception e){}
			TreeMap s = new TreeMap(table);
			Set maping = s.entrySet();
			//如果要导出的文件已经存在，就删掉
			if(m.exists()){
				m.delete();
			}
			else {
				//如果要到处的文件不存在，则新建一个文件
				try{
					m.createNewFile();
				}catch(Exception e){}
			}
			for(Iterator i = maping.iterator();i.hasNext();)
			{
				Map.Entry me = (Map.Entry) i.next();
				//导出读书笔记
				try{
					FileOutputStream out = new FileOutputStream(m,true);
					BufferedOutputStream bout = new BufferedOutputStream(out);
					DataOutputStream dout = new DataOutputStream(bout);
					dout.writeUTF(me.getKey()+"\r\n\t"+me.getValue()+"\r\n");
					dout.close();
				}catch(Exception e){}
			}
		}

}

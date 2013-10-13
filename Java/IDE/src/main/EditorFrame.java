package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.crazyit.editor.AddFrame;
import org.crazyit.editor.FileChooser;
import org.crazyit.editor.IFrameListener;
import org.crazyit.editor.commons.EditFile;
import org.crazyit.editor.commons.WorkSpace;
import org.crazyit.editor.handler.run.JavaRunHandler;
import org.crazyit.editor.handler.save.SaveMediator;
import org.crazyit.editor.tree.TreeCreator;
public class EditorFrame extends JFrame {
	public EditorFrame(String title){
		super(title);
		pack();
	}
	private JTabbedPane tabPane;// 标题栏
	private JDesktopPane desk;//创建可以存放很多个文档的容器
	private Box box;//放置界面
	private JSplitPane editorSplitPane;//分割编辑区和文件区
	private JScrollPane infoPane;
	private JTextArea infoArea;
	private JScrollPane treePane;
    private JSplitPane mainSplitPane;
	private JTree tree;
	private JMenuBar menuBar;
	private JMenu editMenu;
	private JMenu fileMenu;
	private JToolBar toolBar;
	private WorkSpace workSpace;
	private TreeCreator treeCreator;
	
	//创建必要的组件，在之后进行设置与初始化。
	private AddFrame addFrame;
	
	//文件选择器
	private FileChooser fileChooser;
	
	//当前正在编辑的文件对象
	private EditFile currentFile;
	
	//窗口监听器
	private IFrameListener iframeListener;
	
	//打开文件的集合
	private List<EditFile> openFiles = new ArrayList<EditFile>();

	//中介者对象
	private SaveMediator saveMediator;
	
	//运行class文件的处理类
	private JavaRunHandler runHandler;
	public void initializeFrame (){//initialize the UI Frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );
		desk = new JDesktopPane();
		box = new Box(BoxLayout.Y_AXIS);
		editorSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, box, infoPane);
		infoArea = new JTextArea("", 5, 50);
		//创建信息显示区的文本域
		infoPane = new JScrollPane(infoArea);
		//将infoArea文本域作为组件放到infoPane中
		infoArea.setEditable(false);
		//信息区不可编辑
		desk.setBackground(Color.GRAY);
		//设置desk的背景颜色为灰色
		box.add(tabPane);
		box.add(desk);
		//为界面添加相应组件
		editorSplitPane.setDividerSize(3);
		editorSplitPane.setDividerLocation(500);
		add(editorSplitPane);
		pack();//调整JFrame大小。
	}

}

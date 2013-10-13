package source;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.*;

public class MainFrame extends JFrame implements ActionListener,ItemListener
{	
	//定义锁定状态变量
	private String lockState="";
	
	//定义背景颜色对象
	private Color bgColor=new Color(201,237,201);
	//定义字体颜色对象
	private Color foreColor=new Color(61,120,38);
	
	//定义字体对象
	private Font font=new Font("宋体",Font.PLAIN,15);
	
//	*******************声明菜单组件***************
	
	//菜单栏
	private JMenuBar menubar=new JMenuBar();
	//菜单
	private JMenu systemMenu=new JMenu("系统(S)");
	private JMenu helpMenu=new JMenu("帮助(H)");
	//系统菜单项
	private JMenuItem alterPasswordItem=new JMenuItem("修改密码(N)",'N');
	private JMenuItem delUserItem=new JMenuItem("删除用户(D)",'D');
	private JMenuItem lockItem=new JMenuItem("锁定(L)",'L');
	private JMenuItem cancelItem=new JMenuItem("注销(W)",'W');
	private JMenuItem exitItem=new JMenuItem("退出(X)",'X');
	//帮助菜单项
	private JMenuItem helpItem=new JMenuItem("帮助(H)",'H');
	private JMenuItem aboutItem=new JMenuItem("关于(A)",'A');
	
	
	private String user=null;//当前用户的名字	
	private String perNameBefor=null;//编辑的时候之前的名字		
	private String perGroupBefor=null;//编辑的时候修改之前的分组			
	private boolean searchByName=true;//true则默认为按姓名查找	
	private boolean isInsert=false;//是否为添加默认为否	
	Image image=Toolkit.getDefaultToolkit().getImage("images\\txl_2.jpg");//得到图标对象
	Icon icon = new ImageIcon(image);	
	private JPanel jps=new JPanel();//界面上半部分的JPanel容器		
	private JButton jba=new JButton("添加");
	private JButton jbs=new JButton("查找");
	private JTextField jtfs=new JTextField();//按给出信息查找联系人信息
	//选择查找方式的单选按钮
	private JRadioButton jrbxm=new JRadioButton("按姓名查找",true);
	private JRadioButton jrbbh=new JRadioButton("按编号查找");
	private ButtonGroup bg=new ButtonGroup();//单选按钮组		
	private JPanel jpbr=new JPanel();//单选按钮面板		
	//界面左下的树 创建树模型 指定节点"联系人"为根节点
	DefaultMutableTreeNode root=
						new DefaultMutableTreeNode(new NodeValue("联系人",0));
	DefaultTreeModel dtm=new DefaultTreeModel(root);
	private JTree jtz=new JTree();//界面下半部分左边的JTree  		
	private JScrollPane jspz=new JScrollPane(jtz);//JTree的滚动条	
	private DefaultTreeCellRenderer dtcr=new DefaultTreeCellRenderer();//树节点的绘制器		
	private JPanel jpy=new JPanel();//界面下半部分右边界面，布局管理器为卡片布局	
	private JPanel jpyInfo=new JPanel();//右侧显示个人信息的面板	
	//界面下半部分右边的JPanel容器的个人信息栏目里的控件	
	private JLabel[] jlInfo={new JLabel("用户编号:"),new JLabel("姓名:"),
							 new JLabel("性别:"),new JLabel("年龄:"),
							 new JLabel("电话号码:"),new JLabel("Email:"),
							 new JLabel("所属组:"),new JLabel("更改照片:"),
							 new JLabel("邮编:"),new JLabel("地址:"),
							 new JLabel("添加相片"),new JLabel("")};
	private JButton[] jbInfo={new JButton("编辑"),new JButton("保存"),
							  new JButton("删除"),new JButton("浏览"),
							  new JButton("添加分组"),new JButton("删除分组"),
							  new JButton("浏览"),new JButton("上传"),
							  new JButton("删除")};
	//初始默认的一些分组
	private String[] str={"朋友","同事","家庭","重要人士","其他"};
	private JComboBox jcb=new JComboBox(str);//分组下拉列表控件
	private JLabel jlPhoto=new JLabel();//显示图像的JLabel控件
	private JTextField[] jtfInfo=new JTextField[10];	
	private JTextField jtfPhoto=new JTextField();//添加照片到相册的路径	
	private JFileChooser jfcPic=new JFileChooser("f:\\");//上传图像的文件选择器	
	private JFileChooser jfcPho=new JFileChooser("f:\\");//上传照片的文件选择器		
	//性别部分
	private JRadioButton jrbMale=new JRadioButton("男",true);
	private JRadioButton jrbFemale=new JRadioButton("女");
	private ButtonGroup bgGender=new ButtonGroup();	
	private JPanel jpGender=new JPanel();//单选按钮面板	
	private JPanel jpyview=new JPanel();//右侧显示多幅照片的面板	
	private JScrollPane jspyview=new JScrollPane(jpyview);//滚动条	
	private JLabel jlDetail=new JLabel();//右侧显示一幅图片的标签	
	private JScrollPane jspydetail=new JScrollPane(jlDetail);//显示一幅图片标签的滚动条
	private JLabel jlNoPic=new JLabel("您还没有为该联系人添加照片");//没有照片的显示JLabel	
	//图片加载进度条部分
	private JPanel jpProgress=new JPanel();//右侧显示图片加载进度的面板
	private JLabel jlProgress=new JLabel("预览图片加载中.....");
	private JProgressBar jpb=new JProgressBar(JProgressBar.HORIZONTAL,0,100);
	//选中不同树节点时的提示信息部分
	private JLabel jlRoot=new JLabel(icon,JLabel.LEFT);
	private JLabel jlGroup=new JLabel();//分组节点的JLabel
	private CardLayout cl=new CardLayout();//创建卡片布局管理器
	private JLabel[] jla=null;//照片缓冲数组	
	
	//分割窗口部分

	//垂直分割（水平线分割）窗口对象
	private JSplitPane jspOuter=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);

	//对一次分割后的上半部分进行二次垂直分割（水平线分割）对象
	private JSplitPane jspInner1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,menubar,jps);
	//对一次分割后的下半部分进行二次水平分割（垂直线线分割）对象
	private JSplitPane jspInner2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	
	//系统托盘部分						
	private PopupMenu popup=new PopupMenu();	
	private SystemTray tray;     //定义SystemTray成员变量	
	private TrayIcon trayIcon;   //定义TrayIcon成员变量

	private MenuItem lockAndUnlockMainFrame=new MenuItem("锁   定");//定义菜单项
	private MenuItem waitUser=new MenuItem("注   销");//定义菜单项
	private MenuItem showMainFrame=new MenuItem("打开主面板");//定义菜单项
	private MenuItem currently=new MenuItem("显示当前用户");//定义菜单项
	private MenuItem exit=new MenuItem("退出系统");//定义菜单项
	
	//主类构造函数
	public MainFrame(String user)
	{
		//设置用户名
		this.user=user;
		//初始化菜单系统
		this.initMenu();
		//界面上半部分的搭建		
		this.initJps();
		//初始化信息面板	
		this.initInfo();
		//初始化卡片布局的面板		
		this.initJpy();
		//添加系统托盘
		this.initTray();
		//初始化树
		this.initTree();

		//设置主窗体的图标、标题、大小以及可见性
		Image image=Toolkit.getDefaultToolkit().getImage("images\\link.png");//得到图标对象		
		this.setIconImage(image);		
		this.setTitle(user+"的通讯录");	
		this.setSize(650,550);
		//获取屏幕尺寸
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=screenSize.width;
		int srceenHeight=screenSize.height;
		//设置窗口居于屏幕中央
		setLocation((screenWidth-getWidth())/2,(srceenHeight-getHeight())/2);
		
		jtz.addTreeSelectionListener(//为树节点添加事件监听器
		new TreeSelectionListener()
		{
			@Override
			public void valueChanged(TreeSelectionEvent e)
			{//重写valueChanged方法
				DefaultMutableTreeNode cdmtn=//得到选中节点对象
				(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
				NodeValue cnv=(NodeValue)cdmtn.getUserObject();//得到自定义节点对象
				if(cnv.classCode==0)
				{//选中节点是根节点时							
					cl.show(jpy,"root");
				}
				else if(cnv.classCode==1)
				{//选中节点是分组节点时															
					String group=cnv.toString();
					jlGroup.setText(group);
					cl.show(jpy,"group");
				}
				else if(cnv.classCode==2)
				{//选中节点是某一联系人节点时							
					String sql="SELECT pid,pname,pgender,page,pnumber,pemail,pgroup,ppostalcode,"+
			"padress FROM ContactInfo WHERE UserName='"+MainFrame.this.user+"'AND pname='"+cnv.toString()+"'";														
					setInfo(DBOperate.getPerInfo(sql));//从数据库得到此联系人信息并设置到信息面板									
					cl.show(jpy,"Info");
				}
				else if(cnv.classCode==3)
				{//相册预览					
					jpyview.removeAll();//清空相册预览面板
					cl.show(jpy,"tpyl");//显示相册预览面板																		
					viewPic(cdmtn);//预览相册																				
				}
				else if(cnv.classCode==4)
				{//图片明细
					cl.show(jpy,"tpmx");//显示图片明细面板
					NodeValue pnv=//得到选中照片的自定义节点对象
					(NodeValue)((DefaultMutableTreeNode)cdmtn).getUserObject();	
					detailPic(pnv.value);//点击某一张图片
				}
			}
		});			

		//设置垂直分割窗体的位置
		jspOuter.setDividerLocation(80);

		//设置窗体被垂直分割后上半部分的组件(这里参数为再次进行垂直分割的对象)
		jspOuter.setTopComponent(jspInner1);
		//设置窗体被垂直分割后下半部分的组件(这里参数为再次进行水平分割的对象)
		jspOuter.setBottomComponent(jspInner2);

		//设置水平分割窗体的位置
		jspInner1.setDividerLocation(26);
		jspInner2.setDividerLocation(150);
		//设置垂直分割窗体的宽度
		jspOuter.setDividerSize(0);
		//设置水平分割窗体的宽度
		jspInner2.setDividerSize(4);
		//将分割窗体添加到主窗体
		this.add(jspOuter);
		
		//设置窗体关闭按钮执行的动作
		this.addWindowListener(
					new WindowAdapter()
					{
						public void WindowClosing(WindowEvent e)
						{
							//将窗体隐藏
							MainFrame.this.setVisible(false);
						}
					}
					);
		//显示窗口
		this.setVisible(true);
	}

	//初始化菜单系统方法
	public void initMenu()
	{
		//设置菜单快捷键
		systemMenu.setMnemonic('S');
		helpMenu.setMnemonic('H');
		
		//设置加速键
		alterPasswordItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));     //Ctrl+N
		delUserItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK));           //Ctrl+D
		lockItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK));              //Ctrl+L
		cancelItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_MASK));            //Ctrl+W
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));              //Ctrl+X

		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));              //Ctrl+H
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.ALT_MASK));              //Alt+A
		
		//为每个菜单项注册事件监听器
		alterPasswordItem.addActionListener(this);
		delUserItem.addActionListener(this);
		lockItem.addActionListener(this);
		cancelItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		helpItem.addActionListener(this);
		aboutItem.addActionListener(this);
		
		//为每个菜单栏、菜单、菜单项设置背景色
		menubar.setBackground(bgColor);

		systemMenu.setBackground(bgColor);
		helpMenu.setBackground(bgColor);
		
		alterPasswordItem.setBackground(bgColor);
		delUserItem.setBackground(bgColor);
		lockItem.setBackground(bgColor);
		cancelItem.setBackground(bgColor);
		exitItem.setBackground(bgColor);
		
		helpItem.setBackground(bgColor);
		aboutItem.setBackground(bgColor);

		//为每个菜单栏、菜单、菜单项设置字体颜色
		menubar.setForeground(foreColor);

		systemMenu.setForeground(foreColor);
		helpMenu.setForeground(foreColor);
		
		alterPasswordItem.setForeground(foreColor);
		delUserItem.setForeground(foreColor);
		lockItem.setForeground(foreColor);
		cancelItem.setForeground(foreColor);
		exitItem.setForeground(foreColor);
		
		helpItem.setForeground(foreColor);
		aboutItem.setForeground(foreColor);
		
		//为每个菜单栏、菜单、菜单项设置字体
		Font menuFont=new Font("宋体",Font.PLAIN,13);
		menubar.setFont(menuFont);

		systemMenu.setFont(menuFont);
		helpMenu.setFont(menuFont);
		
		alterPasswordItem.setFont(menuFont);
		delUserItem.setFont(menuFont);
		lockItem.setFont(menuFont);
		cancelItem.setFont(menuFont);
		exitItem.setFont(menuFont);
		
		helpItem.setFont(menuFont);
		aboutItem.setFont(menuFont);
		
		//将相应菜单项添加到‘系统’菜单
		systemMenu.add(alterPasswordItem);
		systemMenu.add(delUserItem);
		systemMenu.add(lockItem);
		systemMenu.add(cancelItem);
		systemMenu.add(exitItem);
		//将相应菜单项添加到‘帮助’菜单
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);
		
		//将‘系统’菜单添加到菜单栏
		menubar.add(systemMenu);		
		//将‘帮助’菜单添加到菜单栏
		menubar.add(helpMenu);
	}
	
	public void initJps()
	{//界面上半部分的初始化
		jps.setLayout(null);//设置jps布局管理器为null
		jps.setBackground(bgColor);
		
		//设置按钮大小并添加到JPanel面板里
		jba.setBounds(5,10,80,26);	
		jba.setFont(font);	
		jba.setForeground(foreColor);
		jba.addActionListener(this);//为添加按钮注册事件监听器
		jps.add(jba);//添加按钮到jps面板里
		jbs.setBounds(90,10,80,26);
		jbs.setFont(font);
		jbs.setForeground(foreColor);
		jbs.addActionListener(this);//为查找按钮注册事件监听器
		jps.add(jbs);//添加按钮到jps面板里
		//设置jtfs文本框大小并添加到jps面板里
		jtfs.setBounds(175,10,120,26);
		jtfs.addActionListener(this);//为文本框注册事件监听器
		jps.add(jtfs);
		//设置单选按钮大小和位置并添加到jpbr面板里同时添加到bg单选按钮组里
		jrbxm.setBounds(5,0,50,26);
		jrbxm.setBackground(bgColor);
		jrbxm.setForeground(foreColor);
		jrbxm.addItemListener(this);//为单选按钮注册ItemEvent事件监听器
		bg.add(jrbxm);
		jpbr.add(jrbxm);
		jrbbh.setBounds(60,0,50,26);
		jrbbh.setBackground(bgColor);
		jrbbh.setForeground(foreColor);
		jrbbh.addItemListener(this);//为单选按钮注册ItemEvent事件监听器
		bg.add(jrbbh);
		jpbr.add(jrbbh);
		jpbr.setBounds(300,8,200,28);
		jpbr.setBackground(bgColor);
		jpbr.setFont(font);
		jps.add(jpbr);
	}
	public void initTree()
	{//初始化树
		//设置背景色及字体色和字体
		jtz.setBackground(bgColor);
		jtz.setForeground(foreColor);
		jtz.setFont(font);
		
		jtz.setModel(dtm);//设置树模型		
		jtz.setExpandsSelectedPaths(true);//设置树ExpandsSelectedPaths属性		
		jtz.setCellRenderer(dtcr);//设置树的节点绘制器		
		ImageIcon icon=new ImageIcon("images\\wzk.png");//得到树节点关闭的图标		
		dtcr.setClosedIcon(icon);//设置树节点关闭的图标		
		icon=new ImageIcon("images\\zk.png");//得到树节点展开的图标
		dtcr.setOpenIcon(icon);//设置树节点展开的图标
		icon=new ImageIcon("images\\mzjd.png");//得到树的叶子节点的图标
		dtcr.setLeafIcon(icon);//设置树的叶子节点的图标
		Vector<String> group=DBOperate.getNode(user,"uid");//从数据库得有多少个分组
		for(int i=0;i<group.size();i++)
		{//添加组节点
			String s=group.get(i);			
			DefaultMutableTreeNode dmtnGroup=//创建分组节点对象
							new DefaultMutableTreeNode(new NodeValue(s,1));
			dtm.insertNodeInto(dmtnGroup,root,i);//将分组节点添加到根节点			
			//添加人名节点
			Vector<String> pnode=DBOperate.getNode(user,"pname;"+s);		
			for(String person:pnode)
			{
				dmtnGroup.add(this.initPerNode(person));//将各个分组下的联系人节点添加到分组节点
			}					
		}
	}
	public void initJpy()
	{//界面右边为卡片布局的JPanel里一些控件的添加
		jpy.setLayout(cl);
		//设置背景色
		jpy.setBackground(bgColor);
		//设置选中根节点显示信息格式并添加到面板		
		jlRoot.setFont(new Font("Courier",Font.PLAIN,22));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		jpy.add("root",jlRoot);//添加根节点显示信息				
		jpy.add("Info",jpyInfo);//添加联系人信息面板
		//设置选中分组节点显示信息格式并添加到面板
		jlGroup.setFont(new Font("楷体_GB2312",Font.BOLD,40));
		jlGroup.setForeground(foreColor);
		jlGroup.setHorizontalAlignment(JLabel.CENTER);
		jlGroup.setVerticalAlignment(JLabel.CENTER);		
		jpy.add("group",jlGroup);//添加分组节点显示信息
		//初始化图片预览界面并添加到面板		
		jpyview.setBackground(Color.black);//设置背景色为黑色
		jpyview.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpy.add("tpyl",jspyview);
		//设置相册没有照片时提示信息格式并添加到面板
		jlNoPic.setFont(new Font("楷体_GB2312",Font.BOLD,40));
		jlNoPic.setForeground(foreColor);
		jlNoPic.setHorizontalAlignment(JLabel.CENTER);
		jlNoPic.setVerticalAlignment(JLabel.CENTER);
		jpy.add("nopic",jlNoPic);	
		//初始化图片明细界面并添加到面板
		jlDetail.setOpaque(true);
	    jlDetail.setBackground(Color.black);//设置背景色为黑色	
		jlDetail.setVerticalAlignment(JLabel.CENTER);
		jlDetail.setHorizontalAlignment(JLabel.CENTER);
		jpy.add("tpmx",jspydetail);	
		
		//初始化图片加载进度界面
		jpy.add("tpjd",jpProgress);
		jpProgress.setLayout(null);
		jpProgress.setBackground(bgColor);
		jlProgress.setBounds(20,20,250,50);//设置大小和位置
		jlProgress.setFont(new Font("楷体_GB2312",Font.PLAIN,25));
		jlProgress.setForeground(foreColor);
		
		jpProgress.add(jlProgress);//添加进度条
		jpb.setBounds(20,70,400,50);//设置大小和位置
		jpb.setBackground(new Color(208,244,247));//设置进度条背景色
		jpb.setFont(new Font("楷体_GB2312",Font.PLAIN,25));//设置进度显示百分比的字体
		jpProgress.add(jpb);
		jpb.setBorderPainted(true);//设置进度条边框显示
		jpb.setStringPainted(true);//设置进度条字符显示
	}	
	public void initInfo()
	{//初始化信息界面
		//设置信息面板背景色
		jpyInfo.setBackground(bgColor);
		jpyInfo.setLayout(null);//设置布局管理器为空
		
		jpyInfo.setBounds(50,50,380,360);//设置信息面板的大小和位置		
		jlPhoto.setBounds(220,10,150,170);//设置联系人图像JLabel的大小和位置
		jlPhoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));//将JLbel的边框线显现出来
		jpyInfo.add(jlPhoto);//将显示联系人照片的JLabel添加到信息面板
		
		for(int i=0;i<10;i++)//添加文本标签，并设置大小和位置
		{
			jlInfo[i].setBounds(20,10+i*30,60,26);
			//设置标签文本颜色
			jlInfo[i].setForeground(foreColor);
			jpyInfo.add(jlInfo[i]);
		}
		//添加相片部分的控件
		jlInfo[10].setBounds(20,360,60,26);
		//设置标签文本颜色
		jlInfo[10].setForeground(foreColor);
		jpyInfo.add(jlInfo[10]);
		
		//上传照片张数进度提醒
		jlInfo[11].setBounds(270,395,300,30);
		jlInfo[11].setFont(font);
		jlInfo[11].setForeground(Color.RED);
		jpyInfo.add(jlInfo[11]);
		
		jtfPhoto.setBounds(80,360,200,26);//设置得到照片路径的文本框的大小和位置
		//设置照片路径的文本框文本颜色
		jtfPhoto.setForeground(foreColor);
		jpyInfo.add(jtfPhoto);//将得到照片路径的文本框添加到信息面板
		
		jbInfo[6].setBounds(285,360,80,26);
		//设置标签文本颜色
		jbInfo[6].setForeground(foreColor);
		jbInfo[6].addActionListener(this);//为添加照片的浏览按钮注册事件监听器
		jpyInfo.add(jbInfo[6]);
		
		//设置文件选择器的几种选择文件格式		
		jfcPho.addChoosableFileFilter(new FileNameExtensionFilter("GIF图片文件","gif","GIF"));
		jfcPho.addChoosableFileFilter(new FileNameExtensionFilter("PNG图片文件","png","PNG"));
		jfcPho.addChoosableFileFilter(new FileNameExtensionFilter("JPEG图片文件","jpg","jpeg"));
		for(int i=0;i<10;i++)
		{//初始化一些文本框
			jtfInfo[i]=new JTextField();
			//设置文本框颜色
			jtfInfo[i].setForeground(foreColor);
		}
		for(int i=1;i<7;i++)
		{//设置一些类似文本框的位置
			if(i!=2&i!=6)
			{
				jtfInfo[i].setBounds(80,10+i*30,135,26);
				//设置文本框颜色
				jtfInfo[i].setForeground(foreColor);
				jtfInfo[i].addActionListener(this);//为文本框注册事件监听器				
				jpyInfo.add(jtfInfo[i]);//将文本框添加到信息面板				
			}
		}
		//性别部分
		jrbMale.setBounds(5,3,50,26);
		//设置单选按钮背景色
		jrbMale.setBackground(bgColor);
		//设置单选按钮文本颜色
		jrbMale.setForeground(foreColor);
		jrbMale.addItemListener(this);		//为单选按钮注册ItemEvent事件监听器		
		bgGender.add(jrbMale);
		jpGender.add(jrbMale);
		jrbFemale.setBounds(60,3,50,26);
		//设置单选按钮背景色
		jrbFemale.setBackground(bgColor);	
		//设置单选按钮文本颜色
		jrbFemale.setForeground(foreColor);
		jrbFemale.addItemListener(this);	//为单选按钮注册ItemEvent事件监听器
		bgGender.add(jrbFemale);
		jpGender.add(jrbFemale);
		jpGender.setBounds(60,70,125,26);
		//设置单选按钮容器背景色
		jpGender.setBackground(bgColor);
		jpyInfo.add(jpGender);				//将单选按钮的面板jpbr添加到jps里
		//分组
		jcb.setBounds(80,190,75,26);	
		//设置下拉列表框背景色
		jcb.setBackground(bgColor);	
		//设置下拉列表框文本颜色
		jcb.setForeground(foreColor);
		jcb.setEditable(false);//设置分组文本为不可编辑
		this.initGroup();//初始话分组下拉列表框
		jcb.setSelectedIndex(4);//默认选择的是其他分组
		jpyInfo.add(jcb);//将分组下拉列表框添加到信息面板		
		for(int i=0;i<2;i++)//添加分组删除分组按钮
		{
			jbInfo[4+i].setBounds(175+100*i,190,90,26);
			//设置按钮文本颜色
			jbInfo[4+i].setForeground(foreColor);
			jbInfo[4+i].addActionListener(this);//为按钮注册事件监听器
			jpyInfo.add(jbInfo[4+i]);
		}
		//用户编号
		jtfInfo[0].setBounds(80,10,135,26);
		jpyInfo.add(jtfInfo[0]);
		//更改图像
		jtfInfo[7].setBounds(80,220,200,26);
		jpyInfo.add(jtfInfo[7]);
		jbInfo[3].setBounds(285,220,80,26);
		//设置按钮文本颜色
		jbInfo[3].setForeground(foreColor);
		jbInfo[3].addActionListener(this);//为按钮注册事件监听器
		jpyInfo.add(jbInfo[3]);//将添加图像的浏览按钮添加到信息面板
		//设置文件选择器的几种选择文件格式	
		jfcPic.addChoosableFileFilter(new FileNameExtensionFilter("GIF图片文件","gif","GIF"));
		jfcPic.addChoosableFileFilter(new FileNameExtensionFilter("PNG图片文件","png","PNG"));
		jfcPic.addChoosableFileFilter(new FileNameExtensionFilter("JPEG图片文件","jpg","jpeg"));		
		//邮编文本框的添加
		jtfInfo[8].setBounds(80,250,135,26);
		jpyInfo.add(jtfInfo[8]);
		//地址文本框的添加
		jtfInfo[9].setBounds(80,280,285,26);
		jpyInfo.add(jtfInfo[9]);
		//编辑 保存 删除 等按钮
		for(int i=0;i<3;i++)
		{
			jbInfo[i].setBounds(80+i*100,320,80,26);
			//设置按钮文本颜色
			jbInfo[i].setForeground(foreColor);
			jbInfo[i].addActionListener(this);//为按钮注册事件监听器
			jpyInfo.add(jbInfo[i]);
		}
		for(int i=0;i<2;i++)
		{//上传和删除按钮
			jbInfo[7+i].setBounds(80+i*100,395,80,26);
			//设置按钮文本颜色
			jbInfo[7+i].setForeground(foreColor);
			jbInfo[7+i].addActionListener(this);//为按钮注册事件监听器
			jpyInfo.add(jbInfo[7+i]);
		}
	}
	public void initGroup()//初始化分组下拉列表
	{
		Vector<String> v=DBOperate.getNode(user,"uid");//得到所有分组列表
		boolean b=false;//记录下拉列表中是否存在已有的选项
		for(int i=0;i<v.size();i++)
		{
			for(int j=0;j<jcb.getItemCount();j++)
			{
				if(v.get(i).equals(jcb.getItemAt(j)))
				{
					b=true;	break;//下拉列表框中存在此选项时					
				}			
			}
			if(b==false)
			{//下拉列表框中不存在此选项时 将其添加到分组下拉列表框		
				jcb.addItem(v.get(i));
			}
			else
			{
				b=false;//将b置为false 以待下一次循环使用		
			}							
	    }
	}
	public void initTray()//初始化系统托盘
	{		
		lockAndUnlockMainFrame.addActionListener(this);//为菜单选项注册监听器
		waitUser.addActionListener(this);//为菜单选项注册监听器
		showMainFrame.addActionListener(this);//为菜单选项注册监听器
		currently.addActionListener(this);//为菜单选项注册监听器		
		exit.addActionListener(this);//为菜单选项注册监听器

		popup.add(lockAndUnlockMainFrame);//将菜单选项添加到菜单
		popup.add(waitUser);//将菜单选项添加到菜单
		popup.add(showMainFrame);//将菜单选项添加到菜单
		popup.add(currently);//将菜单选项添加到菜单
		popup.add(exit);//将菜单选项添加到菜单	
		
		if(SystemTray.isSupported())//判断当前系统是否支持系统托盘
		{		
			tray=SystemTray.getSystemTray();//通过静态方法得到系统托盘			
			Image image=Toolkit.getDefaultToolkit().getImage("images\\link.png");//加载图像			
			trayIcon=new TrayIcon(image,"我的通讯录",popup);//创建TrayIcon对象得到托盘图标			
			trayIcon.setImageAutoSize(true);//设置托盘图标自动设置尺寸
			try
			{//将托盘图标设置到系统托盘中
				tray.add(trayIcon);		
			}
			catch(AWTException e)
			{
				e.printStackTrace();
			}
			trayIcon.addActionListener(this);//为trayIcon注册事件监听器
		}
	}
	public DefaultMutableTreeNode initPerNode(String person)//生成联系人节点
	{
		DefaultMutableTreeNode dmtnPerson=//根据得到的人名生成一个树节点
				new DefaultMutableTreeNode(new NodeValue(person,2));	
		DefaultMutableTreeNode dmtnPhoto=//在联系人节点下添加相册节点
				new DefaultMutableTreeNode(new NodeValue("相册",3));
		dmtnPerson.add(dmtnPhoto);	
		Vector<String> pphoto=DBOperate.getNode(user,person);//得到该用户下该联系人的相册照片名称列表
		for(String photo:pphoto)
		{
			DefaultMutableTreeNode Photo=//生成照片节点
				new DefaultMutableTreeNode(new NodeValue(photo,4));
			dmtnPhoto.add(Photo);//添加到相册节点上
		}	
		return dmtnPerson;//返回生成的节点		
	}
	public void clearInfo()//清空信息面板
	{		
		for(int i=0;i<10;i++)
		{
			jtfInfo[i].setText("");//清空文本框
		}
		jlPhoto.setIcon(null);//清空图像
		jcb.setSelectedItem("其他");//设置分组选择“其它分组”
	}
	public void setInfo(Vector<String> pInfo)//将信息向量设置到信息面板中
	{//将信息向量按规则填到信息面板里
		this.clearInfo();		
		if(pInfo.size()==0)
		{
			JOptionPane.showMessageDialog(this,"所查用户不存在！！！","错误",
											JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			for(int i=0;i<2;i++)
			{//显示联系人编号和姓名
				jtfInfo[i].setText(pInfo.get(i));
			}			
			if(pInfo.get(2).equals("男"))
			{//显示性别
				jrbMale.setSelected(true);
			}
			else
			{//显示性别
				jrbFemale.setSelected(true);
			}
			for(int i=3;i<6;i++)
			{//显示年龄、电话号码和电子邮件
				jtfInfo[i].setText(pInfo.get(i));
			}
			for(int i=7;i<9;i++)
			{//显示邮编和地址
				jtfInfo[i+1].setText(pInfo.get(i));
			}			
			jcb.setSelectedItem(pInfo.get(6));  //设置分组信息
			this.setPic(pInfo.get(0));//设置图像为从数据库得到的图像
			this.setEditable(false);//设置面板不可编辑
		}	
	}
	public void setPic(String pid)//设置个人图像显示
	{
		final int width=145;//显示图像的JLabel的宽度
		final int height=165;//显示图像的JLabel的高度
		String sql="SELECT pphoto FROM ContactInfo WHERE pid='"+pid+"'";
		Image Pic=DBOperate.getPic(sql);//从数据库得到此人的图像
		if(Pic!=null)
		{//如果此联系人上传了图像
			int pw=Pic.getWidth(MainFrame.this);//得到此人图像的宽度
			int ph=Pic.getHeight(MainFrame.this);//得到此人图像的高度
			if(pw>ph)
			{//宽度大于高度，图像进行缩放
				Pic=Pic.getScaledInstance(width,height,Image.SCALE_SMOOTH);			
			}
			else
			{//高度大于宽度，图像进行缩放
				Pic=Pic.getScaledInstance(width,height,Image.SCALE_SMOOTH);				
			}
			jlPhoto.setIcon(new ImageIcon(Pic));//将图像显示到JLabel
			jlPhoto.setHorizontalAlignment(JLabel.CENTER);//设置图片水平居中显示
			jlPhoto.setVerticalAlignment(JLabel.CENTER);//设置图片垂直方向居中显示
		}
		else
		{//如果图像为空，则不显示
			jlPhoto.setIcon(null);
		}		
	}
	public void setEditable(boolean Editable)//设置信息窗口是否可编辑
	{
		jrbFemale.setEnabled(Editable);//设置性别是否可编辑
		jrbMale.setEnabled(Editable);//设置性别是否可编辑
		jcb.setEnabled(Editable);//设置下拉列表框是否可编辑		
		for(int i=0;i<10;i++)
		{
			jtfInfo[i].setEditable(Editable);//设置文本框是否可编辑
			if(i>=3&&i<=5)
			{
				jbInfo[i].setEnabled(Editable);//设置部分按钮是否可编辑						
			}			
		}
		jbInfo[1].setEnabled(Editable);//设置保存按钮是否可编辑
	}
	public Vector<String> getInfo()//从信息面板得到用户输入的信息
	{
		Vector<String> pInfo=new Vector<String>();
		pInfo.add(jtfInfo[0].getText().trim());//添加pid
		pInfo.add(jtfInfo[1].getText().trim());//添加pname
		String gender=jrbMale.isSelected()?"男":"女";
		pInfo.add(gender);//添加性别
		pInfo.add(jtfInfo[3].getText().trim());//年龄
		pInfo.add(jtfInfo[4].getText().trim());//电话号码
		pInfo.add(jtfInfo[5].getText().trim());//Email	
		pInfo.add((String)jcb.getSelectedItem());//分组
		pInfo.add(jtfInfo[8].getText().trim());//邮编
		pInfo.add(jtfInfo[9].getText().trim());//地址
		String photoPath=jtfInfo[7].getText().trim();//得到照片路径
		pInfo.add(photoPath);//照片路径		
		return pInfo;
	}
//***************分组管理******************
	public void monitorAddGroupButton()//监听添加分组按钮
	{
		String group=JOptionPane.showInputDialog(this,"请输入分组:","添加分组",
													JOptionPane.PLAIN_MESSAGE);
		if(group!=null)
		{//group不等于null的情况
			if(group.equals(""))
			{//输入分组是空值
				JOptionPane.showMessageDialog(this,"名称不能为空","错误",
											JOptionPane.WARNING_MESSAGE);
			}
			else
			{//分组名不为空
				int count=jcb.getItemCount();//得到分组下拉选项的个数
				for(int i=0;i<count;i++)
				{
					if(group.equals((String)jcb.getItemAt(i)))
					{//是否有相同的分组
						JOptionPane.showMessageDialog(this,"不能插入相同的组","错误",
												JOptionPane.WARNING_MESSAGE);
						return;//有相同的分组，不添加，直接返回
					}				
				}							
				jcb.addItem(group);//添加分组到下拉列表				
				jcb.setSelectedItem(group);//设置分组选中为刚添加的分组				
				this.addGroupNode(group);//在树上添加分组				
			}
		}
	}
	public void addGroupNode(String group)//在树上添加分组节点
	{
		DefaultMutableTreeNode newGroupNode=//生成一个分组类型的节点
								new DefaultMutableTreeNode(new NodeValue(group,1));
		root.add(newGroupNode);//将节点添加到树的根节点上
		((DefaultTreeModel)jtz.getModel()).reload();//更新树模型
	}
	public void monitorDelGroupButton()//删除分组的监听
	{
		int i=JOptionPane.showConfirmDialog(this,"删除分组将会删除"+
			"分组里的所有联系人！！！是否删除？","确认",JOptionPane.YES_NO_OPTION);
		if(i==JOptionPane.YES_OPTION)
		{//确认了
			String group=(String)jcb.getSelectedItem();
			this.delGroupNode(group);//树上的响应
			int count=DBOperate.delGroup(user,group);//数据库里的删除
			jcb.removeItem((String)jcb.getSelectedItem());//下拉列表的删除
			if(count>=0)
			{
				JOptionPane.showMessageDialog(this,"删除分组成功，共删除联系人"+
						count+"个！！！","消息",JOptionPane.INFORMATION_MESSAGE);
			}
			this.clearInfo();//清空信息面板			
		}
	}
	public void delGroupNode(String group)
	{//删除分组节点
		for(int i=0;i<root.getChildCount();i++)
		{
			DefaultMutableTreeNode groupNode=//得到分组节点
						(DefaultMutableTreeNode)root.getChildAt(i);			
			NodeValue groupNv=(NodeValue)groupNode.getUserObject();//转为自定义的对象
			if(group.equals(groupNv.getValue()))
			{//找到要删除的节点
				root.remove(groupNode);//删除此分组节点				
				((DefaultTreeModel)jtz.getModel()).reload(groupNode);//树模型更新
				break;//删除成功，退出循环
			}				
		}
	}
//************************************联系人管理*******************************
	public void monitorDelButton()//监听删除按钮的方法
	{	
		String personName=jtfInfo[1].getText().trim();//得到联系人的名字
		String personGroup=(String)jcb.getSelectedItem();//得到分组名字
		String pid=jtfInfo[0].getText().trim();//得到联系人ID
		//弹出删除对话框
		int index=JOptionPane.showConfirmDialog(this,"是否删除？y/n","确认对话框",
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(index==0)
		{//确认删除		
			boolean b=this.delPerNode(personName,personGroup);//更新树列表
			if(b==true)
			{
				String sqla="delete FROM photo WHERE pid='"+pid+"'";
				String sqlb="delete FROM ContactInfo WHERE pid='"+pid+"'";
				DBOperate.update(sqla);//删除联系人的相册中所有照片
				int i=DBOperate.update(sqlb);//删除联系人信息
				if(i!=-1)
				{//删除成功对话框					
					JOptionPane.showMessageDialog(this,"删除成功","删除",
											JOptionPane.INFORMATION_MESSAGE);
					this.clearInfo();						
				}
			}		
		}
	}	
	public void monitorSaveButton()//监听保存按钮的方法
	{
		String pid=jtfInfo[0].getText().trim();//得到联系人的编号
		String pname=jtfInfo[1].getText().trim();//得到联系人的姓名
		String sqla="SELECT * FROM ContactInfo WHERE pid='"+pid+"'";//判断此编号是否存在的SQL
		String sqlb="SELECT * FROM ContactInfo WHERE pname='"+pname+"'";//判断此姓名是否存在的SQL
		boolean isIdExist=DBOperate.isExist(sqla);//得到编号是否存在
		boolean isNameExist=DBOperate.isExist(sqlb);//得到姓名是否存在		
		String perGroupAfter=(String)jcb.getSelectedItem();//得到修改之后的分组		
		String perNameAfter=jtfInfo[1].getText().trim();//得到修改后联系人的名字					
		if(!(pid.equals("")||pname.equals("")))
		{
			if(this.isInsert)//是添加的话
			{			
				if(!(isIdExist||isNameExist))
				{					
					//得到插入的图片路径是否合法的判断字符串
					String s=DBOperate.insertPerson(user,this.getInfo());
					if(!s.equals("isNull"))
					{//在树上添加节点 此为合法的文件路径
						this.addPerNode(perNameAfter,perGroupAfter);
					}
					else
					{
						JOptionPane.showMessageDialog(this,"图像路径不合法"+
							"添加联系人失败","错误",JOptionPane.WARNING_MESSAGE);
					}							
				}
				else
				{//用户名或者ID已经存在
					JOptionPane.showMessageDialog(this,"联系人ID或者姓名已经存在","错误",JOptionPane.WARNING_MESSAGE);
				}
			}
			else
			{//编辑联系人资料
				if(isIdExist)
				{//ID没变进行更新
					String s=DBOperate.updatePerson(user,this.getInfo());
					if(!s.equals("isNull"))
					{	
						//先删除以前的联系人节点
						this.delPerNode(this.perNameBefor,this.perGroupBefor);
						//添加新的节点到修改的组
						this.addPerNode(perNameAfter,perGroupAfter);		
					}
					else
					{
						JOptionPane.showMessageDialog(this,"图像路径不合法"+
							"添加联系人失败","错误",JOptionPane.WARNING_MESSAGE);				
					}
				}
				this.perNameBefor=null;//将perNameBefor置空
				this.perGroupBefor=null;//将perGroupBefor置空
				if(!isIdExist)
				{//ID变了
					if(!isNameExist)
					{//名字也变了就执行插入动作
						String s=DBOperate.insertPerson(user,this.getInfo());
						if(!s.equals("isNull"))
						{//在树上添加节点 此为合法的文件路径
							this.addPerNode(perNameAfter,perGroupAfter);
						}
						else
						{
							JOptionPane.showMessageDialog(this,"图像路径不合法"+
								"添加联系人失败","错误",JOptionPane.WARNING_MESSAGE);
						}
					}
					else
					{
						DBOperate.update("delete FROM ContactInfo WHERE pname='"+perNameAfter+"'");				
						DBOperate.insertPerson(user,this.getInfo());//重新插入此人记录
					}
				}										
			}				
		}
		else
		{//为空
			JOptionPane.showMessageDialog(this,"联系人ID或者姓名不能为空","错误",
								JOptionPane.WARNING_MESSAGE);
			return;				
		}
		this.isInsert=false;this.setEditable(false);		
	}	
	public void monitorSearchButton()//监听查找按钮的方法
	{
		String name=jtfs.getText().trim();
		String sql="";//声明查找字符串
		if(name.equals(""))
		{
			JOptionPane.showMessageDialog(this,"查找条件不能为空！！！",
								"错误",JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			if(this.searchByName==true)
			{//按姓名查找
				sql="SELECT pid,pname,pgender,page,pnumber,pemail,pgroup,ppostalcode,"+"padress FROM ContactInfo WHERE UserName='"+user+"'AND pname='"+name+"'";				
				this.setInfo(DBOperate.getPerInfo(sql));//设置信息面板为该联系人的信息			
			}
			else
			{//按编号查找
				sql="SELECT pid,pname,pgender,page,pnumber,pemail,pgroup,ppostalcode,"+"padress FROM ContactInfo WHERE UserName='"+user+"'AND pid='"+name+"'";
				this.setInfo(DBOperate.getPerInfo(sql));;//设置信息面板为该联系人的信息		
			}
		}
		this.setEditable(false);//设置面板不可编辑
		cl.show(jpy,"Info");//将Info面板显示出来		
	}
	public void addPerNode(String personName,String group)//添加联系人节点
	{
		//添加首先生成联系人节点的一些信息
		boolean flag=false;//此联系人分组在树上是否存在的标志
		DefaultMutableTreeNode perNode=this.initPerNode(personName);//生成联系人节点
		DefaultMutableTreeNode groupNode;//分组节点				
		for(int i=0;i<root.getChildCount();i++)//节点已生成，在树上找到节点所属的父节点
		{//得到分组节点的值
			groupNode=(DefaultMutableTreeNode)root.getChildAt(i);
			NodeValue groupNv=(NodeValue)groupNode.getUserObject();
			if(groupNv.getValue().equals(group))
			{//得到的分组节点的值等于此人所属分组，挂接节点
				groupNode.add(perNode);//将联系人节点添加到此分组节点
				((DefaultTreeModel)jtz.getModel()).reload(groupNode);//更新树模型
				flag=true; //树上存在此分组节点置标志位为true
				break;//退出循环
			}
		}
		if(flag==false)
		{//分组节点在树中不存在
			DefaultMutableTreeNode dmtGroup=//生成新的分组节点
							new DefaultMutableTreeNode(new NodeValue(group,1));
			dmtGroup.add(perNode);//新建一个分组节点，将其添加到新建的分组上
			root.add(dmtGroup);//将其挂到树上
			((DefaultTreeModel)jtz.getModel()).reload(root);//更新树模型			    	
		}		
	}
	public boolean delPerNode(String personName,String group)//删除联系人节点
	{
		boolean flag=false;  //是否删除成功	
		DefaultMutableTreeNode groupNode;//声明分组节点
		DefaultMutableTreeNode personNode;//声明联系人节点
		for(int i=0;i<root.getChildCount();i++)
		{//循环得到要删除联系人的分组节点
			groupNode=(DefaultMutableTreeNode)root.getChildAt(i);
			NodeValue groupNv=(NodeValue)groupNode.getUserObject();
			if(groupNv.getValue().equals(group))
			{
				for(int j=0;j<groupNode.getChildCount();j++)
				{//得到分组节点下联系人节点
					personNode=(DefaultMutableTreeNode)groupNode.getChildAt(j);
					NodeValue personNv=(NodeValue)personNode.getUserObject();
					if(personNv.getValue().equals(personName))
					{//找到此人节点 现在从此节点得到相册节点，然后添加到相册节点一个照片节点
						personNode.removeFromParent();
						((DefaultTreeModel)jtz.getModel()).reload(groupNode);
						flag=true;//删除成功
						break;//退出循环
					}
				}
				break;//退出循环
			}
		}
		if(flag==false)
		{//删除失败，弹出提示消息
			JOptionPane.showMessageDialog(this,"此分组下没有这个人！！！",
								"错误",JOptionPane.WARNING_MESSAGE);	
		}
		return flag;
	}
	
	
//*****************照片管理***************
	public void monitorUploadButton() throws InterruptedException//监听上传照片的方法
	{	
		String pathList=jtfPhoto.getText();//得到照片路径列表
		final String[] path=pathList.split(";");//将路径列表拆分成一张一张照片的路径
		if(pathList.equals("")||jtfInfo[0].getText().equals(""))
		{//路径为空或者编号为空
			JOptionPane.showMessageDialog(this,"路径或者联系人编号不得为空！！！",
"错误",JOptionPane.WARNING_MESSAGE);
		}
		else
		{//路径和编号均不为空
			new Thread()//新建一个线程上传照片
			{
				int success=0;//上传成功张数
				int fail=0;//上传失败张数
				
				public void run()
				{
					for(int i=0;i<path.length;i++)//依次上传选择的多张照片
					{
						int j=DBOperate.insertPic(path[i],jtfInfo[0].getText());
						switch(j)
						{
							case 0://上传成功，更新照片节点
							jlInfo[11].setText("第 -"+(i+1)+"- 张照片上传成功");
							addPhoNode(path[i]);
							success++;//成功一张
								break;
							case 1://路径错误
JOptionPane.showMessageDialog(null,"系统找不到第"+(i+1)+"个文件！！！\n错误路径:"+path[i], "错误",JOptionPane.WARNING_MESSAGE);
							fail++;//失败一张
								break;
							case 2://该文件名已存在
JOptionPane.showMessageDialog(null,"照片文件已存在或没有此联系人！请先添加联系人\n错误路径:"+path[i], "错误",JOptionPane.WARNING_MESSAGE);
							fail++;//失败一张
						}
						
						try
						{
							sleep(20);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					//上传完成，提示上传成功和失败的张数
					jlInfo[11].setText("上传完成!	成功:"+success+"	  失败:"+fail);
JOptionPane.showMessageDialog(null,"上传完成!\n上传成功:"+success+"张\n上传失败:"+fail+"张","提示",JOptionPane.WARNING_MESSAGE);
				};
			}.start();//启动线程
		}
	}
	public void addPhoNode(String photoPath)//在树上添加照片节点
	{
		String photoName=(new File(photoPath)).getName();	//得到相片的名字
		String personName=jtfInfo[1].getText().trim();//得到联系人的名字
		String group=(String)jcb.getSelectedItem();	//得到分组的名字
		DefaultMutableTreeNode groupNode;//声明分组节点
		DefaultMutableTreeNode personNode;//声明联系人节点
		DefaultMutableTreeNode photoNode;//声明照片节点
		for(int i=0;i<root.getChildCount();i++)
		{//循环得到添加照片联系人的分组节点
			groupNode=(DefaultMutableTreeNode)root.getChildAt(i);
			NodeValue groupNv=(NodeValue)groupNode.getUserObject();
			if(groupNv.getValue().equals(group))
			{//得到此联系人的分组				
				for(int j=0;j<groupNode.getChildCount();j++)
				{//得到分组节点下的添加照片联系人的节点
					personNode=(DefaultMutableTreeNode)groupNode.getChildAt(j);
					NodeValue personNv=(NodeValue)personNode.getUserObject();
					if(personNv.getValue().equals(personName))
					{//找到此人节点 现在从此节点得到相册节点						
						photoNode=(DefaultMutableTreeNode)personNode.getChildAt(0);
						photoNode.add(new DefaultMutableTreeNode(new NodeValue(photoName,4)));
						//通知树模型树已改变
						((DefaultTreeModel)jtz.getModel()).reload(photoNode);												
						break;//退出内层循环
					}
				}
			break;//退出外层循环
			}
		}
	}
	public void delPhoNode(String phoName)
	{
		boolean isDel=false;
		if(phoName.equals("")||phoName==null)
		{//名称为空则弹出错误消息
			JOptionPane.showMessageDialog(this,"照片名称不对，请在添加照片文本框里"+
							"输入照片名称！！！","错误",JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			TreePath tp=jtz.getSelectionPath();//得到当前路径			
			try
			{
				DefaultMutableTreeNode perNode=//得到当前路径选中的节点
							(DefaultMutableTreeNode)tp.getLastPathComponent();			
				DefaultMutableTreeNode xc=//得到选中联系人下面的相册节点
								(DefaultMutableTreeNode)perNode.getFirstChild();
				for(int i=0;i<xc.getChildCount();i++)
				{					
					DefaultMutableTreeNode phoNode=//得到相册下的照片节点
								(DefaultMutableTreeNode)xc.getChildAt(i);
					NodeValue zpmc=(NodeValue)phoNode.getUserObject();//得到自定义节点对象				
					if(zpmc.getValue().equals(phoName))
					{
						isDel=true;//是否删除设置为true						
						DBOperate.update("delete FROM photo WHERE photoname='"+phoName+"'");//更新
						xc.remove(i);//从相册中删除这个节点
						((DefaultTreeModel)jtz.getModel()).reload(xc);//更新树模型
						jtfPhoto.setText("");//清空文本框里的相片名称
						return;
					}
				}
			}
			catch(NullPointerException npe)
			{
				JOptionPane.showMessageDialog(this,"必须在选择该联系人而删除照片！！！","错误",
											JOptionPane.WARNING_MESSAGE);
				jtfPhoto.setText("");//清空添加照片文本框
			}
			if(!isDel)
			{
				JOptionPane.showMessageDialog(this,"此人没有这张照片","错误",
											JOptionPane.WARNING_MESSAGE);
				jtfPhoto.setText("");//清空添加照片文本框
			}			
		}		
	}
//*****************照片预览和照片明细******************
	public void viewPic(final DefaultMutableTreeNode cdmtn) //图片预览
	{		
		if(cdmtn.getChildCount()==0)
		{//相册下没有照片
			cl.show(jpy,"nopic");
		}
		else
		{
			cl.show(jpy,"tpjd");//显示加载进度条
			this.setEnabled(false);//加载图片时禁用窗口
		    new Thread()//新建一线程加载图片并启动此线程
		    {
		    	public void run()//实现run方法
		    	{			    		
		    		final int width=160;//定义预览照片的宽度
		    		final int height=160;//定义预览照片的高度
		    		final int span=10;//定义照片之间的间隔
		    		int pcount=cdmtn.getChildCount();//获取图片个数			    		
				    NodeValue nv=(NodeValue)cdmtn.getUserObject();//获取所有图片					   
				    if(nv.jla==null||nv.jla.length<pcount)
				    {//第一次加载或者添加了新照片后				    
					    String[] pname=new String[pcount];//图片名称数据						    
					    Image[] photo=new Image[pcount];//图片对象数组	    
					    jla=new JLabel[pcount];//放置图片的JLabel数组
				    	for(int i=0;i<pcount;i++)
					    {					    	
					    	String picName=cdmtn.getChildAt(i).toString();//获取图片名称
					    	pname[i]=picName;//将图片名称赋值给数组					    	
					    	String sql="SELECT photo FROM photo WHERE photoname='"+picName+"'";
					    	photo[i]=DBOperate.getPic(sql);//获取图片Image对象						    	
					    	MediaTracker mt=new MediaTracker(MainFrame.this);
					    	mt.addImage(photo[i],1);
					    	try
					    	{
					    		mt.waitForAll();//开始加载图像
					    	}
					    	catch(Exception err){err.printStackTrace();}				    	
					    		//图片缩放
					    		int pw=photo[i].getWidth(MainFrame.this);//图片实际宽度
					    		int ph=photo[i].getHeight(MainFrame.this);//图片实际高度
					    		if(pw>ph)//宽度大
					    		{
					photo[i]=photo[i].getScaledInstance(width,width*ph/pw,Image.SCALE_SMOOTH);
					    		}
					    		else//高度大
					    		{
					photo[i]=photo[i].getScaledInstance(height*pw/ph,height,Image.SCALE_SMOOTH);
					    		}
					    		jla[i]=new JLabel(new ImageIcon(photo[i]));//将缩放后的图片添加到JLabel中
					    		jla[i].setPreferredSize(new Dimension(width,height));//设置JLabel的高度和宽度
					    		jla[i].setToolTipText(pname[i]);//设置照片弹出气球文本字符串					    							    		
					    		MouseAdapter ma=new MouseAdapter()//添加鼠标监听器
					    		{
					    			public void mouseClicked(MouseEvent e)
					    			{//点击一张图片，就是图片明细
					    				JLabel tjl=(JLabel)e.getSource();
					    				detailPic(tjl.getToolTipText());
					    			}
					    			public void mouseEntered(MouseEvent e)
					    			{//鼠标移上图片，高亮显示
					    				JLabel tjl=(JLabel)e.getSource();
					    				tjl.setBorder(new MyBorder());
					    			}
					    			public void mouseExited(MouseEvent e)				    			
					    			{//鼠标移出，高亮效果消失
					    				JLabel tjl=(JLabel)e.getSource();
					    				tjl.setBorder(null);
					    			}
					    		};
					    	   jla[i].addMouseListener(ma);//注册事件监听器
					    	   jla[i].addMouseMotionListener(ma);//注册事件监听器					    
					           jpb.setValue(90*i/pcount);//设置进度条显示
					           jpb.setString(90*i/pcount+"%");//设置进度条字符串显示				    
					    }
						nv.jla=jla;//将图片JLabel数组赋值给nv的图片缓冲数组
				    }
				    else
				    {//没有新照片添加进来时使用以前加载好的
				    	jla=nv.jla;
				    }
			//得到当前jspview的Dimension对象
			Dimension tempD=jspyview.getViewportBorderBounds().getBounds().getSize();
			int tw=(int)tempD.getWidth();//得到当前jspview的宽度
			int perLine=tw/(span+width);//计算每行显示多少幅照片
			int rowc=jla.length/perLine+((jla.length%perLine==0)?0:1);//计算显示照片需要多少行
			int th=rowc*(span+height)+span;//计算显示所有照片时预览窗体的高度
			jpyview.setPreferredSize(new Dimension(tw,th));//重新设置jpyview的宽度和高度			
			for(int i=0;i<jla.length;i++)
			{		
				jpb.setValue(90+10*i/jla.length);//设置进度条显示
				jpb.setString(90+10*i/jla.length+"%");//设置进度条字符串显示
				jpyview.add(jla[i]);//将所有照片添加到滚动窗体中
			}		
			cl.show(jpy,"tpyl");//显示图片预览面板
			MainFrame.this.setEnabled(true);
			    	}
			    }.start();//启动线程
		}	        	
	}
	
	//单独显示一张图片（显示图片详情）
	public void detailPic(String pname)
	{
		String sql="SELECT photo FROM photo WHERE photoname='"+pname+"'";
		ImageIcon ii=new ImageIcon(DBOperate.getPic(sql));
		jlDetail.setIcon(ii);//显示一张照片		
		cl.show(jpy,"tpmx");//卡片布局面板显示图片明细
	}
	
//	*******************************菜单栏相关方发*************************
	
	//修改密码方法
	public void alterPassword()
	{
		//定义用户名和密码，以此作为参数调用DBOperate类得静态方法来判断审核是否成功
		String user=this.user;
		String oldPwd;
		//定义输入两次新密码的变量
		String newPwd1;
		String newPwd2;
		
		//弹出输入框以输入验证密码
		oldPwd=JOptionPane.showInputDialog(null, "请验证用户 - "+user+" - 密码:");
		
		//判断验证（利用已定义的两个参数调用方法验证）
		if(DBOperate.check(user, oldPwd))
		{
			//弹出输入框以输入验证密码
			newPwd1=JOptionPane.showInputDialog(null, "请输入新密码:");
			
			//判断输入的新密码是否为空
			if(newPwd1==null||newPwd1.equals(""))
			{
				//输入不合法，弹出提示
				JOptionPane.showMessageDialog(this,"新密码不得为空！！！","错误",JOptionPane.WARNING_MESSAGE);	
			}
			else
			{
				//再次弹出输入框二次核对新密码
				newPwd2=JOptionPane.showInputDialog(null, "请再次输入新密码:");
				//判断二次输入的新密码是否为空
				if(newPwd2==null||newPwd2.equals(""))
				{
					//第二次输入不合法，弹出提示
				     JOptionPane.showMessageDialog(this,"新密码不得为空！！！","错误",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					//判断两次输入密码是否相同
					if(newPwd1.trim().equals(newPwd2.trim()))     //两次输入相同
					{
						//输入合法，调用方法执行密码更改
						//更新密码的SQL
						String sql="UPDATE User SET Password='"+newPwd1+"' WHERE UserName='"+user+"'";
						if(DBOperate.update(sql)>0)
						{
							//提示密码修改成功
							JOptionPane.showMessageDialog(this,"恭喜您！！！\n密码修改成功，请使用新密码！！！","系统提示",JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else
					{
						//两次新密码输入不同，弹出提示，并返回
						JOptionPane.showMessageDialog(this,"修改失败！！！\n两次输入不一致！","错误",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
		else
		{
			//提示验证失败
			JOptionPane.showMessageDialog(this,"密码验证失败！！！","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//删除用户方法
	public void delUser()
	{
		//定义用户名和密码，以此作为参数调用DBOperate类得静态方法来判断审核是否成功
		String user=this.user;
		String pwd;
		
		//弹出输入框以输入验证密码
		pwd=JOptionPane.showInputDialog(null, "请验证用户 - "+user+" - 密码:");
		
		//判断验证（利用已定义的两个参数调用方法验证）
		if(DBOperate.check(user, pwd))
		{
			//提示用户是否确定删除
			int yn=JOptionPane.showConfirmDialog(this,"删除用户后联系人信息将不可恢复...\n请确认\n是否删除？","删除",
											            JOptionPane.YES_NO_OPTION);
			//如果用户确认删除，执行删除
			if(yn==JOptionPane.YES_OPTION)
			{
				//定义返回删除条数变量
				int count=DBOperate.delUser(user);
				
				//提示删除成功，并提示删除了几条联系人信息
				JOptionPane.showMessageDialog(this,"删除成功！！！\n用户 - "+user+" - 共删除了"+count+"个联系人！！！","系统提示",JOptionPane.INFORMATION_MESSAGE);
				//提示用户不存在，强制退出系统
				JOptionPane.showMessageDialog(this,"系统强制退出！！！\n用户 - "+user+" - 不存在，请注册！！！",
						                            "系统提示",JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
		else
		{
			//提示验证失败
			JOptionPane.showMessageDialog(this,"密码验证失败！！！","错误",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//锁定方法
	public void lock()
	{	
		//关闭窗口
		this.dispose();
		
		//让系统托盘打开主面板不可用，锁定项改为‘解锁’
		lockAndUnlockMainFrame.setLabel("解  锁");
		showMainFrame.setEnabled(false);
		
		//改变锁定状态字符串变量
		lockState="locked";
		
		//弹出提示
		JOptionPane.showMessageDialog(this,"用户 - "+user+" - 的通讯录已锁定\n您可以通过右下角的系统托盘来解锁！",
				                            "系统提示",JOptionPane.INFORMATION_MESSAGE);
	}
	
	//打开帮助方法
	public void openHelp()
	{
		
	}
	
//	******************系统托盘相关方法***************
	
	//锁定和解锁方法
	public void lockAndUnlock()
	{	
		//判断锁定状态
		if(lockState=="locked")   //状态为已锁定，则执行解锁操作
		{
			//定义解锁码变量
			String pwd="";
			
			//弹出解锁密码输入窗口
			pwd=JOptionPane.showInputDialog(null,"请输入解锁密码\n即: "+this.user+" 的用户密码");
			
			if(DBOperate.check(this.user, pwd))
			{
				
				//锁码验证成功，执行解锁
				this.setVisible(true);
				
				//让系统托盘打开主面板可用，解锁项改为‘锁定’
				lockAndUnlockMainFrame.setLabel("锁  定");
				showMainFrame.setEnabled(true);
				
				//更改锁定状态字符串变量
				lockState="unlock";
			}
			else
			{
				//提示锁码错误
				JOptionPane.showMessageDialog(this,"锁码错误！！！","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		else    //状态为未锁定，则执行锁定操作
		{
			//调用前面已定义方法锁定
			this.lock();
		}
	}
	
	//注销用户方法
	public void waitUser()
	{
		int getSure=JOptionPane.showConfirmDialog(null, "你确认注销么？", "系统提示",
				      JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
		//判断用户选择情况
		if(getSure==JOptionPane.YES_OPTION)   //用户选择‘是’，执行注销操作
		{
			//关闭当前用户联系人管理窗口
			this.dispose();
			//声明重新登录窗口对象
			LoginForm loginframe=new LoginForm();
			//显示窗口
			loginframe.setVisible(true);
		}
	}

//	**************************监听器相关方法***************************

	//单选按钮监听方法
	@Override     //声明此方法为重写方法
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getSource()==jrbxm)
		{//按姓名查找 设置flag为true			
			this.searchByName=true;System.out.println("按姓名查找");
		}
		else if(e.getSource()==jrbbh)
		{//按编号查找，设置flag为false			
			this.searchByName=false;System.out.println("按编号查找");
		}
	}
	
	//行为监听方法
	@Override      //声明此方法为重写方法
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbInfo[4])
		{//调用监听添加分组的方法
			this.monitorAddGroupButton();				
		}
		else if(e.getSource()==jbInfo[5])
		{//调用监听删除分组的方法
			this.monitorDelGroupButton();
		}		
		else if(e.getSource()==jbInfo[3])
		{//打开图像文件路径				
			jfcPic.showOpenDialog(this);
			if(jfcPic.getSelectedFile()!=null)
			{
				jtfInfo[7].setText(""+jfcPic.getSelectedFile());
			}
		}
		else if(e.getSource()==jbInfo[6])
		{//打开照片路径
			jfcPho.setMultiSelectionEnabled(true);//允许多选
			jfcPho.showOpenDialog(this);//打开文件选择框
			if(jfcPho.getSelectedFiles()!=null)
			{
				File[] f=jfcPho.getSelectedFiles();//取得选择的文件列表
				String pathStr="";//记录迭代出的文件路径列表
				for(int i=0;i<f.length;i++)//迭代出文件列表的路径
				{
					pathStr+=f[i].getAbsolutePath()+";";//记录路径，分号分隔
				}
				jtfPhoto.setText(pathStr);//设置路径显示文本框内容
			}
		}		
		else if(e.getSource()==jba)
		{//添加联系人
			this.isInsert=true;//设置添加标志为true
			this.clearInfo();//清空信息面板
			jtfInfo[0].requestFocus();//编号文本框得到焦点
			cl.show(jpy,"Info");//显示信息面板
			this.setEditable(true);//设置信息面板中的控件可编辑
		}
		else if(e.getSource()==jbInfo[2])
		{//删除联系人按钮的监听
			this.monitorDelButton();//调用监听删除按钮的方法
		}		
		else if(e.getSource()==jbInfo[0])
		{//编辑按钮的监听
			this.isInsert=false;//设置是否添加联系人标志为false
			this.setEditable(true);//设置信息面板可编辑
			this.perNameBefor=jtfInfo[1].getText().trim();//得到编辑之前的名字
			this.perGroupBefor=(String)jcb.getSelectedItem();//得到编辑之前的分组		
		}
		else if(e.getSource()==jbInfo[1])
		{//保存按钮的监听
			this.monitorSaveButton();					
		}
		else if(e.getSource()==jbs||e.getSource()==jtfs)
		{//查找，按按钮或者在文本框里敲回车
			this.monitorSearchButton();
		}		
		else if(e.getSource()==jbInfo[7])
		{//调用监听上传照片按钮的方法
			try
			{
				this.monitorUploadButton();
			} 
			catch (InterruptedException e1)
			{
				
			}
		}
		else if(e.getSource()==jbInfo[8])
		{//删除照片按钮
			this.delPhoNode(jtfPhoto.getText().trim());
		}
		

		//系统托盘
		else if(e.getSource()==lockAndUnlockMainFrame)
		{//锁定
			//调用已定义方法进行操作
			this.lockAndUnlock();
		}
		else if(e.getSource()==waitUser)
		{//注销
			//调用已定义方法进行操作
			this.waitUser();
		}
		else if(e.getSource()==showMainFrame)
		{//打开主面板
			//判断窗口状态
			if(this.getExtendedState()==Frame.ICONIFIED)
			{
				//窗口是最小化，则让其还原
				this.setExtendedState(Frame.NORMAL);
			}
			else
			{
				//窗口是关闭状态，则让其显示
				this.setVisible(true);
			}
		}
		else if(e.getSource()==currently)
		{//显示当前登陆的用户信息			
			trayIcon.displayMessage("信息","当前登陆的用户为 "+user, TrayIcon.MessageType.INFO);
		}
		else if(e.getSource()==exit)
		{//点击退出程序执行的动作，结束程序安全退出			
			System.exit(0);
		}
		else if(e.getSource()==trayIcon)
		{
			//双击托盘，判断窗口是否锁定来决定是否显示窗口
			
			//窗口未锁定，执行相应操作
			if(lockState.equals("unlock"))
			{
				//判断窗口状态
				if(this.getExtendedState()==Frame.ICONIFIED)
				{
					//窗口是最小化，则让其还原
					this.setExtendedState(Frame.NORMAL);
				}
				else
				{
					//窗口是关闭状态，则让其显示
					this.setVisible(true);
				}
			}
			//窗口已锁定，弹出解锁框
			else
			{
				//调用已定义解锁方法
				this.lockAndUnlock();
			}
		}
		
		//事件源为菜单选项，进行相应操作
		//修改密码菜单项
		else if(e.getSource()==alterPasswordItem)
		{	
			//调用已定义方法进行操作
			this.alterPassword();
		}
		//删除用户菜单项
		else if(e.getSource()==delUserItem)
		{		
			//调用已定义方法进行操作
			this.delUser();
		}
		//锁定菜单项
		else if(e.getSource()==lockItem)
		{	
			//调用已定义方法进行操作
			this.lock();
		}
		//注销菜单项
		else if(e.getSource()==cancelItem)
		{	
			//调用已定义方法进行操作
			this.waitUser();
		}
		//退出菜单项
		else if(e.getSource()==exitItem)
		{	
			System.exit(0);
		}
		//帮助菜单项
		else if(e.getSource()==helpItem)
		{	
			//调用已定义方法进行操作
			this.openHelp();
		}
		//关于菜单项
		else if(e.getSource()==aboutItem)
		{	
			final AboutFrame aboutframe=new AboutFrame(this,"关于",true);

			//添加鼠标事件来响应关于窗口的关闭
			aboutframe.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					//如果鼠标单击窗口，则窗口释放关闭
					aboutframe.dispose();
				}
			});

			//显示关于窗口
			aboutframe.setVisible(true);
		}
	}
	
	//主方法（便于分块测试）
	public static void main(String []args)
	{
		new MainFrame("wyy0025");
	}
}
class NodeValue
{
	String value;//节点字符串名称
	int classCode;// 0 根  1 分组  2 联系人 3 照片
	JLabel[] jla;//缓存图片
	
	public NodeValue(String value,int classCode)
	{//构造器
		this.value=value;
		this.classCode=classCode;
	}
	public String getValue()
	{//得到节点字符串名称
		return this.value;
	}
	public void setValue(String value)
	{//节点名称发生变化时修改节点名
		this.value=value;
	}
	@Override
	public String toString()
	{//重写toString方法
		return value;
	}
}

class MyBorder extends AbstractBorder
{//自定义边框类
	public void paintBorder(Component c,
                        Graphics g,
                        int x,
                        int y,
                        int width,
                        int height)
    {    
    	g.setColor(Color.white);//设置边框颜色为白色
    	g.drawRect(x,y,width-1,height-1);//画出边框
    	g.drawRect(x+1,y+1,width-3,height-3);//在画出边框里再画一个边框
    }
}
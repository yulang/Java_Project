package sources;

import java.awt.*;
import javax.swing.*;

public class AboutFrame extends JDialog
{
    ImageIcon icon1=new ImageIcon("images\\about.jpg");
    //创建两个同时显示文字与图像图标的标签
    JLabel jlabel1=new JLabel("",icon1,SwingConstants.CENTER); 
	
	//构造方法（参数：父窗口，标题，是否为模态对话框）
	public AboutFrame(JFrame owner,String title,boolean Modal)
	{
		super(owner,title,Modal);
		this.setSize(593,426);
		//让窗口不显示标题栏
		this.setUndecorated(true);
		//设置窗口背景色
		this.setBackground(Color.PINK);
		
		//获取屏幕尺寸
		Dimension screenSize=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=screenSize.width;
		int srceenHeight=screenSize.height;
		//设置窗口居于屏幕中央
		setLocation((screenWidth-getWidth())/2+10,(srceenHeight-getHeight())/2+47);
		
		//获取对话框的内容窗格
		Container contentPane=this.getContentPane();
		//设置jlabel1的相关属性
		jlabel1.setBounds(593,426,this.getWidth()/2,this.getHeight()/2);
		jlabel1.setBackground(Color.PINK);
		//加载jlabel1组件到内容窗格
		contentPane.add(jlabel1);
		//为窗口关闭响应退出事件
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
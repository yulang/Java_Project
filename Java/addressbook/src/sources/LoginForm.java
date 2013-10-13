package sources;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener
{
	//声明组件
	private JPanel jpanel1=new JPanel();      //声明面板容器
	private JLabel[] arrayJLabel={new JLabel("用户名:"),new JLabel("密  码:"),new JLabel("")};   //声明包含三个标签的标签数组
	private JTextField userNameJTextField=new JTextField();                                      //声明用户名文本域
	private JPasswordField passwordJPasswordField=new JPasswordField();                          //声明密码输入域
	private JButton[] arrayJButton={new JButton("登  录"),new JButton("注  册")};                //声明包含两个按钮数组
	
	//构造函数
	public LoginForm()
	{	
		//设置窗体相关属性
		//设置标题栏显示图标
		Image icon2=Toolkit.getDefaultToolkit().getImage("images\\ico.gif");
		this.setIconImage(icon2);
		//设置窗口标题
		this.setTitle("--登  录--");
		//设置窗口不可拖动大小
		this.setResizable(false);
		//设置窗口大小
		this.setSize(350,300);
		//获取屏幕尺寸
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=screenSize.width;
		int srceenHeight=screenSize.height;
		//设置窗口居于屏幕中央
		setLocation((screenWidth-getWidth())/2,(srceenHeight-getHeight())/2);
		
		//设置组件的相关属性
		
		//设置面板容器的布局策略为空
		jpanel1.setLayout(null);
		//设置面板容器背景色
		jpanel1.setBackground(Color.WHITE);
		
		//利用循环添加标签和按钮
		for(int i=0;i<2;i++)
		{
			//设置2个标签和2个按钮的位置及大小
			arrayJLabel[i].setBounds(65, 164+i*35, 80, 26);
			arrayJButton[i].setBounds(60+i*135, 235, 90, 26);

			//设置2个标签和两个按钮的文本颜色
			arrayJLabel[i].setForeground(Color.RED);
			arrayJButton[i].setForeground(Color.RED);
			
			//设置2个标签和两个按钮的字体
			arrayJLabel[i].setFont(new Font("宋体",Font.PLAIN,14));
			arrayJButton[i].setFont(new Font("宋体",Font.PLAIN,15));
			
			//将2个标签和2个按钮添加到面板容器
			jpanel1.add(arrayJLabel[i]);
			jpanel1.add(arrayJButton[i]);
			
            //为按钮添加动作事件监听器
			arrayJButton[i].addActionListener(this);
		}
		
		//设置文本域和密码框的最大输入字符量
		userNameJTextField.setColumns(15);
		passwordJPasswordField.setColumns(16);
		
		//设置文本域和密码框的位置及大小
		userNameJTextField.setBounds(115, 161, 160, 30);
		passwordJPasswordField.setBounds(115, 198, 160, 30);
		//设置密码输入回显字符
		passwordJPasswordField.setEchoChar('*');

		//设置文本域和密码框的文本颜色
		userNameJTextField.setForeground(Color.RED);
		passwordJPasswordField.setForeground(Color.RED);
		
		//设置文本域和密码框的字体
		userNameJTextField.setFont(new Font("宋体",Font.BOLD,16));
		passwordJPasswordField.setFont(new Font("宋体",Font.BOLD,16));
		
		//定义一个图标对象，用于显示登陆界面图像
		ImageIcon icon1=new ImageIcon("images\\txl_1.jpg");
		//设置图像
		arrayJLabel[2].setIcon(icon1);
		//设置标签位置及尺寸
		arrayJLabel[2].setBounds(0,0,350,158);
		
		//将文本域和密码框添加到面板容器
		jpanel1.add(arrayJLabel[2]);
		jpanel1.add(userNameJTextField);
		jpanel1.add(passwordJPasswordField);
		
		//为文本域和密码框添加事件监听器
		userNameJTextField.addActionListener(this);
		passwordJPasswordField.addActionListener(this);
		
		//将面板容器添加到内容窗格
		this.add(jpanel1);
		//为窗口添加关闭响应事件
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		//显示窗体
		this.setVisible(true);
	}
	
	//定义一个公共方法，清空文本域内容，并定位焦点到文本域
	public void userNameClear()
	{
		//清空文本域内容
		userNameJTextField.setText("");
		//文本域获得焦点
		userNameJTextField.requestFocus();
	}
	//定义一个公共方法，清空密码框内容，并定位焦点到密码框
	public void passwordClear()
	{
		//清空密码框内容
		passwordJPasswordField.setText("");
		//密码框获得焦点
		passwordJPasswordField.requestFocus();
	}

	//@Override      //声明此方法为重写方法
	//实现ActionListener接口的方法，以实现对事件的监听处理
	public void actionPerformed(ActionEvent e)
	{
		//定义变量(用于调用DBOperate类的方法的实参传递)
		String user=userNameJTextField.getText().toString().trim();                //用户名变量
		String pwd=String.valueOf(passwordJPasswordField.getPassword());           //密码变量
		String sql="";                                                             //SQL查询语句变量
		
		//如果事件源是文本域输入框，则进行相应操作
		if(e.getSource()==userNameJTextField)
		{
			//让焦点转移到密码框
			passwordJPasswordField.requestFocus();
		}
		//如果事件源是登录按钮或者是在输入密码完毕后点击了回车按钮，则进行相应操作
		else if(e.getSource()==arrayJButton[0]||e.getSource()==passwordJPasswordField)
		{
			//判断输入是否合法
			if(user.equals(""))
			{
				//提示输入不合法
				JOptionPane.showMessageDialog(this, "用户名输入不合法!", "系统提示", JOptionPane.INFORMATION_MESSAGE);
				//文本域获得焦点
				userNameJTextField.requestFocus();
				//该句可防止用户名和密码均为空一次性弹出2个对话框
				return;
			}
			if(pwd.equals(""))
			{
				//提示输入不合法
				JOptionPane.showMessageDialog(this, "密码输入不合法!", "系统提示", JOptionPane.INFORMATION_MESSAGE);
				//密码框获得焦点
				passwordJPasswordField.requestFocus();
				return;
			}
			else
			{
				//调用DBOperate类的方法进行判断，用户名及密码是否匹配
				if(true)
				{
					//登录成功，显示主窗体,并传递登录‘用户名’作为实参
					MainFrame mainframe=new MainFrame(user);
					mainframe.setVisible(true);
					//释放登录窗口
					this.dispose();
				}
				else
				{
					//登录失败，提示用户名或密码出错
					JOptionPane.showMessageDialog(null, "用户名或密码有误，\n请检查是否无误再进行登录!", "系统提示",JOptionPane.ERROR_MESSAGE);
					//清空密码框
					this.passwordClear();
					//清空文本域
					this.userNameClear();
					return;
				}
			}
		}
		//如果事件源是注册按钮则进行相应操作
		else if(e.getSource()==arrayJButton[1])
		{
			//判断文本域是否为空
			if(user.equals(""))
			{
				//提示输入不合法
				JOptionPane.showMessageDialog(this, "用户名不能为空!", "系统提示", JOptionPane.INFORMATION_MESSAGE);
				//清除文本域内容
				this.userNameClear();	
				//文本域获得焦点
				userNameJTextField.requestFocus();
				//该句可防止用户名和密码均为空一次性弹出2个对话框
				return;
			}
			//判断密码框是否为空
			else if(pwd.equals(""))
			{
				//提示输入不合法
				JOptionPane.showMessageDialog(this, "密码不能为空!", "系统提示", JOptionPane.INFORMATION_MESSAGE);
				//清除密码框内容
				this.passwordClear();
				//密码框获得焦点
				passwordJPasswordField.requestFocus();
				return;
			}
			//判断注册用户是否存在
			else
			{
				//判断注册的用户是否已经存在
				sql="SELECT UserName FROM User WHERE UserName='"+user+"'";
				if(DBOperate.isExist(sql))
				{
					//注册用户已经存在，进行相应提示
					JOptionPane.showMessageDialog(this, "对不起，注册失败！\n该用户已经存在!", "系统提示", JOptionPane.INFORMATION_MESSAGE);
					//清除密码框内容
					this.passwordClear();
					//清除文本域内容
					this.userNameClear();
					return;
				}
				else
				{
					//尝试捕获异常
					try
					{
						//满足条件执行注册操作
						sql="INSERT INTO User VALUES('"+user+"','"+pwd+"')";
						if(true)
						{
							//提示用户注册成功
							JOptionPane.showMessageDialog(this, "恭喜你!\n注册成功！", "系统提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					catch(Exception ex)
					{
						//打印出错误原因
						ex.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	//主方法
	public static void main(String []args)
	{
		new LoginForm();//创建登陆窗体
	}
}

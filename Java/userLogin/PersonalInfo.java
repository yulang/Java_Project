package StudentManegeSystem.userLogin;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
public class PersonalInfo extends JFrame {
	private JLabel[] title={
			new JLabel("学号"), new JLabel("学生姓名"),new JLabel("性别"),
			new JLabel("年龄"),new JLabel("家庭住址"),new JLabel("联系电话")
	};
	private InitializeData data=new InitializeData();
	private JPanel showInfo=new JPanel();
	private JPanel showButton=new JPanel();
	private JButton[]  bt={
			new JButton("查看"),new JButton("保存"),new JButton("返回主菜单")
	};
	private JScrollPane scroll;
	private String username;
	private static boolean showIf=false;
	private  ArrayList<DetailedStudentInfo> dsi=new ArrayList<DetailedStudentInfo>();
	private  ArrayList<Persons> ps=new ArrayList<Persons>(); 
	private final int HEIGHT=400,LENGTH=800;
	private void initialize(){
		dsi=(new InitializeData()).readDetailedData();
		try {
			ps=(new InitializeData()).readData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	private DetailedStudentInfo user=new DetailedStudentInfo();
	private static Persons userp=new Persons();
	private DetailedStudentInfo changedUser=new DetailedStudentInfo();
	private boolean ifStored=false;
	private  JTextField userInfo[]={new JTextField(),new JTextField(),new JTextField(),new JTextField(),
			new JTextField(),
			new JTextField()
	};
	
	private void change(){
		
		try {
			data.replaceDetailedInfo(dsi, changedUser, username);
			//(new InitializeData()).updatePersons(ps, userp, username);
			System.out.println(changedUser);
			System.out.println(username+"\n"+userp.getName());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "保存成功");
	}
	public PersonalInfo(final String username){
		super("信息查看");
		//if(showIf==false)
		initialize();
		
		
		this.username=username;
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(this.LENGTH,this.HEIGHT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int o=JOptionPane.showConfirmDialog(null, "是否回到主菜单?");
				if(o==JOptionPane.YES_OPTION){
					new MainMenu(userp);
					dispose();
				}
				else if(o==JOptionPane.NO_OPTION){
					dispose();
				}
			}
		});
		
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p=ge.getCenterPoint();
    	
    	this.setLocation(p.x-this.LENGTH/2,p.y-this.HEIGHT/2);
    	
    	
    	
		for(JButton b:bt){
			b.setFont(new Font("华文新魏",Font.BOLD  &Font.ITALIC,18));
			showButton.add(b);
		}
		add(showButton,BorderLayout.NORTH);
		showInfo.setLayout(new GridLayout(2,6,0,0));
		
		
	  
		for(JLabel l:title)
		{
			l.setFont(new Font("楷体",Font.CENTER_BASELINE,18));
			showInfo.add(l);
		}
		
		
		for(DetailedStudentInfo s:dsi){
			if(s.getUserName().equals(username))
			{
				user=s;
				System.out.println(user);
				changedUser=s;
				break;
			}
		}
		
		if(showIf==true){
			
			
			final JTextField userInfo[]={
	    			new JTextField(user.getStudentNumber()),new JTextField(user.getUserRealName()),new JTextField(user.getGender()),
	    			new JTextField(user.getStudentAge()),new JTextField(user.getLivingAdress()),
	    			new JTextField(user.getStudentPhoneNumber())
	                 };
	        showInfo.setLayout(new GridLayout(2,6));
			for(JLabel l:title)
				showInfo.add(l);
			
			for(final JTextField l:userInfo){
				l.setFont(new Font("Times New Roman",Font.ITALIC,20));
				l.setEditable(false);
				l.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						if(e.getClickCount()==2){
							l.setEditable(true);
							l.setToolTipText("中文字符为非法字符！");
						}
					}
				});
				
				showInfo.add(l);
				
			}
			userInfo[0].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e){
					System.out.println(userInfo[0].getText());
					try{
						Integer.parseInt(userInfo[0].getText());
					   changedUser.setStudentNumber(userInfo[0].getText());
					}
					catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请输入纯数字组成的学号！");
						userInfo[0].setText("0000");
					}
					userInfo[0].setEditable(false);
				}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				
			});
			userInfo[1].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e){
					System.out.println(userInfo[1].getText());
					changedUser.setUserRealName(userInfo[1].getText());
					for(Persons p1:ps){
						if(p1.getUsername().equals(username));
						{
							p1.setName(userInfo[1].getText());
							break;
						}
					}
					
					
					userInfo[1].setEditable(false);
				}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				
			});
			userInfo[2].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e){
					System.out.println(userInfo[2].getText());
					changedUser.setGender(userInfo[2].getText());
					userInfo[2].setEditable(false);
				}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				
			});
			userInfo[2].addKeyListener(new KeyListener(){
				public void keyTyped(KeyEvent e){}
				public void keyPressed(KeyEvent e){}
				public void keyReleased(KeyEvent e){
					if(e.getKeyCode()==KeyEvent.VK_M){
						userInfo[2].setText("male");
					}
					else if(e.getKeyCode()==KeyEvent.VK_F){
						userInfo[2].setText("female");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "请输入f(女)或者m(男)");
						userInfo[2].setText("male");
					}
				}
			});
			userInfo[3].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e){
					System.out.println(userInfo[3].getText());
					try{
						int age=Integer.parseInt(userInfo[3].getText());
						if(age>0&& age<100)
							changedUser.setStudentAge(userInfo[3].getText());
						else
						{	JOptionPane.showMessageDialog(null, "您输入的年龄不合法！");
							userInfo[3].setText("18");
						}
						
					}
					catch(Exception e2){
						JOptionPane.showMessageDialog(null, "您输入的字符不对!");
						userInfo[3].setText("18");
					}
					
					userInfo[3].setEditable(false);
				}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				
			});
			userInfo[4].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e){
					System.out.println(userInfo[4].getText());
					changedUser.setLivingAdress(userInfo[4].getText());
					userInfo[4].setEditable(false);
				}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				
			});
			userInfo[5].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e){
					System.out.println(userInfo[5].getText());
					changedUser.setStudentPhoneNumber(userInfo[5].getText());
					System.out.println(changedUser);
					userInfo[5].setEditable(false);
				}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				
			});
			for(int i=0;i<=4;i++)
			{
				final int j=i;
				userInfo[i].addKeyListener(new KeyListener(){
					public void keyPressed(KeyEvent e){}
					public void keyTyped(KeyEvent e){}
					public void keyReleased(KeyEvent e){
						
						if(e.getKeyCode()==KeyEvent.VK_TAB){
							userInfo[j+1].setEditable(true);
						}
					}
					
				});
			}
			scroll=new JScrollPane(showInfo);
			add(scroll,BorderLayout.CENTER);
			JOptionPane.showMessageDialog(null, "你可以双击以改变信息！");
		}
		
		bt[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				showIf=true;
				dispose();
				new PersonalInfo(username);
			}
		});
		bt[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null, "你将要保存以下信息")==JOptionPane.YES_OPTION);
				{
					
					change();
				}
			}
		});
		bt[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				for(Persons p:ps){
					if(p.getUsername().equals(username))
					{	new MainMenu(p);
						break;
					}
				}
				
			}
		});
		
		
	}
	                     			 
	
}

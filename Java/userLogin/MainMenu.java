package StudentManegeSystem.userLogin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainMenu extends JFrame{
	
	JPanel panel=new JPanel();
	private InitializeData data=new InitializeData();
	private JMenu[] menus={
			new JMenu("信息"),new JMenu("查询"),new JMenu("系统管理")
	};
	private JMenuItem[] item1={
			new JMenuItem("本人信息"),new JMenuItem("他人信息")
	};

	private JMenuItem[] item2={
			new JMenuItem("信息查询")
	};

	private JMenuItem[] item3={
			new JMenuItem("用户名修改"),new JMenuItem("密码修改")
	};

	private final int HEIGHT=500,LENGTH=464;
	private ArrayList<Persons> person=new ArrayList<Persons>();
	private ArrayList<DetailedStudentInfo> dsi=new ArrayList<DetailedStudentInfo>();
	private JTextField newUserName=new JTextField(8);
	
	private JLabel label1=new JLabel("您当前的用户名为："),
			label2=new JLabel("您要修改用户名为："),
			username=new JLabel("");
	private void changeUsername(Persons p,String username){
		
		for(Persons p1:person){
			if(p1==p){
				p1.setUserName(username);
			}
		}
			
		
	}
	
	public void setBack(){

		ImageIcon image=new ImageIcon("mainMenu.jpg");
		JLabel bg=new JLabel(image);
		bg.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		
		this.getLayeredPane().add(bg,new Integer(Integer.MIN_VALUE));
		JPanel jpe=(JPanel)this.getContentPane();
		jpe.setOpaque(false);
		
	}
	
	public MainMenu(final Persons p){
		//UIManager.put("PopupMenuUI", "CustomPopupMenuUI");
		dsi=(new InitializeData()).readDetailedData();
		for(DetailedStudentInfo d:dsi){
			if(d.getUserName().equals(p.getUsername())){
				this.setTitle("主菜单("+d.getUserRealName()+")");
			}
		}
		
		setBack();
		try {
			person=data.readData();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		for(int j=0;j<2;j++)
			menus[0].add(item1[j]);
		item1[0].addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseExited(MouseEvent e){
				
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				item1[0].setToolTipText("你将可以查看和编辑本人的信息。点击进入");
			}
		});
		item1[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				new PersonalInfo(p.getUsername());
			}
		});
		
		item1[1].addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseExited(MouseEvent e){
				
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				item1[1].setToolTipText("你将可以查看和编辑他人的信息。点击进入");
			}
		});
		item1[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				new AllInfoShow(p.getUsername(),"0",false,3);
			}
		});
		for(int j=0;j<1;j++)
			menus[1].add(item2[j]);
		
		item2[0].addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseExited(MouseEvent e){
				
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e){
				item2[0].setToolTipText("你将可以查看和编辑他人的信息。点击进入");
			}
		});
		item2[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					dispose();
					new SearchInfo(p.getUsername());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		item3[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				try{
				new ChangeUserName(p.getUsername());
				}
				catch(Exception u){}
			}
		});
		
		for(int j=0;j<2;j++)
			menus[2].add(item3[j]);
		
		JMenuBar menuBar=new JMenuBar();
		for(JMenu m:menus)
			menuBar.add(m);
		setJMenuBar(menuBar);
		
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p1=ge.getCenterPoint();
    	setLocation(p1.x-HEIGHT/2,p1.y-LENGTH/2);
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int o=JOptionPane.showConfirmDialog(null, "是否回到登陆界面？");
				if(o==JOptionPane.YES_OPTION){
					dispose();
					new JF_login();
					
				}
				else if(o==JOptionPane.NO_OPTION){
					dispose();
					System.exit(0);
				}
				else{}
			}
		});
    	setSize(LENGTH,HEIGHT);
    	
    	setBackground(Color.CYAN);
    	setVisible(true);
	}
	
}

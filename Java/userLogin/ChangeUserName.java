package StudentManegeSystem.userLogin;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
public class ChangeUserName extends JFrame{
	private JButton button=new JButton("修改");
	private JButton cancel=new JButton("回到主菜单");
	private JLabel label=new JLabel("您当前的用户名为:");
	private JLabel label2=new JLabel("请输入修改的用户名");
	private JLabel label_username;
	private JTextField changedUsertext=new JTextField();
	private ArrayList<DetailedStudentInfo> dsi;
	private ArrayList<Persons> ps;
	private InitializeData data=new InitializeData();
	private final int LENGTH=400,HEIGHT=300;
	
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	private void initialize() throws Exception{
		dsi=data.readDetailedData();
		ps=data.readData();
	}
	private void modefyUsername(String change) throws IOException{
		
		Iterator<Persons> itp=ps.iterator();
		System.out.println(change);
		while(itp.hasNext()){
			Persons p=itp.next();
			System.out.println(p.getUsername());
			if(p.getUsername().equals(change)){
				p.setUserName(change);
				System.out.println(p.getUsername());
			}
		}
		System.out.println(ps);
		data.Store(ps);
		Iterator<DetailedStudentInfo> itd=dsi.iterator();
		while(itd.hasNext()){
			DetailedStudentInfo d=itd.next();
			if(d.getUserName().equals(change)){
				d.setUserName(change);
				System.out.println(d.getUserName());
			}
		}
		data.StoreDetailed(dsi);
	}
	private String username;
	public ChangeUserName(String username1) throws Exception{
		super("修改用户名");
		this.initialize();
		this.username=username1;
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(this.LENGTH,this.HEIGHT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int o=JOptionPane.showConfirmDialog(null, "是否回到主菜单?");
				if(o==JOptionPane.YES_OPTION){
					try {
						initialize();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for(Persons p:ps){
						if(p.getUsername().equals(username)){
							new MainMenu(p);
							break;
						}
					}
					dispose();
				}
				else if(o==JOptionPane.NO_OPTION){
					dispose();
				}
			}
		});
		this.setResizable(false);
		
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p=ge.getCenterPoint();
    	this.setLocation(p.x-this.LENGTH/2,p.y-this.HEIGHT/2);
    	
    	panel1.setLayout(new GridLayout(1,2));
    	button.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			int m=JOptionPane.showConfirmDialog(null, "是否修改信息");
    			if(m==JOptionPane.YES_OPTION){
    				try {
    					
						modefyUsername(changedUsertext.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    		}
    	});
    	panel1.add(button);
    	panel1.add(cancel);
    	cancel.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			try {
					ps=data.readData();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			for(Persons p:ps){
    				if(p.getUsername().equals(username)){
    					dispose();
    					new MainMenu(p);
    					break;
    				}
    			}
    		}
    	});
    	add(panel1,BorderLayout.NORTH);
    	panel2.setLayout(new GridLayout(2,2));
    	panel2.add(label);
    	label.setFont(new Font("华文楷体",Font.BOLD,18));
    
    	label_username=new JLabel(username1);
    	label_username.setFont(new Font("times new roman",Font.BOLD,25));
    	panel2.add(this.label_username);
    	panel2.add(label2);
    	label2.setFont(new Font("华文楷体",Font.BOLD,18));
    	panel2.add(this.changedUsertext);
    	add(panel2,BorderLayout.CENTER);
	}
	public static void main(String[] args) throws Exception{
		new ChangeUserName("1111");
	}
}

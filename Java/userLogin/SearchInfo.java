package StudentManegeSystem.userLogin;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class SearchInfo extends JFrame{
	private JButton[] bt={
			new JButton("按姓名查询"),new JButton("按学号查询"),new JButton("按用户名查询")
	};
	
	private JPanel firstPanel=new JPanel(),
				   showPanel=new JPanel();
	private ArrayList<DetailedStudentInfo> dsi=new ArrayList<DetailedStudentInfo>();
	private ArrayList<Persons> ps=new ArrayList<Persons>();
	private InitializeData data=new InitializeData();
	private String username;
	private final int LENGTH=400,HEIGHT=300;
	private void initialize() throws Exception{
		dsi=data.readDetailedData();
		ps=data.readData();
		
		
	}
	public SearchInfo(final String username) throws Exception{
		
		this.setVisible(true);		
		this.setSize(this.LENGTH,this.HEIGHT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int o=JOptionPane.showConfirmDialog(null, "是否回到主菜单?");
				if(o==JOptionPane.YES_OPTION){
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
				else{}
			}
		});
		
		this.setResizable(false);
		this.username=username;
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p=ge.getCenterPoint();
    	this.setLocation(p.x-this.LENGTH/2,p.y-this.HEIGHT/2);
    	
    	firstPanel.setLayout(new GridLayout(3,1));
    	
    	for(int i=0;i<bt.length;i++)
    		firstPanel.add(bt[i]);
    	this.add(firstPanel);
    	
    	
    	Point p1=firstPanel.getLocationOnScreen();
    	showPanel.setBounds(p1.x, p1.y, LENGTH, HEIGHT);
    	
    	bt[0].addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			try {
    				dispose();
					new FilteredJList(0,username).startSearch();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    			
    		}
    	});
    	bt[1].addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			try {
    				dispose();
					new FilteredJList(1,username).startSearch();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    			
    		}
    	});
    	bt[2].addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			try {
    				dispose();
					new FilteredJList(2,username).startSearch();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    			
    		}
    	});
    	
	}
	public static void main(String[] args) throws Exception{
		new SearchInfo("1234");
	}
	
	
	
}

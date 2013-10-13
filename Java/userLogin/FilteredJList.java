package StudentManegeSystem.userLogin;

import javax.swing.*;

import java.util.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class FilteredJList extends JList{
      private FilterField filterField;
      private int DEFAULT_FIELD_WIDTH = 20;
      private String elementChosen;
      private FilterModel model;
      private String[] listItems;
      private int kind;
      private ArrayList<DetailedStudentInfo> data;
      private String username;
      public void getStrings(){
    	  this.initializeData();
    	  ArrayList<String> strs=new ArrayList<String>();
    	  
    	  if(kind==0){//按姓名查询
    		for(int i=0;i<data.size();i++){
    			strs.add(data.get(i).getUserRealName());
    		}
    	  }
    	  else if(kind==1){
    		  for(int i=0;i<data.size();i++){
    			  strs.add(data.get(i).getStudentNumber());
    		  }
    	  }
    	  else if(kind==2){
    		  for(int i=0;i<data.size();i++)
    			  strs.add(data.get(i).getUserName());
    	  }
    	  listItems=(String[])strs.toArray(new String[data.size()]);
      }
      public void initializeData(){
    	  try{
    		  data=new InitializeData().readDetailedData();
    	  }
    	  catch(Exception e){}
      }
      public FilteredJList(int kind,String username) 
      {
    	  
         super();
         this.username=username;
         this.kind=kind;
         model=new FilterModel();
         setModel(model);
         filterField = new FilterField(DEFAULT_FIELD_WIDTH);
         
     }
      public void setModel(ListModel m){
    	  if(!(m instanceof FilterModel)){
    		  throw new IllegalArgumentException();
    	  }
    	  super.setModel(m);
      }
      public void addItem(Object o){
    	  ((FilterModel)getModel()).addElement(o);
      }
      public JTextField getFilterField(){
    	  return filterField;
      }

      class FilterModel extends AbstractListModel{
		ArrayList items;
		ArrayList filterItems;
		public FilterModel(){
			super();
			
			items=new ArrayList();
			filterItems=new ArrayList();
		}
		public Object getElementAt(int index){
			if(index< filterItems.size()){
				return filterItems.get(index);
			}
			else 
				return null;
		}
		public int getSize(){
			return filterItems.size();
		}
		public void addElement(Object o){
			items.add(o);
			refilter();
		}
		public void refilter(){
			filterItems.clear();
			String term=getFilterField().getText();
			for(int i=0;i<items.size();i++){
				if(items.get(i).toString().indexOf(term,0)!=-1)
					filterItems.add(items.get(i));
			}
			getEle();
			fireContentsChanged(this,0,getSize());
		}
		public void getEle(){
			elementChosen=(String) model.getElementAt(0);
			System.out.println(elementChosen);
		}
	}
	class FilterField extends JTextField implements DocumentListener{
		public FilterField(int width){
			super(width);
			getDocument().addDocumentListener(this);
		}
		public void changedUpdate(DocumentEvent e){
			((FilterModel)getModel()).refilter();
		}
		public void insertUpdate(DocumentEvent e){
			((FilterModel)getModel()).refilter();
		}
		public void removeUpdate(DocumentEvent e){
			((FilterModel)getModel()).refilter();
		}
		
		
	}
	public void startSearch(){
		new SearchFrame();
	}
	class SearchFrame extends JFrame{
		private final int HEIGHT=300,LENGHT=300;
		private JButton button=new JButton("search");
		private JPanel panel=new JPanel();
		private String ele;
		private FilteredJList list=new FilteredJList(kind,username);
	
		public SearchFrame(){
			
			super("查询");
			ele=elementChosen;
			GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
	    	Point p=ge.getCenterPoint();
	    	setSize(this.LENGHT,this.HEIGHT);
	    	
	    	this.setLocation(p.x-this.getWidth()/2,p.y-this.getHeight()/2);
			
	    	getStrings();
			this.getContentPane().setLayout(new BorderLayout());
			
			
			for(int i=0;i<listItems.length;i++){
				list.addItem(listItems[i]);
			}
			JScrollPane pane=new JScrollPane(list,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getContentPane().add(pane,BorderLayout.CENTER);
			panel.setLayout(new GridLayout(1,2));
			panel.add(list.getFilterField());
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
					list.show();
				}
			});
			panel.add(button);
			getContentPane().add(panel,BorderLayout.NORTH);
			
			setVisible(true);
		}
	}
	public void show(){
		new AllInfoShow(username,elementChosen,true,kind);
		System.out.println(kind);
	}
    
	public static void main(String[] args)
	{
		new FilteredJList(0,"1212").startSearch();
	}
}


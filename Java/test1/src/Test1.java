import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
class DateTime{
 static Calendar today=Calendar.getInstance();
 static int getYear(){
   return today.get(today.YEAR);
     }
   static int getMonth(){
            return today.get(today.MONTH)+1;  //返回月份的方法
        }
   static int getDay(){
            return today.get(today.DATE);//返回日期的方法
        }  
   static int getMonDay(int year,int month){
        int days=31;
        if(month==4||month==6||month==9||month==11)
            days=30;
        if(month==2)
            if((year%4==0&&year%100!=0)||year%400==0)//返回月份的天数
              days=29;
            else
              days=28;
       return days;
  }
}
class YearMonth extends JPanel{
 private int year;
 private int month;
 private JLabel y1;
 private JLabel m1;
    JSpinner showYear;
    JSpinner showMonth;
    YearMonth(){
     ymInit();
     add(y1);
     add(showYear);
        add(m1);//加入月选择组件
        add(showMonth);    
      }
   void ymInit(){
     y1=new JLabel("年");
     year=DateTime.getYear();
     showYear=new JSpinner(new SpinnerNumberModel(year,0,10000,1));
     showYear.setEditor(new JSpinner.NumberEditor(showYear,"0000"));
     showYear.setPreferredSize(new Dimension(60,30));
     y1.setFont(new Font("TimesRomn",Font.BOLD,16));
     m1=new JLabel("月");
     month=DateTime.getMonth();
     showMonth=new JSpinner(new SpinnerNumberModel(month,0,13,1));
     showMonth.setEditor(new JSpinner.NumberEditor(showMonth,"00"));
     showMonth.setPreferredSize(new Dimension(60,30));
     m1.setFont(new Font("TimesRomn",Font.BOLD,16));
     //此处加入月选择的微调器，并进行对应设置
                           
   }
} 
class NotePane extends JPanel implements ActionListener{
    private int year,month,day;
    private JTextArea note;
    private JButton save,delete,export;
    private Hashtable table;
    private JLabel dateInfo;
    private File file;
    private JPanel buttonpane;
    NotePane(){
     super(new BorderLayout());
     noteInit();
     buttonInit();
     addEvent();
     add(dateInfo,"North");
     add(new JScrollPane(note));
        add(buttonpane,"South");    
    }
  private void noteInit(){
   year=DateTime.getYear();
   month=DateTime.getMonth();
   day=DateTime.getDay();
   //替换补充语句，进行颜色和字体设置
   dateInfo=new JLabel(year+"年"+month+"月"+day+"日",JLabel.CENTER); 
   table=new Hashtable();
   file=new File("Note.txt");
   initFile();
   note=new JTextArea();
   note.setFont(new Font("",0,14));
   note.setLineWrap(true);
     }
  private void buttonInit(){
     save=new JButton("保存日志");
     delete=new JButton("删除日志");
     export=new JButton("导出日志");
     //将按钮加入到按钮面板，并分别进行按钮文字的字体设置
     buttonpane=new JPanel();
     buttonpane.add(save);
     buttonpane.add(delete);
     buttonpane.add(export);
    
    
   }
  private void addEvent(){
  	//按钮事件侦听
 save.addActionListener(this); 
    delete.addActionListener(this);
    export.addActionListener(this);
  
    }
  private void initFile(){
  if(!file.exists()){
   try{
    FileOutputStream out=new FileOutputStream(file);
    ObjectOutputStream  objectOut=new ObjectOutputStream(out);
    objectOut.writeObject(table);
    objectOut.close();
    out.close();
       }catch(IOException e){} 
       }
     }
  public void actionPerformed(ActionEvent e){
     if(e.getSource()==save)
           save(year,month,day);
     else if(e.getSource()==delete)
           delete(year,month,day);
     else
       export();
     } 
  public void setDateInfo(int y,int m,int d){
  	//此处加入记事文本框上面的日期信息
        String s=y+"年"+m+"月"+d+"日";
         dateInfo.setText(s);  
        year=y;
        month=m;
        day=d;   
    } 
  public String getDateKey(){
    String s=""+year;
    if(month<10)s+="/0"+month;
    else s+="/"+month;
    if(day<10)s+="/0"+day;
    else s+="/"+day;
    return s;
   }
  public void refreshContent(int year,int month,int day ){
   String key=this.getDateKey();  
   try{
    FileInputStream in1=new FileInputStream(file);
    ObjectInputStream in2=new ObjectInputStream(in1);
    table=(Hashtable)in2.readObject();
    in1.close();
    in2.close();
   }catch(Exception ee){}
   if(table.containsKey(key))
    note.setText(table.get(key)+"");
   else
    note.setText("");
   }
  public void save(int year,int month,int day){
    String logContent=note.getText();
    String key=this.getDateKey();
    try{
     FileInputStream in1=new FileInputStream(file);
     ObjectInputStream in2=new ObjectInputStream(in1);
     table=(Hashtable)in2.readObject();
     in1.close();
     in2.close();
     table.put(key,logContent);
     FileOutputStream  out=new FileOutputStream(file);
     ObjectOutputStream ObjectOut=new ObjectOutputStream(out);
     ObjectOut.writeObject(table);
     ObjectOut.close();
     out.close();
       }catch(Exception ee){}
         String m=year+"年"+month+"月"+day+"日的日志已经保存";//保存日志
     JOptionPane.showMessageDialog(this,m);
   }
  public void delete(int year,int month,int day){
        String key=this.getDateKey();    
   if(table.containsKey(key)){
    String m="要删除"+year+"年"+month+"月"+day+"日的日志吗？";
//    JOptionPane.showMessageDialog询问，待用户确定后删除确定年月日的日志
       if(JOptionPane.showConfirmDialog(this,m)==0){
               table.remove(key);
       try{
        FileOutputStream out=new FileOutputStream(file);
        ObjectOutputStream objectOut=new ObjectOutputStream(out);
        objectOut.writeObject(table);
              objectOut.close();
        out.close();
        note.setText("");
        }catch(Exception ee){}                        
                  }
   }else{
     String m=year+"年"+month+"月"+day+"日"+"无记录";
     JOptionPane.showMessageDialog(this,m,"提示",JOptionPane.WARNING_MESSAGE);
     }
   }
  public void export(){
  	//将所有日记导出到一个文本文件
      File m;
    JFileChooser n=new JFileChooser();
     n.showSaveDialog(null);
     m=n.getSelectedFile();
  try{
         FileInputStream in1=new FileInputStream(file);
      ObjectInputStream in2=new ObjectInputStream(in1);
         table=(Hashtable) in2.readObject();
      in1.close();
      in2.close();
     }catch(Exception e){}
     TreeMap s=new TreeMap(table);
     Set maping=s.entrySet();
 if(m.exists()){
       m.delete();
    }
 else{
  try{  m.createNewFile();
      }catch(Exception e){}
    }
  for(Iterator i=maping.iterator();i.hasNext();)
   {
  Map.Entry me=(Map.Entry)i.next();
   try{
    FileOutputStream out=new FileOutputStream(m,true);
      BufferedOutputStream bout=new BufferedOutputStream(out);
      DataOutputStream dout=new DataOutputStream(bout);
      dout.writeUTF(me.getKey()+"\r\n\t"+me.getValue()+"\r\n");
      dout.close();
      }catch(Exception e){}
    }
   }
  }
  //月历
class MonthPane extends JPanel{
 static JTextField showDay[];
 int first,days;
 YearMonth ym;
 String[] week;
 JLabel[] title;
 int year,month,day;
 MonthPane(){
  super(new GridLayout(7,7,3,3));
  mcInit();
  for(int i=0;i<7;i++)
        add(title[i]);
  for(int i=0;i<42;i++)
       add(showDay[i]);
     arrangeNum(year,month);
 }
void mcInit(){
 year=DateTime.getYear();
 //此处月日和一月天数需从DateTime的方法获取，以下两条语句仅供初始调试
 month=DateTime.getMonth();
 day=DateTime.getDay();
 String week[]={"日","一","二","三","四","五","六"};
 title=new JLabel[7];
 for(int j=0;j<7;j++){
  title[j]=new JLabel();
  title[j].setText(week[j]);
  title[j].setBorder(BorderFactory.createEmptyBorder());
  title[j].setFont(new Font("",1,18));
}
 title[0].setForeground(Color.red);
 title[6].setForeground(Color.blue);
 showDay=new JTextField[42];
 for(int i=0;i<42;i++){
  showDay[i]=new JTextField();
  showDay[i].setFont(new Font("",0,14));
  showDay[i].setEditable(false);
 }
}
public void arrangeNum(int year,int month){
	//一个月的天数，此语句仅供调试，需被替代
 days=DateTime.getMonDay(year,month);
 Calendar c=Calendar.getInstance();
 c.set(year,month-1,1);
 first=c.get(Calendar.DAY_OF_WEEK)-1;
 for(int i=first,n=1;i<first+days;i++,n++){
  showDay[i].setText(""+n);
  if(n==day){
   showDay[i].setForeground(Color.green);
   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));
  }
  else{
   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12));
   showDay[i].setForeground(Color.black);
  }
 } 
 for(int i=days+first;i<42;i++)
         showDay[i].setText("");
    for(int i=first-1;i>=0;i--)
         showDay[i].setText("");  //showDay的其余文本框均置为空字符串
  }
}
class Motto extends JPanel{
  Motto(){
   JLabel L;
   L=new JLabel(new ImageIcon("1.jpg"));//个性图片
      L.setOpaque(false); 
   add(L);
  }  
 }
class LeftPane extends JPanel{
  YearMonth ym;
  MonthPane mp;
  Motto mo;
  JPanel lp;
  JSplitPane split;
  LeftPane(){
   super(new BorderLayout());
   ym=new YearMonth();
   mp=new MonthPane();
   mo=new Motto();
   lp=new JPanel(new BorderLayout());
   lp.add(ym,"South");
   lp.add(mp);
   lp.add(mo,"North");
   split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
   this.add(lp);
   this.add(split,"East");
  }
 } 
class NoteBook extends JFrame implements ChangeListener,MouseListener{
 int year,month,day;
 Calendar c;
 int first,days;
 LeftPane lp;
 NotePane np;
public NoteBook(){
 super("我的天天记事(Ver 3.0)");
 this.setDefaultCloseOperation(3);
 Container con=getContentPane();
 bookInit();
 addEvent();
 con.add(lp,"West");
 con.add(np);
 this.setBounds(150,250,630,400);
 this.setResizable(false);
 this.setVisible(true);
}  
void bookInit(){
 c=Calendar.getInstance();
 lp=new LeftPane();
 np=new NotePane();
 year=DateTime.getYear();
 //此处月日需从DateTime的方法获取，以下语句仅供初始调试，需被替代
 month=DateTime.getMonth();
 day=DateTime.getDay();
}  
void addEvent(){
	//此处加上影响日期的事件侦听(微调器、日期文本框)
	//提示微调器的数值改变事件名是ChangeEvent
    lp.ym.showMonth.addChangeListener(this);
    lp.ym.showYear.addChangeListener(this);
    for(int i=1;i<42;i++)
      MonthPane.showDay[i].addMouseListener(this);
 }  
public void stateChanged(ChangeEvent e){
	//微调器的事件处理
 int y=year;
    String m=lp.ym.showYear.getValue().toString();                   
 String n=lp.ym.showMonth.getValue().toString();
 year=Integer.parseInt(m);
 month=Integer.parseInt(n);
if(month>12){
  year=year+1;
  month=1;
  lp.ym.showMonth.setValue(new Integer(1));
     lp.ym.showYear.setValue(new Integer(y+1));
  }
else if(month<1){
   year=year-1;
   month=12;
   lp.ym.showMonth.setValue(new Integer(12));
   lp.ym.showYear.setValue(new Integer(y-1));
 } 
 lp.mp.arrangeNum(year,month);
 noteBookRefresh();   
}
void noteBookRefresh(){
	//年、月、日任何一个数据改变，均需刷新日记本(月历、日期显示和日记显示)
   lp.mp.arrangeNum(year,month);
   np.setDateInfo(year,month,day);
   np.refreshContent(year,month,day);   
}
public void mouseClicked(MouseEvent e){
 JTextField source=(JTextField)e.getSource();
 try{
  day=Integer.parseInt(source.getText());
  noteBookRefresh();
 }
 catch(Exception ee){}
}
public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e){}
public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}  
public static void main(String arg[]){
 new NoteBook();
 }
}
package StudentManegeSystem.userLogin;
import java.awt.*;

import javax.swing.*;

public class RichJLabel extends JLabel{
	private int tracking;
	public RichJLabel(String text,int tracking){
		super(text,JLabel.CENTER);
		this.tracking=tracking;
		setLeftShaow(2,2,Color.LIGHT_GRAY);
		setRightShadow(-3, -3, new Color(0xccccff));
		setForeground(Color.darkGray);
		setFont(getFont().deriveFont(140f));
		
	}
	
	private int left_x,left_y,right_x,right_y;
	private Color left_color,right_color;
	public void setLeftShaow(int x,int y, Color color){
		left_x=x;
		left_y=y;
		left_color=color;
	}
	public void setRightShadow(int x,int y ,Color color){
		right_x=x;
		right_y=y;
		right_color=color;
	}
	public Dimension getPreferredSize(){
		String text=getText();
		FontMetrics fm=this.getFontMetrics(getFont());
		int w=fm.stringWidth(text);
		w+=(text.length()-1)*tracking;
		w+=left_x+right_x;
		
		int h=fm.getHeight();
		h+=left_y+right_y;
		return new Dimension(w,h);
	}
	public void paintComponent(Graphics g){
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		char[] chars=getText().toCharArray();
		FontMetrics fm=this.getFontMetrics(getFont());
		int h=fm.getAscent();
		int x=0;
		int space=(this.getSize().width-chars.length)/2;
		for(int i=0;i<space;i++){
			g.drawString(" ", x, h);
		}
		for(int i=0;i<chars.length;i++){
			char ch=chars[i];
			int w=fm.charWidth(ch)+tracking;
			
			g.setColor(left_color);
			g.drawString(" "+chars[i], x-this.left_x, h-this.left_y);
			
			g.setColor(getForeground());
			
			g.drawString(" "+chars[i], x, h);
			x+=w;
			
		}
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
		
	}
	public static void main(String[] args){
		RichJLabel label=new RichJLabel("76",-3);
		JFrame frame=new JFrame("RichLabel hack");
		frame.getContentPane().add(label);
		frame.pack();
		frame.setVisible(true);
	}
	
}

package StudentManegeSystem.userLogin;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


class ShadowBorder extends AbstractBorder{
	int xoff,yoff;
	Insets insets;
	public ShadowBorder(int x,int y){
		this.xoff=x;
		this.yoff=y;
		insets=new Insets(0,0,xoff,yoff);
	}
	public Insets getBorderInsets(Component c){
		return insets;
	}
	public void paintBorder(Component comp,Graphics g,int x,int y,int width,int height){
		g.setColor(Color.black);
		g.translate(x, y);
		g.fillRect(width-xoff, yoff, xoff, height-yoff);
		g.fillRect(xoff, height-yoff, width-xoff, yoff);
		g.translate(-x, -y);
	}
}

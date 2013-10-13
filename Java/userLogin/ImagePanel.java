package StudentManegeSystem.userLogin;

	import javax.swing.*;
	import java.awt.image.*;
	import java.awt.*;
	public class ImagePanel extends JPanel{
		private Image img;
		public ImagePanel(Image img){
			this.img=img;
			Dimension size=new Dimension(img.getWidth(null),img.getHeight(null));
			setSize(size);
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setLayout(null);
		}
		

	}


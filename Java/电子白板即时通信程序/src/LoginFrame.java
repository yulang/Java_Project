import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener {

	private String path = "./img/Image-ccsm.png";
	private ImageIcon logo = new ImageIcon(path);
	private JLabel serIPLabel = null;
	private JLabel serPort = null;
	private JLabel nameLabel = null;
	private JTextField serIPText = null;
	private JTextField serPortText = null;
	private JTextField nameText = null;
	private JButton loginBut = null;
	private JButton logoutBut = null;
	private JButton lb = new JButton();

	public LoginFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		init();
		lb.setBounds(35, 20, 150, 180);
		lb.setIcon(logo);
		add(lb);
	}

	private void init() {
		getContentPane().setLayout(null);
		nameLabel = new JLabel("用户名：");
		// nameLabel.setPreferredSize(new Dimension(100, 30));
		nameLabel.setBounds(210, 30, 100, 30);
		getContentPane().add(nameLabel);
		nameText = new JTextField("myName");
		// nameText.setPreferredSize(new Dimension(100, 30));
		nameText.setBounds(270, 30, 100, 30);
		getContentPane().add(nameText);
		serIPLabel = new JLabel("IP:");
		// serIPLabel.setPreferredSize(new Dimension(100, 30));
		serIPLabel.setBounds(210, 70, 100, 30);
		getContentPane().add(serIPLabel);
		serIPText = new JTextField("127.0.0.1");
		// serIPText.setPreferredSize(new Dimension(100, 25));
		serIPText.setBounds(270, 70, 100, 30);
		getContentPane().add(serIPText);
		serPort = new JLabel("端口：");
		// serPort.setPreferredSize(new Dimension(100, 30));
		serPort.setBounds(210, 110, 100, 30);
		getContentPane().add(serPort);
		serPortText = new JTextField("1234");
		// serPortText.setPreferredSize(new Dimension(100, 25));
		serPortText.setBounds(270, 110, 100, 30);
		getContentPane().add(serPortText);
		loginBut = new JButton("确定");
		// loginBut.setPreferredSize(new Dimension(80, 30));
		loginBut.setBounds(210, 160, 80, 25);
		getContentPane().add(loginBut);
		loginBut.addActionListener(this);
		logoutBut = new JButton("取消");
		// logoutBut.setPreferredSize(new Dimension(80, 30));
		logoutBut.setBounds(300, 160, 80, 25);
		getContentPane().add(logoutBut);
		logoutBut.addActionListener(this);
		this.setTitle("Mok's Studio 网络电子白板客户端登录");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension(450, 250));
		this.setBounds(screenSize.width / 2 - 225, screenSize.height / 2 - 175,
				400, 250);
		this.setVisible(true);
		setResizable(false);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("确定")) {
			new WhiteBoardFrame(serIPText.getText().trim(), serPortText
					.getText().trim(), nameText.getText().trim());
			this.setVisible(false);
			this.dispose();
		} else {
			this.dispose();
		}
	}

	public static void main(String args[]) {
		new LoginFrame();
	}

}

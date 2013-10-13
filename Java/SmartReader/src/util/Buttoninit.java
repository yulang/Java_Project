package util;

import javax.swing.JButton;

/*
 * 用于初始化Button的类及方法
 * 
 */

public class Buttoninit {
	/*
	 * 初始化方法
	 * 传入要初始化的JButton
	 * 传入显示的提示文本
	 * 传入要调用的图片名称
	 */
	public static void init(JButton jb,String st,String pt){
		//初始化设置按钮不可用
		jb.setEnabled(false);
		jb.setToolTipText(st);
		jb.setIcon(CreatecdIcon.add(pt));
	}

}

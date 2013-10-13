package com.util;

import java.net.URL;

import javax.swing.ImageIcon;

import com.wsy.MainFrame;

public class CreatecdIcon {
	public static ImageIcon add(String ImageName){
		URL IconUrl = MainFrame.class.getResource("..//res/"+ImageName);
		ImageIcon icon=new ImageIcon(IconUrl);
		return icon;
	}
}

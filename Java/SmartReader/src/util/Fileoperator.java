package util;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;

import com.sun.pdfview.OutlineNode;
import com.sun.pdfview.PDFDestination;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFObject;
import com.sun.pdfview.PagePanel;
import com.sun.pdfview.ThumbPanel;
import com.sun.pdfview.action.GoToAction;
import com.sun.pdfview.action.PDFAction;
import util.Pagecontroller;
import main.MainFrame;
/*
 * @author 余浪
 * student number 11061105
 * 
 * 封装了操作文件的一些方法
 */
public class Fileoperator{
	public static void setup(PageFormat pformat){
		PrinterJob pjob = PrinterJob.getPrinterJob();
		pformat = pjob.pageDialog(pformat);
	}
	
	
}

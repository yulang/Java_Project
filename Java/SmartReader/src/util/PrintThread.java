package util;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

import main.MainFrame;

import com.sun.pdfview.PDFPrintPage;

/*
 * 打印线程
 * 代码参考网上
 */
public class PrintThread extends Thread{
	PDFPrintPage ptPages;
	PrinterJob ptPjob;
	
	public PrintThread(PDFPrintPage pages, PrinterJob pjob){
		ptPages = pages;
		ptPjob = pjob;
	}
	
	public void run(){
		try{
			ptPages.show(ptPjob);
			ptPjob.print();
		}catch(PrinterException pe){
			System.out.println("Printing Error:"+pe.getMessage());
		}
		ptPages.hide();
	}

}

package util;

import com.sun.pdfview.PDFFile;

/*
 * 封装用于向工具条返回当前页码及总页码的方法
 */
public class GetPage {
	public static String showPage (int curpage, PDFFile pdffile){
		String st = "("+(curpage +1) + "/" + pdffile.getNumPages()+")";
		return st;
	}

}

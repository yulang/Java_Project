package util;

import com.sun.pdfview.PDFFile;

/*
 * 封装控制页数的方法
 * 禁止不合法页数的出现
 * 
 * 2012.12.3
 * @author 余浪
 * student number 11061105
 */
public class Pagecontroller {
	public static int checkPage(int pagenum, PDFFile pdffile){
		int rst;
		if(pagenum<0){
			pagenum = 0;
		}
		else if (pagenum >= pdffile.getNumPages()){
			pagenum = pdffile.getNumPages()-1;			
		}
		rst = pagenum;
		return rst;
	}
	
	

}

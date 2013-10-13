package util;

public class CriteriatoNum {
	public static int CtoN (String s){
		if(s=="red")
			return 0;
		else if(s=="yellow")
			return 1;
		else if(s=="blue")
			return 2;
		else if(s=="small")
			return 3;
		else if(s=="middle")
			return 4;
		else if(s=="big")
			return 5;
		else if(s=="circle")
			return 6;
		else if(s=="square")
			return 7;
		else if(s=="rect")
			return 8;
		else return 9;
	}

}

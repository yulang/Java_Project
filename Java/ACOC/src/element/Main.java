package element;

import java.util.Vector;
import java.util.*;
import util.CriteriatoNum;
public class Main {
	private static final int NUM = 20;
	public static void main(String args[]){
		int i,id,color,size,shape;
		Card tmp;
		String criteria;
		Vector cards = new Vector();
		
		for(i=0;i<NUM;i++){
			System.out.println("input format: id color size shape.");
			Scanner input = new Scanner(System.in);
			id=input.nextInt();
			Scanner input1 = new Scanner(System.in);
			color=input1.nextInt();
			Scanner input2 = new Scanner(System.in);
			size=input2.nextInt();
			Scanner input3 = new Scanner(System.in);
			shape=input3.nextInt();
			Card c = new Card(id,color,size,shape);
			cards.add(c);
		}
		
		Scanner input4 = new Scanner(System.in);
		criteria = input4.next();
		Card.getCount(criteria);
		
		for(Iterator it = cards.iterator();it.hasNext();){
			tmp =(Card) it.next();
			if(tmp.getcolor() == CriteriatoNum.CtoN(criteria))
				System.out.println(tmp.getid());
			if(tmp.getshape() == CriteriatoNum.CtoN(criteria))
				System.out.println(tmp.getid());
			if(tmp.getsize() == CriteriatoNum.CtoN(criteria))
				System.out.println(tmp.getid());
		}
			
	}

}

package element;


public class Card {
	private static final int RED = 0, YELLOW = 1, BLUE = 2;
	private static final int SMALL = 3, MIDDLE = 4, BIG = 5;
	private static final int CIRCLE = 6, SQUARE = 7, RECT = 8;
	private static int redcount = 0, yellowcount = 0, bluecount =0;
	private static int smallcount = 0, middlecount = 0, bigcount =0;
	private static int circlecount = 0, squarecount = 0, rectcount = 0;
	private int color, size, shape;
	private int ID;
	
	public Card(int aID, int acolor, int asize, int ashape){
		this.setid(aID);
		this.setcolor(acolor);
		this.setsize(asize);
		this.setshape(ashape);
		if(acolor==RED)
			redcount++;
		else if(acolor==YELLOW)
			yellowcount++;
		else if(acolor==BLUE)
			bluecount++;
		else System.out.println("illegal attribute");
		if(asize==SMALL)
			smallcount++;
		else if(asize==MIDDLE)
			middlecount++;
		else if(asize==BIG)
			bigcount++;
		else System.out.println("illegal attribute");
		if(ashape==CIRCLE)
			circlecount++;
		else if(ashape==SQUARE	)
			squarecount++;
		else if(asize==RECT)
			rectcount++;
		else System.out.println("illegal attribute");
	}
	private void setid(int id){
		ID = id;
	}
	private void setcolor(int acolor){
		color = acolor;
	}
	private void setsize(int asize){
		size = asize;
	}
	private void setshape(int ashape){
		shape = ashape;
	}
	public int getcolor(){
		return this.color;
	}
	public int getid(){
		return this.ID;
	}
	public int getshape(){
		return this.shape;
	}
	public int getsize(){
		return this.size;
	}
	public static void getCount( String criteria){
		if(criteria == "red")
			System.out.println(redcount);
		if(criteria == "yellow")
			System.out.println(yellowcount);
		if(criteria == "blue")
			System.out.println(bluecount);
		if(criteria == "small")
			System.out.println(smallcount);
		if(criteria == "middle")
			System.out.println(middlecount);
		if(criteria == "big")
			System.out.println(bigcount);
		if(criteria == "square")
			System.out.println(squarecount);
		if(criteria == "circle")
			System.out.println(circlecount);
		if(criteria == "rect")
			System.out.println(rectcount);
	}

}

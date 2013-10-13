package StudentManegeSystem.userLogin;

public class Persons {
	private String name;
	private String passwd;
	private String Class;
	private double chinese;
	private double math;
	private double english;
	private String username;
	
	public String getUsername(){
		return username;
	}
	public String getName(){
		return name;
	}
	public String getPasswd(){
		return passwd;
	}
	
	public String getClassName(){
		return Class;
	}
	public double getChinese(){
		return chinese;
	}
	public double getMath(){
		return math;
	}
	public double getEnglish(){
		return english;
	}
	public double getAverageMark(){
		return (chinese+math+english)/3.0;
	}
	public void setUserName(String username){
		this.username=username;
	}
	
	
	public void setName(String name){
		this.name=name;
	}
	public void setPasswd(String passwd){
		this.passwd=passwd;
	}
	public void setClassName(String classname){
		this.Class=classname;
	}
	public void setChinese(double chinese)
	{
		this.chinese=chinese;
	}
	public void setMath(double math){
		this.math=math;
	}
	public void setEnglish(double english){
		this.english=english;
	}
	public String toString(){
		return getUsername()+" "+getName()+" "+String.valueOf(getPasswd())+" "
				+getClassName()+" "+getChinese()+" "+
				getMath()+" "+getEnglish();
	}
}

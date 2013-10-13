package StudentManegeSystem.userLogin;

public class DetailedStudentInfo {
	private String userName;
	private String userRealName;
	private String userpasswd;
	private String studentNumber;
	private String studentGender;
	private String studentAge;
	private String studentLivingAdress;
	private String studentPhoneNumber;
	
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String username){
		this.userName=username;
	}
	public String getUserPasswd(){
		return this.userpasswd;
		
	}
	public void setPasswd(String passwd){
		this.userpasswd=passwd;
	}
	public String getUserRealName(){
		return this.userRealName;
	}
	public void setUserRealName(String userName){
		this.userRealName=userName;
	}
	public String getStudentNumber(){
		return this.studentNumber;
	}
	public void setStudentNumber(String studentNumber){
		this.studentNumber=studentNumber;
	}
	
	public String getGender(){
		return this.studentGender;
	}
	public void setGender(String gender){
		this.studentGender=gender;
	}
	public String getLivingAdress(){
		return this.studentLivingAdress;
	}
	public void setLivingAdress(String adress){
		this.studentLivingAdress=adress;
	}
	public String getStudentAge(){
		return this.studentAge;
	}
	public void setStudentAge(String age){
		this.studentAge=age;
	}
	public String getStudentPhoneNumber(){
		return this.studentPhoneNumber;
	}
	public void setStudentPhoneNumber(String phoneNumber){
	    this.studentPhoneNumber=phoneNumber;
	}
	
	public String toString(){
		return this.userName+" "+this.userpasswd+" "+this.userRealName+" "+this.getGender()+" "+this.getStudentAge()+" "
				+this.studentNumber+" "+this.studentLivingAdress+" "+this.studentPhoneNumber;
	}
	
}

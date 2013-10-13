package database;

public class Member {
	int memID;
	String memName;
	String memSex;
	String memIdent;
	String memtele;
	String memAddr;
	int maxBorrow;
	int curBorrow;
	String passWord;
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemSex() {
		return memSex;
	}
	public void setMemSex(String memSex) {
		this.memSex = memSex;
	}
	public String getMemIdent() {
		return memIdent;
	}
	public void setMemIdent(String memIdent) {
		this.memIdent = memIdent;
	}
	public String getMemtele() {
		return memtele;
	}
	public void setMemtele(String memtele) {
		this.memtele = memtele;
	}
	public String getMemAddr() {
		return memAddr;
	}
	public void setMemAddr(String memAddr) {
		this.memAddr = memAddr;
	}
	public int getMaxBorrow() {
		return maxBorrow;
	}
	public void setMaxBorrow(int maxBorrow) {
		this.maxBorrow = maxBorrow;
	}
	public int getCurBorrow() {
		return curBorrow;
	}
	public void setCurBorrow(int curBorrow) {
		this.curBorrow = curBorrow;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}

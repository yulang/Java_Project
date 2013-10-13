package database;

public class Book {
	int bookId;
	String bookName;
	String recommend;
	int recmderID;
	String status;
	String borrowtime;
	String deadline;
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public int getRecmderID() {
		return recmderID;
	}

	public void setRecmderID(int recmderID) {
		this.recmderID = recmderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBorrowtime() {
		return borrowtime;
	}

	public void setBorrowtime(String borrowtime) {
		this.borrowtime = borrowtime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId){
		this.bookId = bookId;
	}


	

}

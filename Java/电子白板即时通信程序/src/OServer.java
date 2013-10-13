import java.io.*;
import java.net.*;

interface myDog extends java.io.Serializable {

	void setAge(int a);

	int getAge();

	void setName(String n);

	String getName();

	void run();

}

@SuppressWarnings("serial")
class dog implements myDog {

	private int age;
	private String name;

	public void setAge(int a) {
		age = a;
	}

	public int getAge() {
		return age;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public void run() {
		System.out.println("I can run fast!");
	}

}

class OServer {

	public static void main(String[] args) throws IOException {
		dog d = new dog();
		d.setAge(10);
		d.setName("Tom");
		System.out.println(d.getAge());
		System.out.println(d.getName());
		d.run();
		ServerSocket s = new ServerSocket(1234);
		Socket ss = s.accept();
		OutputStream out = ss.getOutputStream();
		BufferedOutputStream b = new BufferedOutputStream(out);
		ObjectOutputStream oo = new ObjectOutputStream(b);
		oo.writeObject(d);
		oo.flush();
	}

}
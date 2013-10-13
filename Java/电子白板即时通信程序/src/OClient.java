import java.io.*;
import java.net.*;

class OClient {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		Socket s = new Socket("127.0.0.1", 1234);
		InputStream in = s.getInputStream();
		BufferedInputStream input = new BufferedInputStream(in);
		ObjectInputStream inn = new ObjectInputStream(input);
		dog d = (dog) inn.readObject();
		System.out.println("client:" + d.getAge());
		System.out.println("client:" + d.getName());
		d.run();
	}

}
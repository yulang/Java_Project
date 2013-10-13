import java.io.*;
import java.net.*;
import java.util.*;

class WhiteBoardServer extends Thread {

	public static int port = 1234;
	static PaintedObjects paintedObjects = new PaintedObjects();
	static Vector rlineVector = new Vector();
	static Vector streamVector = new Vector();
	Socket socket;

	public WhiteBoardServer(Socket socket) {
		System.out.println("Accepted from " + socket.getInetAddress() + ".");
		this.socket = socket;
	}

	public void run() {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			outputStream.flush();
			ObjectInputStream inputStream = new ObjectInputStream(
					new BufferedInputStream(socket.getInputStream()));
			handle(inputStream, outputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			System.out.println("�� " + socket.getInetAddress() + " �Ͽ�");
			try {
				socket.close();
			} catch (IOException ignored) {
			}
		}
	}

	void handle(ObjectInputStream inputStream, ObjectOutputStream outputStream)
			throws IOException, ClassNotFoundException {
		try {
			synchronized (streamVector) {
				// �����ͻ���ͨ����
				streamVector.addElement(outputStream);
				outputStream.writeObject(new LoginMessage(
						((PaintedObjects) paintedObjects.clone()),
						((Vector) rlineVector.clone()), streamVector
								.indexOf(outputStream)));
				System.out.println("LoginMessage");
				outputStream.flush();
			}
			handleMessage(inputStream);
		} finally {
			streamVector.removeElement(outputStream);
		}
	}

	void handleMessage(ObjectInputStream inputStream) throws IOException,
			ClassNotFoundException {
		while (true) {
			Object message = inputStream.readObject();
			if (message instanceof IMessage) {
				message = (IMessage) message;
				if (message instanceof ClientAddObjMsg) {
					Object obj = ((ClientAddObjMsg) message).getObj();
					synchronized (streamVector) {
						Object id = paintedObjects.add(obj);
						notifyAll(new ServerAddObjMessage(id, obj));
					}
				} else if (message instanceof ClientReplaceObjMsg) {
					Object oldID = ((ClientReplaceObjMsg) message).getId();
					if (oldID != null) {
						Object element = ((ClientReplaceObjMsg) message)
								.getElement();
						synchronized (paintedObjects) {
							Object id = paintedObjects.replaceObject(oldID,
									element);
							if (id != null) {
								notifyAll(new ServerReplacedMessage(oldID, id,
										element));
							}
						}
					}
				} else if (message instanceof ClientQuitMsg) {
					System.out.println("�յ��˳���Ϣ.....");
					return;
				} else {
					System.out.println("Unknown message: " + message);
				}
			} else if (message instanceof ClientAddRanLineMsg) {
				rlineVector.addElement(((ClientAddRanLineMsg) message)
						.getLine());
				NotifyRanLine((ClientAddRanLineMsg) message);
			}
		}
	}

	static void addOutputStream(ObjectOutputStream out) {
		streamVector.addElement(out);
	}

	static void rmvOutputStream(ObjectOutputStream out) {
		streamVector.removeElement(out);
	}

	static void NotifyRanLine(ClientAddRanLineMsg message) {
		System.out.println("Broadcast addRandomLineMsg to "
				+ streamVector.size() + " recipients.");
		for (int i = 0; i < streamVector.size(); ++i) {
			ObjectOutputStream out = (ObjectOutputStream) streamVector
					.elementAt(i);
			try {
				out.writeObject(message);
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("����ClientAddRanLineMsg��Ϣʧ�ܣ�");
			}
		}
	}

	static void notifyAll(IMessage message) {
		System.out.println("Broadcast " + message.getClass().getName() + " to "
				+ streamVector.size() + " recipients.");
		for (int i = 0; i < streamVector.size(); ++i) {
			ObjectOutputStream out = (ObjectOutputStream) streamVector
					.elementAt(i);
			try {
				out.writeObject(message);
				out.flush();
			} catch (Exception ex) {
				System.out.println("failed to send message");
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			try {
				WhiteBoardServer wbServer = new WhiteBoardServer(
						serverSocket.accept());
				wbServer.start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}

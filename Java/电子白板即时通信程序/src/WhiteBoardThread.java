import java.io.*;
import java.net.*;

class WhiteBoardThread extends Thread{
 public Socket socket;
 protected ObjectInputStream inputStream;
 protected ObjectOutputStream outputStream;
 public WhiteBoardPanel whiteBoard;
 public WhiteBoardThread(Socket wbsocket,WhiteBoardPanel whiteBoard){
   try {
			socket = wbsocket;
			this.whiteBoard = whiteBoard;
			inputStream = new ObjectInputStream(new BufferedInputStream(
					wbsocket.getInputStream()));
			LoginMessage loginMsg = (LoginMessage) inputStream.readObject();
			WhiteBoardPanel.elements = loginMsg.getList();
			WhiteBoardPanel.ranLineVector = loginMsg.getRlines();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

public void run () {
        try {
          readMessage();
        } catch (Exception ex) {
          ex.printStackTrace ();
        } finally {
          try {
            socket.close ();
          } catch (IOException ignored) {
          }
        }
       }

  void readMessage () throws IOException, ClassNotFoundException {
          while (true) {
			Object message = inputStream.readObject();
			if (message instanceof IMessage) {
				message = (IMessage) message;
				if (message instanceof ServerAddObjMessage) {
					Object id = ((ServerAddObjMessage) message).getID();
					Object element = ((ServerAddObjMessage) message)
							.getElement();
					WhiteBoardPanel.elements.addElementWithID(id, element);
					whiteBoard.repaint();
				} else if (message instanceof ServerReplacedMessage) {
					synchronized (WhiteBoardPanel.elements) {
						Object oldID = ((ServerReplacedMessage) message)
								.getOldID();
						Object id = ((ServerReplacedMessage) message).getID();
						Object element = ((ServerReplacedMessage) message)
								.getObject();
						boolean success = WhiteBoardPanel.elements
								.replaceOjbWithID(oldID, id, element);
						System.out.println("replace " + success);
						whiteBoard.repaint();
					}
				} else {
					System.err.println("δ֪��Ϣ: " + message);
				}
			} else if (message instanceof ClientAddRanLineMsg) {
				WhiteBoardPanel.ranLineVector
						.addElement(((ClientAddRanLineMsg) message).getLine());
				whiteBoard.repaint();
			}
		}
	}
}

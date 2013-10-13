import java.io.*;
import java.util.*;

interface IMessage extends Serializable {
}

@SuppressWarnings("serial")
class LoginMessage implements IMessage {

	PaintedObjects paintedOjbs;
	int id;
	Vector<?> randLineVector;

	public LoginMessage(PaintedObjects list, Vector<?> randLines, int id) {
		this.paintedOjbs = list;
		this.randLineVector = randLines;
		this.id = id;
	}

	public PaintedObjects getList() {
		return paintedOjbs;
	}

	public Vector<?> getRlines() {
		return randLineVector;
	}

	public int getId() {
		return id;
	}

}
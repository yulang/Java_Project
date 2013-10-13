import java.io.*;

@SuppressWarnings("serial")
class ClientAddRanLineMsg implements Serializable {

	RanLineObj element;

	public ClientAddRanLineMsg(RanLineObj line) {
		this.element = line;
	}

	public RanLineObj getLine() {
		return element;
	}

}
@SuppressWarnings("serial")
class ClientAddObjMsg implements IMessage {

	Object obj;

	public ClientAddObjMsg(Object obj) {
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}

}
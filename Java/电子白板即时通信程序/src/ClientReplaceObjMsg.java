@SuppressWarnings("serial")
class ClientReplaceObjMsg implements IMessage {

	Object id;
	Object object;

	public ClientReplaceObjMsg(Object object, Object id) {
		this.object = object;
		this.id = id;
	}

	public Object getElement() {
		return object;
	}

	public Object getId() {
		return id;
	}

}
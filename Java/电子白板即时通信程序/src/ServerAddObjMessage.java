@SuppressWarnings("serial")
class ServerAddObjMessage implements IMessage {
	
	Object id;
	Object object;

	public ServerAddObjMessage(Object id, Object element) {
		this.id = id;
		this.object = element;
	}

	public Object getID() {
		return id;
	}

	public Object getElement() {
		return object;
	}

}
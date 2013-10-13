@SuppressWarnings("serial")
class ServerReplacedMessage implements IMessage {

	Object oldId;
	Object id;
	Object object;

	public ServerReplacedMessage(Object oldID, Object id, Object element) {
		this.oldId = oldID;
		this.id = id;
		this.object = element;
	}

	public Object getOldID() {
		return oldId;
	}

	public Object getID() {
		return id;
	}

	public Object getObject() {
		return object;
	}

}
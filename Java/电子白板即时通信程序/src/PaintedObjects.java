import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
class PaintedObjects implements Serializable, Cloneable {

	protected Vector paintedObjVector = new Vector();
	protected Vector objIds = new Vector();

	public synchronized Object add(Object element) {
		Object id = new Integer(System.identityHashCode(element));
		addElementWithID(id, element);
		return id;
	}

	public synchronized void addElementWithID(Object id, Object element) {
		objIds.addElement(id);
		paintedObjVector.addElement(element);
	}

	public synchronized Object replaceObject(Object oldID, Object element) {
		int idx = objIds.indexOf(oldID);
		if (idx >= 0) {
			objIds.removeElementAt(idx);
			paintedObjVector.removeElementAt(idx);
			return add(element);
		} else {
			return null;
		}
	}

	public synchronized boolean replaceOjbWithID(Object oldID, Object id,
			Object element) {
		int index = objIds.indexOf(oldID);
		if (index >= 0) {
			objIds.removeElementAt(index);
			paintedObjVector.removeElementAt(index);
			objIds.addElement(id);
			paintedObjVector.addElement(element);
			return true;
		} else {
			return false;
		}
	}

	public synchronized Object getObjectId(Object element) {
		int idx = paintedObjVector.indexOf(element);
		return (idx >= 0) ? objIds.elementAt(idx) : null;
	}

	public synchronized Enumeration elements() {
		return ((Vector) paintedObjVector.clone()).elements();
	}

	public synchronized Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}

	public synchronized int size() {
		return paintedObjVector.size();
	}
}
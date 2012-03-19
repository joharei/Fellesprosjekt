package synclogic;

import java.util.ArrayList;
import java.util.List;
import model.SaveableClass;

public abstract class SynchronizationUnit {

	protected List<SyncListener> listeners;
	
	public SynchronizationUnit() {
		this.listeners = new ArrayList<SyncListener>();
	}
	
	public void addListener(SyncListener sl) {
		this.listeners.add(sl);
	}
	
	public void removeListener(SyncListener sl) {
		this.listeners.remove(sl);
	}
	
	public abstract void addToSendQueue(Object o);
	
	/**
	 * Returns the object defined by the given class and ID. Returns null if not found.
	 * 
	 * @param c		The objects class
	 * @param id	The objects ID
	 * @return		The object, or null if not found
	 */
	public Object getObjectFromID(SaveableClass c, String id) {
		for (SyncListener l : this.listeners) {
			if(l.getSaveableClass() == c && l.getObjectID() == id) {
				return l;
			}
		}
		return null;
	}
}

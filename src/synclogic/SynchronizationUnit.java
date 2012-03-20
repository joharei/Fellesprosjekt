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
	
	public abstract void addToSendQueue(String o);
	
	/**
	 * Returns the object defined by the given class and ID. Returns null if not found.
	 * If id is null, all objects of the given class will be returned
	 * 
	 * @param c		The object's class
	 * @param id	The object's ID, or null if all objects of the given class is requested
	 * @return		The object, or null if not found
	 */
	public Object getObjectFromID(SaveableClass c, String id) {
		List<Object> objects = new ArrayList<Object>();
		for (SyncListener l : this.listeners) {
			if(id == null) {
				if(l.getSaveableClass() == c) {
					objects.add(c);
				}
			} else if(l.getSaveableClass() == c && l.getObjectID().equals(id)) {
				return l;
			}
		}
		return objects.isEmpty() ? null : objects;
	}
}

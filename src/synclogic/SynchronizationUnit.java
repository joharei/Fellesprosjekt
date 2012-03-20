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
	 * Returns the object defined by the given class and ID in a List. Returns null if not found.
	 * If id is null, all objects of the given class will be returned in a list.
	 * 
	 * @param c		The object's class
	 * @param id	The object's ID, or null if all objects of the given class is requested
	 * @return		The object(s) in a list, or null if no matching element was found
	 */
	public List<SyncListener> getObjectsFromID(SaveableClass c, String id) {
		List<SyncListener> objects = new ArrayList<SyncListener>();
		for (SyncListener l : this.listeners) {
			if(id == null) {
				if(l.getSaveableClass() == c) {
					objects.add(l);
				}
			} else if(l.getSaveableClass() == c && l.getObjectID().equals(id)) {
				objects.add(l);
				return objects;
			}
		}
		return objects.isEmpty() ? null : objects;
	}
}

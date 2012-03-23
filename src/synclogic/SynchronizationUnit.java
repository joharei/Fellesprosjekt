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
	
	/**
	 * Returns the first object that matches the given class and id. Null if not found.
	 * 
	 * @param c		The object's class
	 * @param id	The object's ID
	 * @return		The matching object, or null of no matching object was found
	 */
	public SyncListener getObjectFromID(SaveableClass c, String id) {
		List<SyncListener> objects = getObjectsFromID(c, id);
		if(objects.isEmpty()) {
			return null;
		} else {
			return objects.get(0);
		}
	}
	
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
		return objects;
//		return objects.isEmpty() ? null : objects;
	}
	
	/**
	 * Notifies the changed object, telling it to 'clone' the newObject
	 * 
	 * @param c			The object's class
	 * @param objectID	The object's ID
	 * @param newObject	The new object
	 * @return			True if a matching object was found. False if not
	 */
	public boolean fire(SaveableClass c, String objectID, SyncListener newObject){
		SyncListener oldObject = this.getObjectFromID(c, objectID);
		if(oldObject == null) {
			return false;
		} else {
			oldObject.fire(oldObject.getSaveableClass(), newObject);
			return true;
		}
	}
	 /**
	  * Should be called if the received object does not exist (getObjectFromID returns null), 
	  * because fire cannot be used then.
	  * 
	  * @param o	The received object
	  */
	public abstract void addObject(SyncListener o);
}

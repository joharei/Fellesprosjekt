package synclogic;

import model.SaveableClass;

public interface SyncListener {

	/**
	 * Notifies the object that has been changed of the changes.
	 * 
	 * @param classType		The type of the object that has changed		
	 * @param newVersion	A copy of the object with the changes
	 * @return
	 */
	public boolean fire(SaveableClass classType, Object newVersion);
	
	public SaveableClass getSaveableClass();
	
	public String getObjectID();
}

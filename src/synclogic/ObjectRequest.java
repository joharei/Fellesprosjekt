package synclogic;

import model.SaveableClass;

public class ObjectRequest {

	private SaveableClass c;
	private String id;
	
	/**
	 * Creates a new ObjectRequest with the given arguments
	 * 
	 * @param c		The requested object's class
	 * @param id	The requested object's id, or null if all objects of this class is requested
	 */
	public ObjectRequest(SaveableClass c, String id) {
		this.c = c;
		this.id = id;
	}
	
	/**
	 * @return	The requested object's class
	 */
	public SaveableClass getSaveableClass() {
		return this.c;
	}
	
	/**
	 * @return The ID of the object, or null if all objects of the given class is requested.
	 */
	public String getObjectID() {
		return this.id;
	}
}

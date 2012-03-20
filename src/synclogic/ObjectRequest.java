package synclogic;

import model.SaveableClass;

public class ObjectRequest {

	private SaveableClass c;
	private String id;
	
	public ObjectRequest(SaveableClass c, String id) {
		this.c = c;
		this.id = id;
	}
	
	public SaveableClass getSaveableClass() {
		return this.c;
	}
	
	public String getID() {
		return this.id;
	}
}

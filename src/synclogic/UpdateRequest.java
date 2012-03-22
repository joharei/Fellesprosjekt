package synclogic;

import java.util.ArrayList;
import java.util.List;

public class UpdateRequest {

	private List<Object> objects;
	
	public static final String NAME_PROPERTY_OBJECT_LIST = "objreq";
	
	public UpdateRequest() {
		this.objects = new ArrayList<Object>();
	}
	
	public void addObject(Object o) {
		this.objects.add(o);
	}
	
	public void addAllObjects(List<Object> objects) {
		this.objects.addAll(objects);
	}
	
	public int size() {
		return objects.size();
	}
	
	public Object getObject(int i) {
		return this.objects.get(i);
	}
	
	public List<Object> getObjects() {
		return objects;
	}
	
}

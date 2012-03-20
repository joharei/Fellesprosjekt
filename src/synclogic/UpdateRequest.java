package synclogic;

import java.util.ArrayList;
import java.util.List;

public class UpdateRequest {

	private List<SyncListener> objects;
	
	public UpdateRequest() {
		this.objects = new ArrayList<SyncListener>();
	}
	
	public void addObject(SyncListener o) {
		this.objects.add(o);
	}
	
	public void addAllObjects(List<SyncListener> objects) {
		this.objects.addAll(objects);
	}
	
	public int size() {
		return objects.size();
	}
	
	public SyncListener getObject(int i) {
		return this.objects.get(i);
	}
	
}

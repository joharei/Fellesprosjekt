package synclogic;

import java.util.ArrayList;
import java.util.List;

public abstract class SynchronizationUnit {

	private List<SyncListener> listeners;
	
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
}

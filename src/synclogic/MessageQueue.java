package synclogic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue extends LinkedList<String> implements Queue<String> {
	private PropertyChangeSupport propChangeSupp;
	
	public MessageQueue(){
		super();
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	@Override
	public boolean add(String e) {
		this.propChangeSupp.firePropertyChange("list", this.peek(), e);
		return super.add(e);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.removePropertyChangeListener(listener);
	}
}

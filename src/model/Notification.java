package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import synclogic.SyncListener;

public class Notification implements SyncListener {
	private Invitation invitation;
	private NotificationType type;
	private String id;
	private User triggeredBy;
	private User recipient;
	private PropertyChangeSupport pcs;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "notification";
	public static final String NAME_PROPERTY_INVITATION = "invitation";
	public static final String NAME_PROPERTY_TYPE = "type";
	public static final String NAME_PROPERTY_ID = "id";
	public static final String NAME_PROPERTY_TRIGGERED_BY = "triggeredBy";
	public static final String NAME_PROPERTY_RECIPIENT = "recipient";
	
	
	public Notification(Invitation invitation, NotificationType type, String id, User triggeredBy){
		pcs = new PropertyChangeSupport(this);
		setInvitation(invitation);
		setType(type);
		setId(id);
		setTriggeredBy(triggeredBy);
	}
	
	public Invitation getInvitation() {
		return invitation;
	}
	public void setInvitation(Invitation invitation) {
		Invitation old = getInvitation();
		this.invitation = invitation;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_INVITATION, old, getInvitation()));
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		NotificationType old = getType();
		this.type = type;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_TYPE, old, getType()));
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		String old = getId();
		this.id = id;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_ID, old, getId()));
	}
	public User getTriggeredBy() {
		return triggeredBy;
	}
	public void setTriggeredBy(User triggeredBy) {
		User old = getTriggeredBy();
		this.triggeredBy = triggeredBy;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_TRIGGERED_BY, old, getTriggeredBy()));
		
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		boolean old = isRead();
		this.read = read;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_INVITATION, old, isRead()));
	}
	private boolean read;

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		Notification notif = (Notification) newVersion;
		setId(notif.getId());
		setInvitation(notif.getInvitation());
		setRead(notif.isRead());
		setTriggeredBy(notif.getTriggeredBy());
		setType(notif.getType());
		setRecipient(notif.getRecipient());
		System.out.println("Notification updated!");
	}

	@Override
	public SaveableClass getSaveableClass() {
		return SaveableClass.Notification;
	}

	@Override
	public String getObjectID() {
		return getId();
	}

	public void setRecipient(User recipient) {
		User old = getRecipient();
		this.recipient = recipient;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_RECIPIENT, old, getRecipient()));
	}

	public User getRecipient() {
		return recipient;
	}
}

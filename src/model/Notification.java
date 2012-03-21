package model;

import synclogic.SyncListener;

public class Notification implements SyncListener {
	private Invitation invitation;
	private NotificationType type;
	private int id;
	private User triggeredBy;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "notification";
	public static final String NAME_PROPERTY_INVITATION = "invitation";
	public static final String NAME_PROPERTY_TYPE = "type";
	public static final String NAME_PROPERTY_ID = "id";
	public static final String NAME_PROPERTY_TRIGGERED_BY = "triggeredBy";
	
	public Notification(Invitation invitation, NotificationType type, int id, User triggeredBy){
		setInvitation(invitation);
		setType(type);
		setId(id);
		setTriggeredBy(triggeredBy);
	}
	
	public Invitation getInvitation() {
		return invitation;
	}
	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}
	public String getId() {
		return "" + id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getTriggeredBy() {
		return triggeredBy;
	}
	public void setTriggeredBy(User triggeredBy) {
		this.triggeredBy = triggeredBy;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	private boolean read;

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		Notification notif = (Notification) newVersion;
		setId(Integer.parseInt(notif.getId()));
		setInvitation(notif.getInvitation());
		setRead(notif.isRead());
		setTriggeredBy(notif.getTriggeredBy());
		setType(notif.getType());
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
}

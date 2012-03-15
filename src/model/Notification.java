package model;

public class Notification {
	private Invitation invitation;
	private NotificationType type;
	private int id;
	private User triggeredBy;
	
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
	public int getId() {
		return id;
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
}

package model;

import synclogic.SyncListener;

public class Invitation implements SyncListener {
	private InvitationStatus status;
	private Meeting meeting;
	private User invitee;//TODO: Update class diagram
	private String id;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "invitation";
	public static final String NAME_PROPERTY_STATUS = "status";
	public static final String NAME_PROPERTY_MEETING = "meeting";
	public static final String NAME_PROPERTY_INVITEE = "invitee";
	
	public Invitation(InvitationStatus status, Meeting meeting, String id) {
		setStatus(status);
		setMeeting(meeting);
		setID(id);
	}
	
//TODO: Update class diagram, remove invitee
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return this.id;
	}
	
	public void setStatus(InvitationStatus status) {
		this.status = status;
	}
	public InvitationStatus getStatus() {
		return status;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setInvitee(User invitee) {
		this.invitee = invitee;
	}

	public User getInvitee() {
		return invitee;
	}

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		Invitation inv = (Invitation) newVersion;
		setID(inv.getID());
		setInvitee(inv.getInvitee());
		setMeeting(inv.getMeeting());
		setStatus(inv.getStatus());
		System.out.println("Invitation updated!");
	}

	@Override
	public SaveableClass getSaveableClass() {
		return SaveableClass.Invitation;
	}

	@Override
	public String getObjectID() {
		return getID();
	}
}

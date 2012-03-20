package model;

public class Invitation {
	private InvitationStatus status;
	private Meeting meeting;
	private User invitee;//TODO: Update class diagram
	private String id;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "invitation";
	public static final String NAME_PROPERTY_STATUS = "status";
	public static final String NAME_PROPERTY_MEETING = "meeting";
	public static final String NAME_PROPERTY_INVITEE = "invitee";
	
	public Invitation(InvitationStatus status, Meeting meeting) {
		setStatus(status);
		setMeeting(meeting);
	}
	
	/**
	 * Create an invitation
	 * @param status Invitation status
	 * @param meeting The meeting
	 * @param invitee The recipient user
	 */
	public Invitation(InvitationStatus status, Meeting meeting, User invitee) {
		setStatus(status);
		setMeeting(meeting);
		setInvitee(invitee);
	}
	
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
}

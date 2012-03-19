package model;

public class Invitation {
	private InvitationStatus status;
	private Meeting meeting;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "invitation";
	public static final String NAME_PROPERTY_STATUS = "status";
	public static final String NAME_PROPERTY_MEETING = "meeting";
	
	public Invitation(InvitationStatus status, Meeting meeting) {
		setStatus(status);
		setMeeting(meeting);
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
}

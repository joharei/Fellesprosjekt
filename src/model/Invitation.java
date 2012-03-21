package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import synclogic.SyncListener;

public class Invitation implements SyncListener {
	private InvitationStatus status;
	private Meeting meeting;
	private String id;
	private PropertyChangeSupport pcs;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "invitation";
	public static final String NAME_PROPERTY_STATUS = "status";
	public static final String NAME_PROPERTY_MEETING = "meeting";
	public static final String NAME_PROPERTY_ID = "id";
	
	public Invitation(InvitationStatus status, Meeting meeting, String id) {
		setStatus(status);
		setMeeting(meeting);
		setID(id);
		pcs = new PropertyChangeSupport(this);
	}
	
	public void setID(String id) {
		String old = getID();
		this.id = id;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_ID, old, id));
	}
	
	public String getID() {
		return this.id;
	}
	
	public void setStatus(InvitationStatus status) {
		InvitationStatus old = status;
		this.status = status;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_STATUS, old, getStatus()));
	}
	public InvitationStatus getStatus() {
		return status;
	}

	public void setMeeting(Meeting meeting) {
		Meeting old = getMeeting();
		this.meeting = meeting;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_MEETING, old, meeting));
	}

	public Meeting getMeeting() {
		return meeting;
	}

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		Invitation inv = (Invitation) newVersion;
		setID(inv.getID());
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

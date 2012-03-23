package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

public class Meeting extends Appointment {
	public Meeting(Date date, Date startTime, Date endTime, String description,
			String location, Room room, String id, User owner, boolean isDeleted) {
		super(date, startTime, endTime, description, location, room, id, owner,	isDeleted);
		pcs = new PropertyChangeSupport(this);
	}
	
	public Meeting(Date date, Date startTime, Date endTime, String description,
			String location, Room room, String id, User owner, boolean isDeleted,
			ArrayList<String> invites) {
		super(date, startTime, endTime, description, location, room, id, owner,
				isDeleted);
		pcs = new PropertyChangeSupport(this);
		setInvitations(invites);
	}

	private ArrayList<User> participants;
	private ArrayList<String> invitationIDs;
	private ArrayList<String> usersToInvite;
	private PropertyChangeSupport pcs;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "meeting";
	public static final String NAME_PROPERTY_PARTICIPANTS = "participants";
	public static final String NAME_PROPERTY_INVITATIONS = "invitations";
	public static final String NAME_PROPERTY_USERS_TO_INVITE = "userstoinvite";
	
	public void addParticipant(User participant) {
		ArrayList<User> old = getParticipants();
		this.participants.add(participant);
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_PARTICIPANTS, old, getParticipants()));
	}
	
	public void removeParticipant(User participant) {
		ArrayList<User> old = getParticipants();
		this.participants.remove(participant);
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_PARTICIPANTS, old, getParticipants()));
	}
	
	public void setParticipants(ArrayList<User> participants) {
		ArrayList<User> old = getParticipants();
		this.participants = participants;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_PARTICIPANTS, old, getParticipants()));
	}
	@SuppressWarnings("unchecked")
	public ArrayList<User> getParticipants() {
		return (ArrayList<User>) participants.clone();
	}
	
	public void addInvitation(Invitation inv) {
		this.addInvitation(inv.getID());
		
//		ArrayList<String> old = getInvitations();
//		this.invitationIDs.add(inv.getID());
//		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_INVITATIONS, old, getInvitations()));
	}
	
	public void addInvitation(String invitationID) {
		ArrayList<String> old = getInvitations();
		this.invitationIDs.add(invitationID);
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_INVITATIONS, old, getInvitations()));
	}
	
	public void removeInvitation(Invitation inv) {
		ArrayList<String> old = getInvitations();
		this.invitationIDs.remove(inv.getID());
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_INVITATIONS, old, getInvitations()));
	}
	

	public void setInvitations(ArrayList<String> invitationIDs) {
		ArrayList<String> old = getInvitations();
		this.invitationIDs = invitationIDs;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_INVITATIONS, old, getInvitations()));
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getInvitations() {
		return (ArrayList<String>) invitationIDs.clone();
	}

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		super.fire(classType, newVersion);
		Meeting meet = (Meeting) newVersion;
		setParticipants(meet.getParticipants());
		setInvitations(meet.getInvitations());
		System.out.println("Meeting changed");
	}

	@Override
	public SaveableClass getSaveableClass() {
		return SaveableClass.Meeting;
	}

	public void setUsersToInvite(ArrayList<String> usersToInvite) {
		ArrayList<String> old = getUsersToInvite();
		this.usersToInvite = usersToInvite;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_USERS_TO_INVITE, old, getUsersToInvite()));
	}

	public ArrayList<String> getUsersToInvite() {
		return usersToInvite;
	}

//	@Override
//	public String getObjectID() {
//		return super.getObjectID();
//	}
	
}

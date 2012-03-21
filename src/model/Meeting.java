package model;

import java.util.ArrayList;
import java.util.Date;

public class Meeting extends Appointment {
	public Meeting(Date date, Date startTime, Date endTime, String description,
			String location, Room room, int id, User owner, boolean isDeleted) {
		super(date, startTime, endTime, description, location, room, id, owner,
				isDeleted);
	}

	private ArrayList<User> participants;
	private ArrayList<String> invitationIDs;
	private ArrayList<String> usersToInvite;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "meeting";
	public static final String NAME_PROPERTY_PARTICIPANTS = "participants";
	public static final String NAME_PROPERTY_INVITATIONS = "invitations";
	public static final String NAME_PROPERTY_USERS_TO_INVITE = "userstoinvite";
	
	public void addParticipant(User participant) {
		this.participants.add(participant);
	}
	
	public void removeParticipant(User participant) {
		this.participants.remove(participant);
	}
	
	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}
	
	public void addInvitation(Invitation inv) {
		this.invitationIDs.add(inv.getID());
	}
	
	public void removeInvitation(Invitation inv) {
		this.invitationIDs.remove(inv.getID());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> getParticipants() {
		return (ArrayList<User>) participants.clone();
	}

	public void setInvitations(ArrayList<String> invitationIDs) {
		this.invitationIDs = invitationIDs;
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
		this.usersToInvite = usersToInvite;
	}

	public ArrayList<String> getUsersToInvite() {
		return usersToInvite;
	}

//	@Override
//	public String getObjectID() {
//		return super.getObjectID();
//	}
	
}

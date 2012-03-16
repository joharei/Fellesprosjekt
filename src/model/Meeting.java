package model;

import java.util.ArrayList;

public class Meeting extends Appointment {
	private ArrayList<User> participants;
	private ArrayList<Invitation> invitations;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "meeting";
	public static final String NAME_PROPERTY_PARTICIPANTS = "participants";
	public static final String NAME_PROPERTY_INVITATIONS = "invitations";
	
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
		this.invitations.add(inv);
	}
	
	public void removeInvitation(Invitation inv) {
		this.invitations.remove(inv);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> getParticipants() {
		return (ArrayList<User>) participants.clone();
	}

	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Invitation> getInvitations() {
		return (ArrayList<Invitation>) invitations.clone();
	}
}

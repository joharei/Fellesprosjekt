package model;

import java.util.ArrayList;

public class Meeting extends Appointment {
	private ArrayList<User> participants;
	private ArrayList<Invitation> invitations;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "meeting";
	public static final String NAME_PROPERTY_PARTICIPANTS = "participants";
	public static final String NAME_PROPERTY_INVITATIONS = "invitations";
}

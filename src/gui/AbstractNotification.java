package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;

import javax.swing.JDialog;
import javax.swing.JLabel;

import model.Invitation;
import model.Meeting;
import model.Notification;
import model.NotificationType;
import model.Room;

public abstract class AbstractNotification extends JDialog{
	
	private Invitation inv;
	
	private GridBagConstraints gb;
	private GridBagLayout layout;
	
	protected JLabel type, by, date, time, place, description;
	private JLabel byField, dateField, timeField, placeField, descField;
	
	private Meeting mt; 
	
	private String header;
	
	public AbstractNotification(Notification notification){
		
		this.setModal(true);
		this.setPreferredSize(new Dimension(500, 600));
		pack();
		
		this.inv = notification.getInvitation();
		this.mt = inv.getMeeting();
		
		gb = new GridBagConstraints();
		layout = new GridBagLayout();
		setLayout(layout);
		
		//title
		gb.gridx = 1;
		gb.gridy = 2;
		type = new JLabel(header);
		type.setFont(new Font("Helvetica LT Condensed", Font.BOLD, 20));
		gb.insets = new Insets(0, 0, 50, 0);
		setHeader();
		add(type, gb);
		
		
		//owner of the meeting
		gb.insets = new Insets(50, 0, 0, 0);

		gb.gridx = 1;
		gb.gridy = 2;
		by = new JLabel("By: ");
		add(by, gb);
		
		gb.gridx = 2;
		gb.gridy = 2;
		byField = new JLabel(mt.getOwner().getName());
		add(byField, gb);
		
		
		//date field
		gb.insets = new Insets(20, 0, 0, 0);

		gb.gridx = 1;
		gb.gridy = 3;
		date = new JLabel("Date: ");
		add(date, gb);
		
		gb.gridx = 2;
		gb.gridy = 3;
		DateFormat date = Meeting.getDateFormat();
		dateField = new JLabel(date.format(mt.getDate()));
		add(dateField, gb);
		
		
		//time field
		gb.insets = new Insets(20, 0, 0, 0);

		gb.gridx = 1;
		gb.gridy = 4;
		time = new JLabel("Time: ");
		add(time, gb);
		
		gb.gridx = 2;
		gb.gridy = 4;
		DateFormat time = Meeting.getTimeformat();
		timeField = new JLabel(time.format(mt.getStartTime()) + " - " + time.format(mt.getEndTime()));
		add(timeField, gb);
		
		
		//place field
		gb.insets = new Insets(20, 0, 0, 0);

		gb.gridx = 1;
		gb.gridy = 5;
		place = new JLabel("Place: ");
		add(place, gb);
		
		gb.gridx = 2;
		gb.gridy = 5;
		Room room = mt.getRoom();
		if(room == null){
			placeField = new JLabel(mt.getLocation());
		}
		else{
			placeField = new JLabel(room.getName());
		}
		add(placeField, gb);
		
		
		//description field
		gb.insets = new Insets(20, 0, 0, 0);

		gb.gridx = 1;
		gb.gridy = 6;
		description = new JLabel("Description: ");
		add(description, gb);
		
		gb.gridx = 2;
		gb.gridy = 6;
		descField = new JLabel(mt.getDescription());
		add(descField, gb);
		
		
		
	}
	
	public JDialog getInstance(Notification notification) {
		// TODO
		switch(notification.getType()) {
		case INVITATION_ACCEPTED:
			
		case INVITATION_RECEIVED:
			return new InvitationPromt(notification);
		case INVITATION_REJECTED:
			
		case INVITATION_REVOKED:
			
		case MEETING_CANCELLED:
			
		case MEETING_CHANGE_REJECTED:
			
		case MEETING_TIME_CHANGED:
			
		}
		return null;
	}
	
	protected abstract void setHeader();
	
	public GridBagConstraints getGridBagConstrints(){
		return this.gb;
		
	}
	

}

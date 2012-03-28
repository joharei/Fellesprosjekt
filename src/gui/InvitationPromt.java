package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import synclogic.SyncListener;

import model.Invitation;
import model.InvitationStatus;
import model.Meeting;
import model.Room;
import model.SaveableClass;
import model.User;

public class InvitationPromt extends JDialog{
	
	private Invitation inv;
	
	private GridBagConstraints gb;
	private GridBagLayout layout;
	
	private JLabel type, by, date, time, place, description;
	private JLabel byField, dateField, timeField, placeField, descField;
	private JButton accept, decline;
	
	private Meeting mt; 
	
	public InvitationPromt(Invitation inv) {
		this.inv = inv;
		mt = inv.getMeeting();
		
		this.setModal(true);
		this.setPreferredSize(new Dimension(500, 600));
		pack();
		
		gb = new GridBagConstraints();
		layout = new GridBagLayout();
		setLayout(layout);
		
		//title
		gb.gridx = 1;
		gb.gridy = 1;
		type = new JLabel("Meeting");
		type.setFont(new Font("Helvetica LT Condensed", Font.BOLD, 50));
		gb.insets = new Insets(0, 0, 50, 0);
		add(type, gb);
		
		
		//owner of the meeting
		gb.insets = new Insets(20, 0, 0, 0);

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
		
		//accept and decline buttons
		gb.insets = new Insets(20, 0, 0, 0);

		gb.gridx = 1;
		gb.gridy = 7;
		accept = new JButton("Accept");
		accept.addActionListener(new AcceptAction());
		add(accept, gb);
		
		gb.gridx = 2;
		gb.gridy = 7;
		decline = new JButton("Decline");
		decline.addActionListener(new DeclineAction());
		add(decline, gb);
		
	}
	
	class AcceptAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
//			XCal.getCSU().addListener(mt);
//			((GUICalender) GUICalender.thisCopy).buildView();
			
			inv.setStatus(InvitationStatus.ACCEPTED);
			XCal.getCSU().addToSendQueue(inv);
			
			dispose();
		}
		
	}
	
	class DeclineAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			inv.setStatus(InvitationStatus.REJECTED);
			XCal.getCSU().addToSendQueue(inv);
			dispose();
		}
		
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
////		Invitation inv = (Invitation) XCal.getCSU().getObjectFromID(SaveableClass.Invitation, "7");
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.HOUR_OF_DAY, 2);
//		Date date = cal.getTime();
//		
//		User user = new User("Tommy", "E", "tome", "123");
//		Meeting mt = new Meeting(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), date, "beskrivelse", "sted", null, "7", user, false);
//		Invitation inv = new Invitation(InvitationStatus.NOT_ANSWERED, mt , "7");
//		InvitationPromt invp = new InvitationPromt(inv);
//		invp.pack();
//		invp.setVisible(true);
//	}

}

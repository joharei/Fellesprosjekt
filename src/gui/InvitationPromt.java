package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Invitation;
import model.InvitationStatus;
import model.Meeting;
import model.Notification;
import model.NotificationType;
import model.User;

public class InvitationPromt extends AbstractNotification{
	
	private Invitation inv;
	private Meeting mt;
	private Notification not;
	
	private JButton accept, decline;
	private String header;
	
	private GridBagConstraints gb2 = getGridBagConstrints();
	
	public InvitationPromt(Notification not) {
		super(not);
		this.inv = not.getInvitation();
		this.mt = inv.getMeeting();
		this.not = not;
		
		//accept and decline buttons
		gb2.insets = new Insets(100, 0, 0, 0);
		gb2.gridx = 1;
		gb2.gridy = 7;
		if (inv.getStatus() == InvitationStatus.REVOKED) {
			JOptionPane.showMessageDialog(this, "This invitation has been revoked", "Invitation revoked!", JOptionPane.INFORMATION_MESSAGE);
			JButton close = new JButton("Close");
			close.addActionListener(new CloseAction());
			add(close, gb2);
		} else {
			accept = new JButton("Accept");
			gb2.insets = new Insets(50, 100, 0, 0);
			accept.addActionListener(new AcceptAction());
			add(accept, gb2);
			
			gb2.gridx = 2;
			gb2.gridy = 7;
			decline = new JButton("Decline");
			gb2.insets = new Insets(50, 0, 0, 100);
			decline.addActionListener(new DeclineAction());
			add(decline, gb2);
		}
	}
	
	class CloseAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			not.setRead(true);
			XCal.getCSU().addToSendQueue(not);
			dispose();
		}
		
	}
	
	class AcceptAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
//			XCal.getCSU().addListener(mt);
//			((GUICalender) GUICalender.thisCopy).buildView();
			not.setRead(true);
			inv.setStatus(InvitationStatus.ACCEPTED);
			XCal.getCSU().addToSendQueue(inv);
			XCal.getCSU().addToSendQueue(not);
			dispose();
		}
		
	}
	
	class DeclineAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			not.setRead(true);
			inv.setStatus(InvitationStatus.REJECTED);
			XCal.getCSU().addToSendQueue(inv);
			XCal.getCSU().addToSendQueue(not);
			dispose();
		}
		
	}

	@Override
	protected void setHeader() {
		// TODO Auto-generated method stub
		this.header = "Meeting Invitation";
		type.setText(header);
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
//		Notification not = new Notification(inv, NotificationType.INVITATION_RECEIVED, "7", user);
//		InvitationPromt invp = new InvitationPromt(not);
//		invp.pack();
//		invp.setVisible(true);
//	}

}

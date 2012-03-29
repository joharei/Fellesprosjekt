package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import synclogic.SyncListener;

import model.Notification;
import model.SaveableClass;
import model.User;

public class GUILoggInInfo extends JPanel{
	private JLabel nameLabel;
	private JComboBox notificationBox;
	private List<Notification> notifications;
	private GridBagConstraints c = new GridBagConstraints();
	
	public GUILoggInInfo(){
		
		setLayout(new GridBagLayout());
		this.notifications = new ArrayList<Notification>();
		nameLabel = new JLabel();
		nameLabel.setText(getName());
		c.gridx=1;
		c.gridy=1;
		add(nameLabel,c);
		
//		ansattNrLabel = new JLabel();
//		ansattNrLabel.setText(getAnsattNr());
//		c.gridx=1;
//		c.gridy=2;
//		add(ansattNrLabel,c);
		
//		notifications = new String[20];
//		notifications[0]=("Notifications");
//		for (int i = 1; i < notifications.length-1; i++) {
//			
//			try {
//				notifications[i]=(sendNotification(i));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//			}
//		}
		
		notificationBox = new JComboBox();
		c.gridx=1;
		c.gridy=4;
		add(notificationBox,c);
		notificationBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        getSelectedNotification();
		        
		    }
		});
		getSelectedNotification();
		
	}
	
	public void addNotification(Notification not) {
		this.notifications.add(not);
	}
	
	public void loadNotifications() {
		User loggedInUser = (User) XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText());
//		for (SyncListener listener : XCal.getCSU().getObjectsFromID(SaveableClass.Notification, null)) {
		for(SyncListener listener : ((User) XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText())).getNotifications()) {
			// Check if its this user's notification
			System.out.println("Fant notification: " + listener.getObjectID());
			if(!this.notifications.contains(listener)) {
				System.out.println("Added notification: " + listener.getObjectID());
				this.notifications.add((Notification) listener);
			} else {
				System.out.println("Did not add notification");
			}
		}
		DefaultComboBoxModel model = ((DefaultComboBoxModel)this.notificationBox.getModel());
		model.removeAllElements();
		System.out.println("SHOULD ADD 'Notifications'");
		model.addElement("Notifications       ");
		for (String text : this.getNotificationStrings()) {
			System.out.println("Should add notification: " + text);
			model.addElement(text);
		}
		System.out.println("DONE ADDING NOTIFICATIONS");
	}
	
	public String[] getNotificationStrings() {
		List<String> nots = new ArrayList<String>();
		for (Notification no : this.notifications) {
			if(no.isRead()) {
				continue;
			}
			switch(no.getType()) {
			case MEETING_CANCELLED:
				nots.add("Meeting cancelled");
				break;
			case INVITATION_RECEIVED:
				nots.add("Invitation received");
				break;
			case INVITATION_ACCEPTED:
				nots.add("User accepted invitation");
				break;
			case INVITATION_REJECTED:
				nots.add("User rejected invitation");
				break;
			case INVITATION_REVOKED:
				nots.add("Invitation revoked");
				break;
			case MEETING_TIME_CHANGED:
				nots.add("Meeting time changed");
				break;
			case MEETING_CHANGE_REJECTED:
				nots.add("Meeting change rejected");
				break;
			}
//			try {
//				nots.add(no.getInvitation().getStatus().toString());
//			} catch (NullPointerException e) {
//				nots.add("Notification som kaster NullPointerException");
//				System.out.println("========== NOTIFICATION ==========");
//				System.out.println("ID: " + no.getObjectID());
//				System.out.println("Type: " + no.getType().toString());
//				System.out.println("Invitation: " + no.getInvitation());
//				if(no.getInvitation() != null) {
//					System.out.println("Invitation status: " + no.getInvitation().getStatus());
//				} else {
//					System.out.println("Invitataion var null");
//				}
//				System.out.println("========== END NOTIFICA ==========");
//			}
		}
		String[] arr = new String[nots.size()];
		int counter = 0;
		for (String string : nots) {
			arr[counter] = string;
			counter++;
			System.out.println("kjoerer...");
		}
		return arr;
//		return (String[]) nots.toArray();
	}
	
//	public void setNotificationText(){
//		AppointmentGui aGui = new AppointmentGui();
//		messageFromAppointment=aGui.descriptionField.getText();
//		System.out.println(messageFromAppointment);
//	}
//	public String sendNotification(int a){
//		
//		String message []={"Notifications","Meeting deleted","Invitation","Meeting declined","Meeting accepted"};
//		
//		return message[a];
//	}
	public void getSelectedNotification(){
		if(this.notificationBox.getSelectedItem() != null && !((String)this.notificationBox.getSelectedItem()).trim().equals("Notifications")) {
			JOptionPane.showMessageDialog(null,"Noe skal skje her!");
			// TODO
//			AbstractNotification.get
		}
		
//		Object value = notificationBox.getSelectedItem();
//		if(value.equals("Meeting deleted")){
//			JOptionPane.showMessageDialog(null,"Meeting"+""+" was deleted");
//		}
		//Her skal det skje noe mer naar man velger en notification
	}
//	private String getAnsattNr() {
//		// TODO Auto-generated method stub
//		String nr=" 00001";
//		String text="Employee Nr: ";
//		
//		return text+nr;
//	}
	
	public String getName(){
		//hent opp info fra Logg inn siden
		String info2 = XCal.usernameField.getText();
		for (User o : XCal.getCSU().getAllUsers()) {
			if (o.getUsername().equalsIgnoreCase(XCal.usernameField.getText())){
				return o.getFirstname() + " " + o.getSurname();
			}
		}
		
		return info2;
	}
}

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import model.Appointment;
import model.Meeting;
import model.Notification;
import model.Room;
import model.SaveableClass;
import model.User;
import model.XmlSerializerX;

import gui.GUICalender;
import gui.MeetingGui.AddButtonAction;
import gui.MeetingGui.RemoveButtonAction;

public class ChangeAppointmentGui extends AppointmentGui{
	
	/**
	 * This class is for editing or deleting a appointment created by a user
	 */
	
	private JButton change, newCancel, delete, add, remove;
	private GridBagConstraints gb;
	private Appointment app;
	private JLabel invite;
	private JList addPersons;
	private JScrollPane addPersonsScroll;
	
	
	public ChangeAppointmentGui(Appointment app){
		
		super();
		this.app = app;
		this.setModal(true);
		this.setPreferredSize(new Dimension(600,700));
		pack();
		
		gb = new GridBagConstraints();
		
		remove(create);
		remove(cancel);
		
		getCalIcon().setDate(app.getDate());
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(app.getStartTime());
		getStartTimeField().setSelectedIndex(startCal.get(Calendar.HOUR_OF_DAY));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(app.getEndTime());
		getEndTimeField().setSelectedIndex(endCal.get(Calendar.HOUR_OF_DAY)-startCal.get(Calendar.HOUR_OF_DAY)-1);
		getDescriptionField().setText(app.getDescription());
		getPlaceField1().setText(app.getLocation());
		getPlaceField2().setSelectedItem(app.getRoom());
		
		if(app instanceof Meeting){
			//invite
			gb.gridx = 0;
			gb.gridy = 5;
			invite = new JLabel("Invite: ");
			gb.insets = new Insets(10, 30, 50, 0);
			add(invite, gb);
			
			
			//add persons field
			gb.gridx = 1;
			gb.gridy = 5;
			addPersons = new JList();
			addPersons.setModel(new DefaultListModel());
			for (User user : XCal.getCSU().getAllUsers()) {
				for (Notification not : user.getNotifications()) {
					if(not.getInvitation().getMeeting().getObjectID().equals(app.getObjectID())){
						((DefaultListModel) addPersons.getModel()).addElement(user);
					}
				}
			}
			
			gb.insets = new Insets(48, 50, 0, 0);
			
			addPersonsScroll = new JScrollPane(addPersons);
			addPersonsScroll.setPreferredSize(new Dimension(170, 100));
			add(addPersonsScroll, gb);
			
			
			//add button
			gb.gridx = 2;
			gb.gridy = 5;
			add = new JButton("add");
			add.setPreferredSize(new Dimension(80, 30));
			gb.insets = new Insets(10, 0, 32, 0);
			add.addActionListener(new AddButtonAction());
			add(add, gb);
			
			//remove button
			gb.gridx = 2;
			gb.gridy = 5;
			remove = new JButton("remove");
			remove.setPreferredSize(new Dimension(80, 30));
			gb.insets = new Insets(45, 0, 0, 0);
			remove.addActionListener(new RemoveButtonAction());
			add(remove, gb);
			
		}
		
		
		
		//add change and new cancel buttons
		gb.gridx = 1;
		gb.gridy = 6;
		change = new JButton("Save");
		gb.insets = new Insets(75, 0, 0, 50);
		change.addActionListener(new ChangeAction());
		add(change, gb);
		
		
		gb.gridx = 1;
		gb.gridy = 6;
		newCancel = new JButton("cancel");
		gb.insets = new Insets(75, 100, 0, 0);
		newCancel.addActionListener(new CancelAction());
		add(newCancel, gb);
		
		//adding a delete button with action listener 
		gb.gridx = 1;
		gb.gridy = 7;
		if(app instanceof Meeting){
			delete = new JButton("Delete meeting");
		} else{
			delete = new JButton("Delete appointment");
		}
		gb.insets = new Insets(10, 30, 0, 0);
		delete.addActionListener(new DeleteAction());
		add(delete, gb);
	}
	
	class ChangeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			DefaultListModel model = (DefaultListModel) getAddPersons().getModel();
			JFrame frame = null;
			if(getCalIcon().getDate() == null || getDescriptionField().getText().isEmpty() || getEndTimeField().getSelectedIndex()-getStartTimeField().getSelectedIndex() == 0 || ((getPlaceField1().getText().isEmpty() || getPlaceField1().getText().equals("Type or click button"))
					&& getPlaceField2().getSelectedItem().equals("choose"))){
				JOptionPane.showMessageDialog(frame, "<HTML>Some fields are empty, please take care of the empty fields :) " );
			} else if (!app.getOwner().getUsername().equals(((User) XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText())).getUsername())){
				if(app instanceof Meeting){
					JOptionPane.showMessageDialog(frame, "<HTML>You are not the host of this meeting, and cannot change it.");
				} else{
					JOptionPane.showMessageDialog(frame, "<HTML>You are not the owner of this appointment, and cannot change it.");
				}
			}
			else{
				app.setDate(getCalIcon().getDate());
				Calendar cal = Calendar.getInstance();
				cal.setTime(getCalIcon().getDate());
				
				String start = (String) getStartTimeField().getSelectedItem();
				start = start.substring(0, 2);
				int startTime = Integer.parseInt(start);
				cal.set(Calendar.HOUR_OF_DAY, startTime);
				Date startT = cal.getTime();
				
				String end = (String) getEndTimeField().getSelectedItem();
				end = end.substring(0, 2);
				int endTime = Integer.parseInt(end);
				cal.set(Calendar.HOUR_OF_DAY, endTime);
				Date endT = cal.getTime();
				
				app.setStartTime(startT);
				app.setEndTime(endT);
				app.setDescription(getDescriptionField().getText());
				app.setLocation(getPlaceField1().getText());
				app.setRoom((Room)getPlaceField2().getSelectedItem());
				
				XCal.getCSU().addToSendQueue(app);
				
				((GUICalender) GUICalender.thisCopy).buildView();
				
				JOptionPane.showMessageDialog(frame, "<HTML>Changes saved.");

//				JOptionPane.showMessageDialog(frame, "<HTML>Changes saved. <P> Message has been sent to: " + getAddPersons().getModel() );
				
				
				setVisible(false);
			}
		}
	}
	
	class CancelAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFrame frame = null;
			Object[] options = {"Yes", "No"};
			int selection = JOptionPane.showOptionDialog(frame, "Are you sure you want to delete this appointment?", "Final confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
			if(selection == JOptionPane.YES_OPTION){
				if (app.getOwner().getObjectID().equalsIgnoreCase(XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText()).getObjectID())){
					app.setDeleted(true);
					XCal.getCSU().addToSendQueue(app);
					((GUICalender) GUICalender.thisCopy).buildView();
					JOptionPane.showMessageDialog(frame, "<HTML>Appointment deleted.");
				} else{
					JOptionPane.showMessageDialog(frame, "<HTML>You are not the owner of this appointment, and cannot delete it.");
				}
				setVisible(false);
			}
		}
		
	}
	
	class RemoveButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object[] userStrings = addPersons.getSelectedValues();
			DefaultListModel model = (DefaultListModel) addPersons.getModel();
			for(int i = 0; i<userStrings.length; i++){
				 model.removeElement(userStrings[i]);
			}
		}
		
	}
	
	class AddButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton b = (JButton) e.getSource();
			MeetingGui m = (MeetingGui) b.getParent().getParent().getParent().getParent();
			ListOfPersons2 selectPersons = new ListOfPersons2(m);
		}
		
	}
	

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		ChangeAppointmentGui gui = new ChangeAppointmentGui();
//		gui.pack();
//		gui.setVisible(true);
//
//	}

}

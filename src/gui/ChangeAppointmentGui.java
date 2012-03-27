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
import javax.swing.JOptionPane;

import model.Appointment;
import model.Room;
import model.SaveableClass;

import gui.GUICalender;;

public class ChangeAppointmentGui extends AppointmentGui{
	
	/**
	 * This class is for editing or deleting a appointment created by a user
	 */
	
	private JButton change, newCancel, delete;
	private GridBagConstraints gb;
	private Appointment app;
	
	
	public ChangeAppointmentGui(Appointment app){
		
		super();
		this.app = app;
		this.setModal(true);
		this.setPreferredSize(new Dimension(600,700));
		pack();
		
		gb = new GridBagConstraints();
		
//		remove(getNewCreate());
//		remove(getNewCancel());
		
		getCalIcon().setDate(app.getDate());
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(app.getStartTime());
		getStartTimeField().setSelectedIndex(startCal.get(Calendar.HOUR_OF_DAY));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(app.getEndTime());
		getEndTimeField().setSelectedItem(endCal.get(Calendar.HOUR_OF_DAY));
		getDescriptionField().setText(app.getDescription());
		getPlaceField1().setText(app.getLocation());
		getPlaceField2().setSelectedItem(app.getRoom());
//		getAddPersons();
		
		
		
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
		delete = new JButton("Delete Appointment");
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
			} else if (app.getOwner() != XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText())){
				
				JOptionPane.showMessageDialog(frame, "<HTML>You are not the owner of this appointment, and cannot change it.");
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

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import synclogic.RoomAvailabilityRequest;

import model.Appointment;
import model.Room;
import model.SaveableClass;
import model.User;


import com.toedter.calendar.JDateChooser;

public class AppointmentGui extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel dateSelect, startTime, endTime, description, place;
	private JTextArea descriptionField;
	private JComboBox startTimeField, endTimeField, placeField2;
	protected JButton create, cancel;
	private JTextField placeField1;
	private JScrollPane descriptionScroll;
	private JPanel guiCalendar;
	
	private JDateChooser calIcon;
	
	protected GridBagConstraints gb;
	protected GridBagLayout gbLayout;
	
	
	public AppointmentGui(JPanel guiCalendar) {
		
		this.setModal(true);
		this.setPreferredSize(new Dimension(500,600));
		pack();
		this.guiCalendar = guiCalendar;
		
		gb = new GridBagConstraints();
		gbLayout = new GridBagLayout();
		setLayout(gbLayout);
		
		//date
		gb.gridx = 0;
		gb.gridy = 0;
		dateSelect = new JLabel("Select date: ");
		gb.insets = new Insets(0, 10, 0, 0);
		add(dateSelect, gb);
		
		
		//date field with calendar
		gb.gridx = 1;
		gb.gridy = 0;
		calIcon = new JDateChooser();
		calIcon.setPreferredSize(new Dimension(100,20));
		gb.insets = new Insets(0, 0, 0, 15);
		add(calIcon, gb);
		
		//start time label
		gb.gridx = 0;
		gb.gridy = 1;
		startTime = new JLabel("Start time: ");
		gb.insets = new Insets(50, 10, 0, 0);
		add(startTime, gb);
		
		//start time field
		gb.gridx = 1;
		gb.gridy = 1;
		startTimeField = new JComboBox();
		populateTextComboBox(0, 23, startTimeField);
		startTimeField.setPreferredSize(new Dimension(75, 20));
		startTimeField.addItemListener(new TimeSelectionAction());
		gb.insets = new Insets(50, 0, 0, 40);
		add(startTimeField, gb);
		
		//end time label
		gb.gridx = 0;
		gb.gridy = 2;
		endTime = new JLabel("End time: ");
		gb.insets = new Insets(10, 18, 0, 0);
		add(endTime, gb);
		
		//end time field
		gb.gridx = 1;
		gb.gridy = 2; 
		endTimeField = new JComboBox();
		populateTextComboBox(0, 23, endTimeField);
		endTimeField.setPreferredSize(new Dimension(75, 20));
		gb.insets = new Insets(10, 0, 0, 40);
		add(endTimeField, gb);
		
		//description
		gb.gridx = 0;
		gb.gridy = 3;
		description = new JLabel("Description: ");
		gb.insets = new Insets(0, 0, 100, 0);
		add(description, gb);
		
		//description field
		gb.gridx = 1;
		gb.gridy = 3;
		descriptionField = new JTextArea(10, 15);
		descriptionField.setLineWrap(true);
		gb.insets = new Insets(50, 50, 0, 0);
		descriptionField.setBounds(gb.gridx = 1, gb.gridy = 3, 0, 0);
		descriptionScroll = new JScrollPane(descriptionField);
		add(descriptionScroll, gb);
		
		//place
		gb.gridx = 0;
		gb.gridy = 4;
		place = new JLabel("Place: ");
		gb.insets = new Insets(10, 30, 0, 0);
		add(place, gb);
		
		//place text field
		gb.gridx = 1;
		gb.gridy = 4;
		placeField1 = new JTextField("Type or click button");
		placeField1.setColumns(15);
		gb.insets = new Insets(10, 50, 0, 0);
		placeField1.addFocusListener(new PlaceTextFieldAction());
		add(placeField1, gb);
		
		//place button field
		gb.gridx = 2;
		gb.gridy = 4;
		placeField2 = new JComboBox();
		placeField2.addItem("choose");
		
		
//		//dummy inputs
//		placeField2.addItem("G032");
//		placeField2.addItem("R8");
		
		
		gb.insets = new Insets(10, 0, 0, 0);
		placeField2.setPreferredSize(new Dimension(75, 20));
		add(placeField2, gb);
		
		//create button
		gb.gridx = 1;
		gb.gridy = 5;
		create = new JButton("Create");
		gb.insets = new Insets(75, 0, 0, 50);
		add(create, gb);
		
		//cancel button
		gb.gridx = 1;
		gb.gridy = 5;
		cancel = new JButton("Cancel");
		gb.insets = new Insets(75, 100, 0, 0);
		add(cancel, gb);
		addActionListeners();
	}
	public String sendNotification(){
		
		return descriptionField.getText();
	}
	
	public JDateChooser getCalIcon(){
		return calIcon;
	}
	public JComboBox getStartTimeField(){
		return startTimeField;
	}
	public JComboBox getEndTimeField(){
		return endTimeField;
	}
	public JTextArea getDescriptionField(){
		return descriptionField;
	}
	public JTextField getPlaceField1(){
		return placeField1;
	}
	public JComboBox getPlaceField2(){
		return placeField2;
	}
	
	
	public GridBagConstraints getGridBagConstraints(){
		return gb;
	}
	
	public GridBagLayout getGridBagLayout(){
		return gbLayout;
	}
	
	private void addActionListeners() {
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				dispose();
			}
		});
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(calIcon.getDate() == null || descriptionField.getText().isEmpty() || ((placeField1.getText().isEmpty() || placeField1.getText().equals("Type or click button"))
						&& placeField2.getSelectedItem().equals("choose"))){
					JFrame frame = null;
					JOptionPane.showMessageDialog(frame, "<HTML>Some fields are empty, please take care of the empty fields :) " );
				}
				else{
					JFrame frame = null;
					JOptionPane.showMessageDialog(frame, "<HTML>Appointment created " );
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(calIcon.getDate());
					
					String start = (String) startTimeField.getSelectedItem();
					start = start.substring(0, 2);
					int startTime = Integer.parseInt(start);
					cal.set(Calendar.HOUR_OF_DAY, startTime);
					Date startT = cal.getTime();
					
					String end = (String) endTimeField.getSelectedItem();
					end = end.substring(0, 2);
					int endTime = Integer.parseInt(end);
					cal.set(Calendar.HOUR_OF_DAY, endTime);
					Date endT = cal.getTime();
					
					
					Room room = (Room) placeField2.getSelectedItem();
					Appointment app = new Appointment(calIcon.getDate(), startT, endT, descriptionField.getText(), placeField1.getText(), room, room.getObjectID(), (User)XCal.getCSU().getObjectFromID(SaveableClass.User, XCal.usernameField.getText()), false);
					XCal.getCSU().addObject(app);
					XCal.getCSU().addToSendQueue(app);
					
					((GUICalender) guiCalendar).buildView();
					
					dispose();
					
				}
				
			}
		});
		placeField2.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				if(calIcon.getDate() == null || endTimeField.getSelectedIndex()-startTimeField.getSelectedIndex() == 0){
					JFrame frame = null;
					JOptionPane.showMessageDialog(frame, "<HTML>You have to specify a time to see the available rooms!" );
				}else{
					Calendar cal = Calendar.getInstance();
					cal.setTime(calIcon.getDate());
					
					String start = (String) startTimeField.getSelectedItem();
					start = start.substring(0, 2);
					int startTime = Integer.parseInt(start);
					cal.set(Calendar.HOUR, startTime);
					Date startT = cal.getTime();
					
					String end = (String) endTimeField.getSelectedItem();
					end = end.substring(0, 2);
					int endTime = Integer.parseInt(end);
					cal.set(Calendar.HOUR, endTime);
					Date endT = cal.getTime();
					((JComboBox) arg0.getSource()).removeAllItems();
					((JComboBox) arg0.getSource()).addItem("Choose");
					for (Room room : XCal.getCSU().getAvailableRooms(startT, endT)) {
						((JComboBox) arg0.getSource()).addItem(room);
					}
				}
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	

	class PlaceTextFieldAction implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(placeField1.getText().isEmpty() == true || placeField1.getText().equals("Type or click button")){
				placeField1.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if(placeField1.getText().isEmpty() == true){
				placeField1.setText("Type or click button");
			}
		}
		
	}
	
	class TimeSelectionAction implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			JComboBox b = (JComboBox) e.getSource();
			Object selIndex = e.getItem();
			if(e.getStateChange() == ItemEvent.SELECTED){
				if(b.getSelectedItem().equals(selIndex)){
					int index = b.getSelectedIndex();
					endTimeField.removeAllItems();
					populateTextComboBox(index + 1, 23, endTimeField);
				}
			}
		}
		
	}
	
	private void populateTextComboBox(int start, int end, JComboBox cBox){
		
		
		DateFormat format = DateFormat.getTimeInstance(DateFormat.SHORT);
		Calendar c = Calendar.getInstance();
		
		while(start <= end){
			
			c.set(Calendar.HOUR_OF_DAY, start);
			c.set(Calendar.MINUTE, 0);
			String f = format.format(c.getTime());
			cBox.addItem(f);
			start++;
		
	}
}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AppointmentGui gui = new AppointmentGui();
//		gui.pack();
//		gui.setVisible(true);
//	}

}

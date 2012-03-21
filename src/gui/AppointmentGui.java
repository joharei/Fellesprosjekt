package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


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
	
	private JDateChooser calIcon;
	
	protected GridBagConstraints gb;
	protected GridBagLayout gbLayout;
	
	
	public AppointmentGui() {
		
		this.setModal(true);
		this.setPreferredSize(new Dimension(500,600));
		pack();
		
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
		gb.insets = new Insets(10, 0, 0, 0);
		placeField2.setPreferredSize(new Dimension(50, 20));
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
	
	private void addActionListeners() {
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				setVisible(false);
			}
		});
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				setVisible(false);
			}
		});
	}
	

	class PlaceTextFieldAction implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			placeField1.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			placeField1.setText("Type or click button");
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
//		JFrame frame = new JFrame("Appointment");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		//set dimension size
//		frame.setContentPane(new AppointmentGui());
//		frame.setPreferredSize(new Dimension(500, 750));
//		
//		frame.pack();
//		frame.setVisible(true);
//	}

}

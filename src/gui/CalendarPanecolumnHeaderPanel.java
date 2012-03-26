package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("Serial")
public class CalendarPanecolumnHeaderPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel time = new JLabel("Time"+"                          ");
	private JLabel monday = new JLabel("Monday"+"                           ");
	private JLabel tuesday = new JLabel("Tuesday"+"                         ");
	private JLabel wednesday = new JLabel("Wednesday"+"                     ");
	private JLabel thursday = new JLabel("Thursday"+"                       ");
	private JLabel friday = new JLabel("Friday"+"                           ");
	private JLabel saturday = new JLabel("Saturday"+"                       ");
	private JLabel sunday = new JLabel("Sunday"+"                           ");
	GridBagConstraints c = new GridBagConstraints();
	
	public CalendarPanecolumnHeaderPanel(SmallCalendar smallCal){
		
		setLayout(new GridBagLayout());
//		monday.setText("Mon"+" ");
//		tuesday.setText("Thu"+"  ");
//		wednesday.setText("Wed"+" ");
//		thursday.setText("Thu"+" ");
//		friday.setText("Fri"+" ");
//		saturday.setText("Sat"+" ");
//		sunday.setText("Sun"+" ");
		
		//c.anchor=GridBagConstraints.NORTHWEST;
		add(time);
		add(monday);
		add(tuesday);
		add(wednesday);
		add(thursday);
		add(friday);
		add(saturday);
		add(sunday);
		setPreferredSize(new Dimension(1050,40));
	}
}

package gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("Serial")
public class CalendarPanecolumnHeaderPanel extends JPanel{
	
	private JLabel label = new JLabel("Mon       Tue        Wed      Thu        Fri        Sat       Sun     ");
	private JLabel label2 = new JLabel("Time          ");
	public CalendarPanecolumnHeaderPanel(){
		add(label2);
		add(label);
		setPreferredSize(new Dimension(400,30));
	}
}

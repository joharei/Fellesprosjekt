package gui;

import java.awt.Dimension;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane{
	
	//private String [] days = { "Time"," - ","Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"};
	
	public ScrollPane(){
		super(new GUICalender());
		setVisible(true);
		setPreferredSize(new Dimension(400,300));
		
	}
}

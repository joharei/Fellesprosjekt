package gui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane{
	
	//private String [] days = { "Time"," - ","Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"};
	
	public ScrollPane(SmallCalendar cal){
		super(new GUICalender(cal));
		setVisible(true);
		setPreferredSize(new Dimension(978,350));
		// 978
//		getVerticalScrollBar().setValue(150);
		getViewport().setViewPosition(new Point(0, 240));
	}
}

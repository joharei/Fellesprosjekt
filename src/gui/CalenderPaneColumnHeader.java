package gui;

import javax.swing.JViewport;

public class CalenderPaneColumnHeader extends JViewport{
	
	public CalenderPaneColumnHeader(){
		setBorder(null);
		setOpaque(false);
		add(new CalendarPanecolumnHeaderPanel());
	}
}

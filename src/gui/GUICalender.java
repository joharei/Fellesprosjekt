package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Appointment;

public class GUICalender extends JPanel implements PropertyChangeListener{
	private int rader =7;
	private int kolonner=24;
	private JButton [][] button;
	private Appointment [][] appList;
	private boolean [][] process;
	GridBagConstraints c = new GridBagConstraints();
	private JPanel panel2 = new JPanel();
	
	public GUICalender(SmallCalendar cal){
		cal.addPropertyChangeListener(this);
		button = new JButton [kolonner][rader];
		
		setLayout(new GridBagLayout());
		String [] teller={"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
		String [] col ={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
		
		for ( int h = 0; h < kolonner; h++) {
			
			button[h][0] = new JButton(teller[h]);
			button[h][0].setBackground(Color.DARK_GRAY);
			button[h][0].setForeground(Color.WHITE);
			button[h][0].setEnabled(false);
			c.gridx=9;
			c.gridy=4+h;
			c.ipady=5;
			add(button[h][0],c);
		}
		appList = new Appointment [kolonner][rader];
		process = new boolean [kolonner][rader];
		for (Appointment app : XCal.getCSU().getAllAppointments()) {
			Calendar day = Calendar.getInstance();
			day.setTime(app.getDate());
			Calendar start = Calendar.getInstance();
			start.setTime(app.getStartTime());
			Calendar end = Calendar.getInstance();
			end.setTime(app.getEndTime());
			System.out.println(day.get(Calendar.DAY_OF_WEEK));
			for (int i = 0; i < end.get(Calendar.HOUR_OF_DAY)-start.get(Calendar.HOUR_OF_DAY); i++){
				appList[day.get(Calendar.HOUR_OF_DAY)+i][(7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7] = app;
				process[day.get(Calendar.HOUR_OF_DAY)+i][(7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7] = true;
			}
		}
		for(int i = 0; i<process.length;i++){
			for(int j = 0; j<process[i].length; j++){
				if (process[i][j]){
					System.out.print("x");
				} else{
					System.out.print("-");
				}
			}
			System.out.println();
		}


	for ( int i = 0; i < kolonner; i++) {
		
		for (int j = 0; j < rader; j++) {
//			if(process[cal.get(Calendar.HOUR_OF_DAY)+i][(7 + cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7])
			button[i][j] = new JButton(new AbstractAction(" - ") {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				// This method is called when the button is pressed
				public void actionPerformed(ActionEvent evt) {
					//System.out.println("Tester");
				}
			}
					);
			c.gridx=11+j;
			c.gridy=4+i;
			c.ipadx=80;
			c.ipady=5;
			c.weightx=1;
			button[i][j].setBackground(Color.WHITE);
			button[i][j].setActionCommand(" ");
			add(button[i][j],c);
		}
	}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
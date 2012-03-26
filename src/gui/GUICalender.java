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
	private int kolonner =7;
	private int rader=24;
	private JButton [][] button;
	private Appointment [][] appList;
	private int [][] occupied;
	private int week;
	GridBagConstraints c = new GridBagConstraints();
	private JPanel panel2 = new JPanel();
	
	public GUICalender(SmallCalendar cal){
		cal.addListener(this);
		this.week = cal.getUkeNr();
		button = new JButton [rader][kolonner];
		
		setLayout(new GridBagLayout());
		String [] teller={"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
		String [] col ={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
		
		for ( int h = 0; h < rader; h++) {
			
			button[h][0] = new JButton(teller[h]);
			button[h][0].setBackground(Color.DARK_GRAY);
			button[h][0].setForeground(Color.WHITE);
			button[h][0].setEnabled(false);
			c.gridx=9;
			c.gridy=4+h;
			c.ipady=5;
			add(button[h][0],c);
		}
		buildView();
	}
	private void buildView(){
		appList = new Appointment [rader][kolonner];
		occupied = new int [rader][kolonner];
		for (Appointment app : XCal.getCSU().getAllAppointments()) {
			Calendar day = Calendar.getInstance();
			day.setTime(app.getDate());
			if (day.get(Calendar.WEEK_OF_YEAR) == this.week){
				Calendar start = Calendar.getInstance();
				start.setTime(app.getStartTime());
				Calendar end = Calendar.getInstance();
				end.setTime(app.getEndTime());
				System.out.println(day.get(Calendar.DAY_OF_WEEK));
				for (int i = 0; i < end.get(Calendar.HOUR_OF_DAY)-start.get(Calendar.HOUR_OF_DAY); i++){
					appList[day.get(Calendar.HOUR_OF_DAY)+i][(7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7] = app;
					occupied[day.get(Calendar.HOUR_OF_DAY)+i][(7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7] = 1;
				}
			}
		}
		for(int i = 0; i<occupied.length;i++){
			for(int j = 0; j<occupied[i].length; j++){
				if (occupied[i][j]==1){
					System.out.print("x");
				} else{
					System.out.print("-");
				}
			}
			System.out.println();
		}


		for ( int i = 0; i < rader; i++) {
			
			for (int j = 0; j < kolonner; j++) {
				if(button[i][j] != null && button[i][j].getBackground()!=Color.DARK_GRAY){
					remove(button[i][j]);
				}
				if(occupied[i][j]==1){
					button[i][j] = new JButton(new AbstractAction(appList[i][j].getTitle()) {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						
						// This method is called when the button is pressed
						public void actionPerformed(ActionEvent evt) {
							//System.out.println("Tester");
						}
					});
					int duration = 0;
					int k = i;
					while(appList[k][j] == appList[i][j]){
						duration++;
						occupied[k][j] = 2;
						k++;
					}
					System.out.println(duration);
					c.gridheight=duration;
					c.fill=GridBagConstraints.BOTH;
					c.gridx=11+j;
					c.gridy=4+i;
					c.ipadx=0;
					c.ipady=5;
					c.weightx=0;
					button[i][j].setBackground(Color.CYAN);
					button[i][j].setActionCommand(" ");
					add(button[i][j],c);
				}
				else if (occupied[i][j] == 2){
				}else {
					button[i][j] = new JButton(new AbstractAction(" - ") {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						
						// This method is called when the button is pressed
						public void actionPerformed(ActionEvent evt) {
							//System.out.println("Tester");
						}
					});
					c.gridheight=1;
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
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals(SmallCalendar.NAME_PROPERTY_WEEK_NUMBER)){
			this.week = (Integer)arg0.getNewValue();
			buildView();
		}
	}
}
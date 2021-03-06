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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Appointment;
import model.Meeting;
import model.User;

public class GUICalender extends JPanel implements PropertyChangeListener{
	private int kolonner =7;
	private int rader=24;
	private JButton [][] button;
	private Appointment [][] appList;
	private int [][] occupied;
	private int week;
	GridBagConstraints c = new GridBagConstraints();
	private JPanel panel2 = new JPanel();
	private String [] options = {"Meeting","Appointment"};
	private Calendar[] weekDates;
	private SmallCalendar cal;
	public static JPanel thisCopy;
	private int[] days = {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
	
	public GUICalender(SmallCalendar cal){
		cal.addListener(this);
		this.cal = cal;
		this.week = cal.getUkeNr();
		this.thisCopy = this;
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
	public void buildView(){
		weekDates = new Calendar[7];
		int p = 0;
		for (int day : days) {
			Calendar today = Calendar.getInstance();
			today.set(Calendar.WEEK_OF_YEAR, this.week);
			today.set(Calendar.DAY_OF_WEEK, day);
			weekDates[p]=today;
			p++;
		}
		appList = new Appointment [rader][kolonner];
		occupied = new int [rader][kolonner];
		for (Appointment app : XCal.getCSU().getAllAppointments()) {
			Calendar day = Calendar.getInstance();
			day.setTime(app.getDate());
			boolean showMeeting = false;
			if (app instanceof Meeting){
				for (User user : ((Meeting) app).getParticipants()) {
					if (user.getUsername().equals(XCal.usernameField.getText())){
						showMeeting = true;
						break;
					}
				}
				if (!showMeeting && ((Meeting) app).getOwner().getUsername().equals(XCal.usernameField.getText())){
					showMeeting = true;
				}
			}
			if ((!(app instanceof Meeting) && day.get(Calendar.WEEK_OF_YEAR) == this.week && !app.isDeleted()) || ((app instanceof Meeting && showMeeting) && day.get(Calendar.WEEK_OF_YEAR) == this.week && !app.isDeleted())){
				Calendar start = Calendar.getInstance();
				start.setTime(app.getStartTime());
				Calendar end = Calendar.getInstance();
				end.setTime(app.getEndTime());
				for (int i = 0; i < end.get(Calendar.HOUR_OF_DAY)-start.get(Calendar.HOUR_OF_DAY); i++){
					// (7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7 <--- finner ukedagen :)
					appList[start.get(Calendar.HOUR_OF_DAY)+i][(7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7] = app;
					occupied[start.get(Calendar.HOUR_OF_DAY)+i][(7 + day.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7] = 1;
				}
			}
		}

		for ( int i = 0; i < rader; i++) {
			
			for (int j = 0; j < kolonner; j++) {
				if(button[i][j] != null && button[i][j].getBackground()!=Color.DARK_GRAY){
					remove(button[i][j]);
				}
				if(occupied[i][j]==1){
					button[i][j] = new JButton(new CustomAction(appList[i][j].getTitle(), appList[i][j]) {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						
						// This method is called when the button is pressed
						public void actionPerformed(ActionEvent evt) {
							JDialog change = new ChangeAppointmentGui(getApp());
							change.setVisible(true);
						}
					});
					int duration = 0;
					int k = i;
					while(appList[k][j] == appList[i][j]){
						duration++;
						occupied[k][j] = 2;
						k++;
					}
					c.gridheight=duration;
					c.fill=GridBagConstraints.BOTH;
					c.gridx=11+j;
					c.gridy=4+i;
					c.ipadx=0;
					c.ipady=5;
					c.weightx=0;
					if (appList[i][j] instanceof Meeting){
						button[i][j].setBackground(Color.MAGENTA);
					}else{
						button[i][j].setBackground(Color.CYAN);
					}
					button[i][j].setActionCommand(" ");
					add(button[i][j],c);
				}
				else if (occupied[i][j] == 2){
				}else {
					weekDates[j].set(Calendar.HOUR_OF_DAY, i);
					Calendar rightNow = Calendar.getInstance();
					rightNow.setTime(weekDates[j].getTime());
					button[i][j] = new JButton(new CustomAction("", rightNow) {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						
						// This method is called when the button is pressed
						public void actionPerformed(ActionEvent evt) {
							int response=JOptionPane.showOptionDialog(null, "Meeting or Appointment?", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "none of your business");
							if(response==0){
								JDialog mGUI = new MeetingGui(getDate());
								mGUI.setVisible(true);
							}
							if(response==1){
								JDialog aGUI = new AppointmentGui(getDate());
								aGUI.setVisible(true);
								
							}
						}
					});
					c.gridheight=1;
					c.fill=GridBagConstraints.BOTH;
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
		this.updateUI();
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals(SmallCalendar.NAME_PROPERTY_WEEK_NUMBER)){
			this.week = (Integer)arg0.getNewValue();
			buildView();
		}
	}
	
	class CustomAction extends AbstractAction{
		private Appointment app;
		private Calendar date;
		public CustomAction(String title, Appointment app){
			super(title);
			this.setApp(app);
			this.setDate(date);
			
		}
		public CustomAction(String title, Calendar date){
			super(title);
			this.setDate(date);
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		public Appointment getApp() {
			return app;
		}
		public void setApp(Appointment app) {
			this.app = app;
		}
		public Calendar getDate(){
			return date;
		}
		public void setDate(Calendar date) {
			this.date = date;
		}
	}
}
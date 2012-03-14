package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Week {
	private Date startDate, endDate;
	private ArrayList<Appointment> appointments;
	
	
	public Week(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Week(Date startDate, Date endDate, ArrayList<Appointment> appointments) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.appointments = appointments;
	}

	public int getWeekNumber() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
}

package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Week {
	private Date startDate, endDate;
	private ArrayList<Appointment> appointments;
	private int weekNumber;
	
	
	public Week(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.weekNumber = calcWeekNumber(startDate);
		if (weekNumber != calcWeekNumber(endDate)) {
			throw new IllegalArgumentException("Given dates belong to different weeks");
		}
	}
	
	public Week(Date startDate, Date endDate, ArrayList<Appointment> appointments) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.weekNumber = calcWeekNumber(startDate);
		this.appointments = appointments;
		if (weekNumber != calcWeekNumber(endDate)) {
			throw new IllegalArgumentException("Given dates belong to different weeks");
		}
	}

	private static int calcWeekNumber(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	public int getWeekNumber() {
		return this.weekNumber;
	}
	
	public Date getStartDate() {
		return (Date) this.startDate.clone();
	}
	
	public Date getEndDate() {
		return (Date) this.endDate.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Appointment> getAppointments() {
		return  (ArrayList<Appointment>) this.appointments.clone();
	}
}

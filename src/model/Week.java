package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Week {
	private Date startDate, endDate;
	private ArrayList<Appointment> appointments;
	private int weekNumber;
	
	//testing method
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(2012, 2, 12);
		Date start = c.getTime();
		System.out.println(start);
		new Week(start);
	}
	
	/**Create a week of correct duration
	 * @param startDate Date of the monday
	 */
	public Week(Date startDate) {
		this.startDate = startDate;
		Calendar d = Calendar.getInstance();
		d.setFirstDayOfWeek(Calendar.MONDAY);
		d.setTime(this.startDate);
		if (d.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			throw new IllegalArgumentException("Chosen day is not a monday!");
		}
		int maxday = d.getActualMaximum(Calendar.DAY_OF_WEEK);
		d.set(Calendar.DAY_OF_WEEK, maxday + 1);
		this.endDate = d.getTime();
		this.weekNumber = calcWeekNumber(startDate);
		this.appointments = new ArrayList<Appointment>();
	}
	
	/**Create a week of correct duration with the
	 * specified appointments.
	 * @param startDate Date of the monday
	 * @param appointments List of appointments
	 */
	public Week(Date startDate, ArrayList<Appointment> appointments) {
		this.startDate = startDate;
		Calendar d = Calendar.getInstance();
		d.setFirstDayOfWeek(Calendar.MONDAY);
		d.setTime(this.startDate);
		if (d.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			throw new IllegalArgumentException("Chosen day is not a monday!");
		}
		int maxday = d.getActualMaximum(Calendar.DAY_OF_WEEK);
		d.set(Calendar.DAY_OF_WEEK, maxday + 1);
		this.endDate = d.getTime();
		this.weekNumber = calcWeekNumber(startDate);
		this.appointments = appointments;
	}
	
	/**
	 * Create a week with start and finish dates.
	 */
	public Week(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.weekNumber = calcWeekNumber(startDate);
		if (weekNumber != calcWeekNumber(endDate)) {
			throw new IllegalArgumentException("Given dates belong to different weeks");
		}
	}
	
	/**
	 * Create a week with start-, finish dates and
	 * a list of appointments for the week.
	 */
	public Week(Date startDate, Date endDate, ArrayList<Appointment> appointments) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.weekNumber = calcWeekNumber(startDate);
		this.appointments = appointments;
		if (weekNumber != calcWeekNumber(endDate)) {
			throw new IllegalArgumentException("Given dates belong to different weeks");
		}
	}

	/**
	 * Get the correct week number for the specified date
	 */
	private static int calcWeekNumber(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * Get the week number for this week.
	 */
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
	
	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public void addAppointment(Appointment app) {
		this.appointments.add(app);
	}
	
	public void removeAppointment(Appointment app) {
		this.appointments.remove(app);
	}
}

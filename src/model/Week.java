package model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Week {
	private Date startDate, endDate;
	private ArrayList<Appointment> appointments;
	private int weekNumber;
	private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "week";
	public static final String NAME_PROPERTY_START_DATE = "sdate";
	public static final String NAME_PROPERTY_END_DATE = "edate";
	public static final String NAME_PROPERTY_APPOINTMENTS = "appointments";
	public static final String NAME_PROPERTY_WEEK_NUMBER = "weeknb";
	
	public static DateFormat getDateFormat() {
		return (DateFormat) dateFormat.clone();
	}
	
	//testing method
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(2012, 2, 12);
		Date start = c.getTime();
		System.out.println("Week starts on: " + start);
		Week week = new Week(start);
		Date end = week.getEndDate();
		System.out.println("Week ends on: " + end);
		System.out.println("The week number is: " + week.getWeekNumber());
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
		//find the duration of the week and when to set the end date
		int maxday = d.getActualMaximum(Calendar.DAY_OF_WEEK);
		d.set(Calendar.DAY_OF_WEEK, maxday + 1);
		this.endDate = d.getTime();
		int[] val = validateWeekNumber();
		if (val[0] == 1) {
			this.weekNumber = val[1];
		} else {
			throw new RuntimeException("Computed week is invalid, different week numbers!");
		}
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
	 * Checks whether the week numbers of the start
	 * and end date of the week matches.
	 * @return An int array where the first element represents
	 * a boolean and the following two are the start and end week
	 * numbers. 
	 */
	private int[] validateWeekNumber() {
		int wn1 = calcWeekNumber(this.getStartDate());
		int wn2 = calcWeekNumber(this.getEndDate());
		int bool = (int) (wn1 == wn2 ? 1 : 0);
		return new int[] {
				bool, wn1, wn2
		};
	}
	
//	private boolean validateWeekNumber() {
//		return (calcWeekNumber(this.getStartDate()) == calcWeekNumber(this.getEndDate()));
//	}

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

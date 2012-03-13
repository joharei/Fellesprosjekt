package model;

import java.util.Calendar;
import java.util.Date;

import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.resources.CalendarData;
import sun.util.resources.CalendarData_no;

public class Week {
	Date startDate, endDate;
	List appointments;
	
	int getWeekNumber() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		return cal.WEEK_OF_YEAR;
	}
}

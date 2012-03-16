package model;

import java.text.DateFormat;
import java.util.Date;

public class Appointment {
	private Date date, startTime, endTime;
	private String description, location;
	private Room room;
	private int id;
	private User owner;
	private boolean isDeleted;
	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	private DateFormat timeformat = DateFormat.getTimeInstance(DateFormat.SHORT);
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "appointment";
	public static final String NAME_PROPERTY_DATE = "date";
	public static final String NAME_PROPERTY_START_TIME = "stime";
	public static final String NAME_PROPERTY_END_TIME = "etime";
	public static final String NAME_PROPERTY_DESCRIPTION = "descr";
	public static final String NAME_PROPERTY_LOCATION = "loc";
	public static final String NAME_PROPERTY_ROOM = "room";
	public static final String NAME_PROPERTY_ID = "id";
	public static final String NAME_PROPERTY_OWNER = "owner";
	public static final String NAME_PROPERTY_DELETED = "deleted";
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public DateFormat getDateFormat() {
		return (DateFormat) dateFormat.clone();
	}

	public DateFormat getTimeformat() {
		return (DateFormat) timeformat.clone();
	}
}

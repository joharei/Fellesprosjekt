package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import synclogic.SyncListener;

public class Appointment implements SyncListener {
	private Date date, startTime, endTime;
	private String description, location;
	private Room room;
	private int id;
	private User owner;
	private boolean isDeleted;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
	private PropertyChangeSupport pcs;
	
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
	
	public Appointment(Date date, Date startTime, Date endTime, String description,
			String location, Room room, int id, User owner, boolean isDeleted) {
		setDate(date);
		setStartTime(startTime);
		setEndTime(endTime);
		setDescription(description);
		setLocation(location);
		setRoom(room);
		setId(id);
		setOwner(owner);
		setDeleted(isDeleted);
		pcs = new PropertyChangeSupport(this);
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		Date old = getDate();
		this.date = date;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_DATE, old, date));
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		Date old = getStartTime();
		this.startTime = startTime;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_START_TIME, old, startTime));
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		Date old = getEndTime();
		this.endTime = endTime;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_END_TIME, old, endTime));
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		String old = getDescription();
		this.description = description;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_DESCRIPTION, old, description));
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		String old = getLocation();
		this.location = location;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_LOCATION, old, location));
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		Room old = getRoom();
		this.room = room;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_ROOM, old, room));
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		int old = getId();
		this.id = id;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_ID, old, id));
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		User old = getOwner();
		this.owner = owner;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_OWNER, old, owner));
	}
	public void setDeleted(boolean isDeleted) {
		boolean old = isDeleted();
		this.isDeleted = isDeleted;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_DELETED, old, isDeleted));
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public static DateFormat getDateFormat() {
		return (DateFormat) dateFormat.clone();
	}

	public static DateFormat getTimeformat() {
		return (DateFormat) timeformat.clone();
	}

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		Appointment app = (Appointment) newVersion;
		setDate(app.getDate());
		setDeleted(app.isDeleted());
		setDescription(app.getDescription());
		setStartTime(app.getStartTime());
		setEndTime(app.getEndTime());
		setId(app.getId());
		setLocation(app.getLocation());
		setOwner(app.getOwner());
		setRoom(app.getRoom());
		System.out.println("Updated appointment!");
	}

	@Override
	public SaveableClass getSaveableClass() {
		return SaveableClass.Appointment;
	}

	@Override
	public String getObjectID() {
		return "" + getId();
	}
}

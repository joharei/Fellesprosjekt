package synclogic;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Room;

public class RoomAvailabilityRequest {
	private ArrayList<Room> rooms;
	private Date start;
	private Date end;
	public final static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	
	public static final String NAME_PROPERTY_ROOM_LIST = "roomlist";
	public static final String NAME_PROPERTY_START_DATE = "sdate";
	public static final String NAME_PROPERTY_END_DATE = "edate";
	
	public RoomAvailabilityRequest(Date start, Date end) {
		rooms = new ArrayList<Room>();
		setStart(start);
		setEnd(end);
	}
	
	public void setAvailableRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	public ArrayList<Room> getAvailableRooms() {
		return this.rooms;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStart() {
		return start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getEnd() {
		return end;
	}
}

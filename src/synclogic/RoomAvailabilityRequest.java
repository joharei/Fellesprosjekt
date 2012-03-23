package synclogic;

import java.util.ArrayList;

import model.Room;

public class RoomAvailabilityRequest {
	private ArrayList<Room> rooms;
	
	public static final String NAME_PROPERTY_ROOM_LIST = "roomlist";
	
	public RoomAvailabilityRequest() {
		rooms = new ArrayList<Room>();
	}
	
	public void setAvailableRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	public ArrayList<Room> getAvailableRooms() {
		return this.rooms;
	}
}

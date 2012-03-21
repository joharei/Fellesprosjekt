package model;

import synclogic.SyncListener;

public class Room implements SyncListener {
	private int id, capacity;
	private String name;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "room";
	public static final String NAME_PROPERTY_ID = "id";
	public static final String NAME_PROPERTY_NAME = "name";
	public static final String NAME_PROPERTY_CAPACITY = "cap";
	
	/**
	 * Create a room with the following specification.
	 * @param id
	 * @param name
	 * @param capacity
	 */
	public Room(int id, String name, int capacity) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		Room room = (Room) newVersion;
		setCapacity(room.getCapacity());
		setId(room.getId());
		setName(room.getName());
		System.out.println("Room updated!");
	}

	@Override
	public SaveableClass getSaveableClass() {
		return SaveableClass.Room;
	}

	@Override
	public String getObjectID() {
		return "" + getId();
	}
}

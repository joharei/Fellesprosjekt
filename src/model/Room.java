package model;

public class Room {
	private int id, capacity;
	private String name;
	
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
}

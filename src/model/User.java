package model;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.fp.model.Person;

public class User extends Person {
	private String username, password;
	private ArrayList<Notification> notifications;
	private ArrayList<Week> weekModels;
	private int phone;
	private boolean isOnline;
	public final static String NAME_PROPERTY_USERNAME = "username";
	public final static String NAME_PROPERTY_PASSWORD = "password";
	public final static String NAME_PROPERTY_PHONE = "phone";
	
	public User(String name, String username, String password, String email, Date dateOfBirth, int phone) {
		setName(name);
		setDateOfBirth(dateOfBirth);
		setEmail(email);
		setPhone(phone);
		setUsername(username);
		setPassword(password);
		notifications = new ArrayList<Notification>();
		weekModels = new ArrayList<Week>();
	}
	
	private void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public ArrayList<Week> getWeekModels() {
		return weekModels;
	}

	public int getPhone() {
		return phone;
	}

	public boolean isOnline() {
		return isOnline;
	}
	
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	private void setUsername(String username) {
		if (this.username == null) {
			this.username = username;
		}
	}
	
	private void setPhone(int phone) {
		this.phone = phone;
	}
}

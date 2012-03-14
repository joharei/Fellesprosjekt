package model;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.fp.model.Person;

public class User {
	private String firstname, surname, username, email, password;
	private Date dateOfBirth;
	private ArrayList<Notification> notifications;
	private ArrayList<Week> weekModels;
	private int phone;
	private boolean isOnline;
	public final static String NAME_PROPERTY_FIRSTNAME = "fname";
	public final static String NAME_PROPERTY_SURNAME = "sname";
	public final static String NAME_PROPERTY_USERNAME = "uname";
	public final static String NAME_PROPERTY_PASSWORD = "pwd";
	public final static String NAME_PROPERTY_EMAIL = "email";
	public final static String NAME_PROPERTY_PHONE = "phone";
	public final static String NAME_PROPERTY_DATE_OF_BIRTH = "date";
	
	public User(String firstname, String surname, String username, String password, String email, Date dateOfBirth, int phone) {
		setName(firstname, surname);
		setDateOfBirth(dateOfBirth);
		setEmail(email);
		setPhone(phone);
		setUsername(username);
		setPassword(password);
		notifications = new ArrayList<Notification>();
		weekModels = new ArrayList<Week>();
	}
	
	public void setName(String n1, String n2) {
		this.firstname = n1;
		this.surname = n2;
	}

	public void setDateOfBirth(Date date) {
		this.dateOfBirth = date;
	}

	private void setEmail(String email) {
		this.email = email;
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

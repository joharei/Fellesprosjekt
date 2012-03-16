package model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class User {
	private String firstname, surname, username, email, password;
	private Date dateOfBirth;
	private ArrayList<Notification> notifications;
	private ArrayList<Week> weekModels;
	private int phone;
	private boolean isOnline;
	private boolean isDeleted;
	private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	
	//constants
	public final static String NAME_PROPERTY_CLASSTYPE = "user";
	public final static String NAME_PROPERTY_FIRSTNAME = "fname";
	public final static String NAME_PROPERTY_SURNAME = "sname";
	public final static String NAME_PROPERTY_USERNAME = "uname";
	public final static String NAME_PROPERTY_PASSWORD = "pwd";
	public final static String NAME_PROPERTY_EMAIL = "email";
	public final static String NAME_PROPERTY_PHONE = "phone";
	public final static String NAME_PROPERTY_DATE_OF_BIRTH = "date";
	public final static String NAME_PROPERTY_DELETED = "del";
	
	/**
	 * Creates a user without a password.
	 * Typically used for instancing other users than the logged in user on clients.
	 */
	public User(String firstname, String surname, String username, String email, Date dateOfBirth, int phone) {
		setName(firstname, surname);
		setDateOfBirth(dateOfBirth);
		setEmail(email);
		setPhone(phone);
		setUsername(username);
		notifications = new ArrayList<Notification>();
		weekModels = new ArrayList<Week>();
	}
	
	/**
	 * Create a user with a saved password.
	 * Used for logged in user on client or on server.
	 */
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
	
	public void setFirstname(String name) {
		this.firstname = name;
	}
	
	public void setSurname(String name) {
		this.surname = name;
	}
	
	public String getName() {
		return this.firstname + " " + this.surname;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public String getSurname() {
		return this.surname;
	}

	public void setDateOfBirth(Date date) {
		this.dateOfBirth = date;
	}
	
	public Date getDateOfBirth() {
		return this.dateOfBirth;
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
		} else {
			throw new RuntimeException("A username is already set for this user!");
		}
	}
	
	private void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmail() {
		 return this.email;
	}
	
	public String toString() {
		String s = "===BEGIN USER===" + 
		"\nName: " + getName() +
		"\nUsername: " + getUsername() +
		"\nPassword: " + getPassword() +
		"\nEmail: " + getEmail() +
		"\nBirthdate: " + getDateFormat().format(getDateOfBirth()) +
		"\nPhone number: " + getPhone() +
		"\n===END USER===";
		return s;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	
	public static DateFormat getDateFormat() {
		return (DateFormat) dateFormat.clone();
	}
}

package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import synclogic.SyncListener;


public class User implements SyncListener {
	private String firstname, surname, username, email, password;
	private Date dateOfBirth;
	private ArrayList<Notification> notifications;
	private ArrayList<Week> weekModels;
	private int phone;
	private boolean isOnline;
	private boolean isDeleted;
	private ArrayList<User> subscribesTo = new ArrayList<User>();
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private PropertyChangeSupport pcs;

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
	public final static String NAME_PROPERTY_SUBSCRIBES_TO = "substo";
	public final static String NAME_PROPERTY_IS_ONLINE = "isonline";
	public final static String NAME_PROPERTY_WEEK_MODELS = "weeks";
	
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
		pcs = new PropertyChangeSupport(this);
	}
	
	public User(String firstname, String surname, String username, String password) {
		setName(firstname, surname);
		setUsername(username);
		setPassword(password);
		notifications = new ArrayList<Notification>();
		weekModels = new ArrayList<Week>();
		pcs = new PropertyChangeSupport(this);
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
		pcs = new PropertyChangeSupport(this);
	}
	
	public void addWeekModel(Week week) {
		ArrayList<Week> old = getWeekModels();
		weekModels.add(week);
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_WEEK_MODELS, old, week));
	}
	
	public void addNotification(Notification notification) {
		notifications.add(notification);
	}
	
	public void setName(String n1, String n2) {
		setFirstname(n1);
		setSurname(n2);
	}
	
	public void setFirstname(String name) {
		String old = getFirstname();
		this.firstname = name;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_FIRSTNAME, old, name));
	}
	
	public void setSurname(String name) {
		String old = getSurname();
		this.surname = name;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_SURNAME, old, name));
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
		Date old = getDateOfBirth();
		this.dateOfBirth = date;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_DATE_OF_BIRTH, old, date));
	}
	
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	private void setEmail(String email) {
		String old = getEmail();
		this.email = email;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_EMAIL, old, email));
	}

	private void setPassword(String password) {
		String old = getPassword();
		this.password = password;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_PASSWORD, old, password));
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
		boolean old = isOnline();
		this.isOnline = isOnline;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_IS_ONLINE, old, isOnline));
	}

	private void setUsername(String username) {
		if (this.username == null) {
			this.username = username;
			pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_USERNAME, null, username));
		} else {
			throw new RuntimeException("A username is already set for this user!");
		}
	}
	
	private void setPhone(int phone) {
		int old = getPhone();
		this.phone = phone;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_PHONE, old, phone));
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
		"\nSubscribes to: " + getSubscribesTo() +
		"\nDeleted: " + isDeleted() +
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

	@Override
	public void fire(SaveableClass classType, Object newVersion) {
		if (!classType.equals(getSaveableClass())) {
			throw new IllegalArgumentException("Wrong classtype received");
		}
		User updated = (User) newVersion;
		System.out.println("Update received!");
		System.out.println("Old user: " + this.toString());
		System.out.println("Updated user: " + updated.toString());
		
		setFirstname(updated.getFirstname());
		setSurname(updated.getSurname());
		setEmail(updated.getEmail());
		setPassword(updated.getPassword());
		setPhone(updated.getPhone());
		setDateOfBirth(updated.getDateOfBirth());
		setDeleted(updated.isDeleted());
		setSubscribesTo(updated.getSubscribesTo());
	}

	@Override
	public SaveableClass getSaveableClass() {
		return SaveableClass.User;
	}

	@Override
	public String getObjectID() {
		return getUsername();
	}
	
	public void addSubscription(User target) {
		ArrayList<User> old = getSubscribesTo();
		this.subscribesTo.add(target);
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_SUBSCRIBES_TO, old, getSubscribesTo()));
	}
	
	public void removeSubscription(User target) {
		ArrayList<User> old = getSubscribesTo();
		this.subscribesTo.remove(target);
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_SUBSCRIBES_TO, old, getSubscribesTo()));
	}

	public void setSubscribesTo(ArrayList<User> subscribesTo) {
		ArrayList<User> old = getSubscribesTo();
		this.subscribesTo = subscribesTo;
		pcs.firePropertyChange(new PropertyChangeEvent(this, NAME_PROPERTY_SUBSCRIBES_TO, old, subscribesTo));
	}

	@SuppressWarnings("unchecked")
	public ArrayList<User> getSubscribesTo() {
		return (ArrayList<User>) subscribesTo.clone();
	}
}

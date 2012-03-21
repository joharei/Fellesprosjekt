package synclogic;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import javax.management.RuntimeErrorException;

import model.Appointment;
import model.Invitation;
import model.InvitationStatus;
import model.Meeting;
import model.Notification;
import model.NotificationType;
import model.SaveableClass;
import model.User;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;

public class ServerSynchronizationUnit extends SynchronizationUnit {

	private List<SyncListener> updatedButNotSavedObjects;
	
	private List<ClientHandler> activeUserConnections;
	private DatabaseUnit dbUnit;
	/**
	 * The connection that will be used to listen for incoming connections
	 */
	private Connection connection;
	
	public ServerSynchronizationUnit() throws ConnectException {
		super();
		this.updatedButNotSavedObjects = new ArrayList<SyncListener>();
		this.activeUserConnections = new ArrayList<ClientHandler>();
		this.dbUnit = new DatabaseUnit();
		// TODO: LOADING!!!
		//dbUnit.load();
		// TODO: Maa lagre et sted ogsaa!
	}

	public void removeClientConnection(ClientHandler handler) {
		this.activeUserConnections.remove(handler);
	}
	
	public void addUpdatedObject(SyncListener object) {
		List<SyncListener> list = new ArrayList<SyncListener>();
		list.add(object);
		addUpdatedObjects(list);
	}

	/**
	 * Add the given objects to updatedButNotSavedObjects. Will remove old objects of same class with same ID
	 * 
	 * @param objects	The objects to add
	 */
	public void addUpdatedObjects(List<SyncListener> objects) {
		List<SyncListener> toBeRemovedFromUpdatedButNotSavedObjects = new ArrayList<SyncListener>();
		for (SyncListener listener : this.updatedButNotSavedObjects) {
			for (SyncListener o : objects) {
				if(listener.getSaveableClass() == o.getSaveableClass() && listener.getObjectID().equalsIgnoreCase(o.getObjectID())) {
					toBeRemovedFromUpdatedButNotSavedObjects.add(listener);
				}
			}
		}
		this.updatedButNotSavedObjects.removeAll(toBeRemovedFromUpdatedButNotSavedObjects);
		this.updatedButNotSavedObjects.addAll(objects);
	}

	public void listenForUserConnections(int port) {
		this.connection = new ConnectionImpl(port);
		while(true) {
			try {
				startUserSession(this.connection.accept());
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the user with the given username, or null if the user is not present in the listener list
	 * 
	 * @param username	The user's username
	 * @return			The user
	 */
	public User getUser(String username) {
		for(SyncListener s : this.listeners) {
			if(s.getSaveableClass() == SaveableClass.User && s.getObjectID().equalsIgnoreCase(username)) {
				return (User) s;
			}
		}
		return null;
	}
	
	private void startUserSession(Connection con) {
		ClientHandler ch = new ClientHandler(con, this);
		this.activeUserConnections.add(ch);
		new Thread(ch).start();
	}
	
	public List<User> getActiveUsers() {
		List<User> users = new ArrayList<User>();
		for (ClientHandler handler : this.activeUserConnections) {
			if(handler.getUser() != null) {
				users.add(handler.getUser());
			}
		}
		return users;
	}
	
	/**
	 * Checks if the given update is valid
	 * 
	 * @param update	The update to check
	 * @param original	The original. Null if update is a new object
	 * @return			True if the update is valid. False if not
	 */
	public boolean isValidUpdate(SyncListener update, SyncListener original) {
		// TODO
		throw new RuntimeException("NOT YET IMPLEMENTED!");
	}

	public ClientHandler getClientHandler(User user) {
		for (ClientHandler handler : this.activeUserConnections) {
			// Returnerer ogsaa handler til bruker med samme brukernavn, hvis det ikke er samme objekt
			if(handler.getUser() == user || this.getObjectFromID(SaveableClass.User, user.getUsername()) == handler.getUser()) { 
				return handler;
			}
		}
		return null;
	}
	
	public String getNewKey(SaveableClass c) {
		return this.dbUnit.getNewKey(c);
	}
	
	public void sendMeetingInvitations(Meeting m) {
		List<String> usernames = m.getUsersToInvite();
		for (String username : usernames) {
			User userToInvite = (User) this.getObjectFromID(SaveableClass.User, username);
			Invitation newInv = new Invitation(InvitationStatus.NOT_ANSWERED, m, dbUnit.getNewKey(SaveableClass.Invitation));
			this.addListener(newInv);
			Notification newNot = new Notification(newInv, NotificationType.INVITATION_RECEIVED, dbUnit.getNewKey(SaveableClass.Notification), m.getOwner());
			this.addListener(newNot);
			userToInvite.addNotification(newNot);
			// TODO: Er det greit aa sende bare notificationen, eller maa jeg legge den i User og sende User paa nytt?
			this.getClientHandler(userToInvite).addToSendQueue(newNot);
			m.addInvitation(newInv);
		}
		m.setUsersToInvite(new ArrayList<String>());
		this.getClientHandler(m.getOwner()).addToSendQueue(m);
	}
	
	public void addObject(SyncListener o) {
		this.addListener(o);
		switch(o.getSaveableClass()) {
		case Meeting:
			// Ikke generell
			((Meeting) o).setId(this.dbUnit.getNewKey(SaveableClass.Meeting));
			sendMeetingInvitations((Meeting) o);
		case Appointment:
			// Ikke generell
			if(o.getSaveableClass() == SaveableClass.Appointment) {
				((Appointment) o).setId(this.dbUnit.getNewKey(SaveableClass.Appointment));
			}
			Appointment a = (Appointment) o;
			User owner = a.getOwner();
			List<User> users = this.getActiveUsers();
			for (User user : users) {
				if(user.getSubscribesTo().contains(owner)) {
					this.getClientHandler(user).addToSendQueue(a);
				}
			}
			break;
		default:
			throw new RuntimeException("Unexpected class!");
		}
	}
	
//	public static void main(String[] args) {
//		ConnectionImpl.fixLogDirectory();
//		ServerSynchronizationUnit ssu = new ServerSynchronizationUnit();
//		ssu.listeners.add(new User("Johan", "Reitan", "joharei", "123", "BLANK", new Date(), 113));
//		ssu.listenForUserConnections(1337);
//	}
}

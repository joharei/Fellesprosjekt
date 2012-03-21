package synclogic;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ntnu.fp.net.co.Connection;
import model.Invitation;
import model.InvitationStatus;
import model.Meeting;
import model.Notification;
import model.NotificationType;
import model.SaveableClass;
import model.User;
import model.XmlSerializerX;

public class ClientHandler implements Runnable {

	private Connection connection;
	private ServerSynchronizationUnit serverSynchronizationUnit;
	private User user;
	private Map<SyncListener, Date> sentObjects;
	private List<Object> sendQueue;
	
	public ClientHandler(Connection con, ServerSynchronizationUnit ssu) {
		this.sendQueue = new ArrayList<Object>();
		this.connection = con;
		this.serverSynchronizationUnit = ssu;
		this.sentObjects = new HashMap<SyncListener, Date>();
	}
	
	@Override
	public void run() {
		// Receive login request
		try {
			this.receive(this.login((LoginRequest) XmlSerializerX.toObject(this.connection.receive())));
		} catch (Exception e) {
			// Do nothing
			e.printStackTrace();
		}
		this.serverSynchronizationUnit.removeClientConnection(this);
		System.out.println("Client handler should be dead now.");
	}
	
	public boolean login(LoginRequest loginRequest) {
		System.out.println("Entered login!");
		try {
			// Get the user
			User user = serverSynchronizationUnit.getUser(loginRequest.getUsername());
			// Check if login is OK
			if(user == null) {
				loginRequest.setLoginAccepted(false);
			} else {
				loginRequest.setLoginAccepted(loginRequest.getUsername().equalsIgnoreCase(user.getUsername()) && user.getPassword().equals(loginRequest.getPassword()) ? true : false);
			}
			System.out.println("Sending response!");
			this.connection.send(XmlSerializerX.toXml(loginRequest, SaveableClass.LoginRequest));
			user.setOnline(loginRequest.getLoginAccepted());
			if(loginRequest.getLoginAccepted()) {
				this.user = user;
			}
			return loginRequest.getLoginAccepted();
		} catch (Exception e) {
			this.user = null;
			e.printStackTrace();
			return false;
		}
	}
	
	public void addSentObjects(List<SyncListener> objects) {
		Date d = new Date();
		for (SyncListener s : objects) {
			this.sentObjects.put(s, d);
		}
	}
	
	public void receive(boolean isloggedIn) {
		while(true) {
			try {
				// TODO: Send "feilmelding" dersom en endring ikke kan utfoeres
				// TODO: Timeout!
				Object o = XmlSerializerX.toObject(this.connection.receive());
				if(o instanceof LoginRequest && !isloggedIn) {
					isloggedIn = this.login((LoginRequest) o);
				} else if(isloggedIn){
					if(o instanceof ObjectRequest) {
						// Handle object request
						ObjectRequest request = (ObjectRequest) o;
						// Get requested object
						List<SyncListener> requestedObjects = this.serverSynchronizationUnit.getObjectsFromID(request.getSaveableClass(), request.getObjectID());
						// Send requested object
						this.connection.send(XmlSerializerX.toXml(requestedObjects, request.getSaveableClass()));
						// Remember that the requested object is sent
						this.addSentObjects(requestedObjects);
						// Den under skal vel ikke vaere her?
						// this.serverSynchronizationUnit.addUpdatedObjects(requestedObjects);
					} else if(o instanceof UpdateRequest) {
						UpdateRequest updateReq = (UpdateRequest) o;
						// TODO: Antar naa at alt som brukeren er interessert i blir puttet i sendQueue. Er det greit?
						updateReq.addAllObjects(this.sendQueue);
						this.connection.send(XmlSerializerX.toXml(updateReq, SaveableClass.UpdateRequest));
					} else if(o instanceof List) {
						this.processReceivedObjects((List) o);
					} else if(o instanceof SyncListener) {
						List<SyncListener> l = new ArrayList<SyncListener>();
						l.add((SyncListener) o);
						this.processReceivedObjects(l);
					} else {
						throw new RuntimeException("Oops! Something went wrong.");
					}
				}
			} catch (Exception e) {
				// TODO Er denne ok?
				e.printStackTrace();
				System.out.println("Connection was closed by client (or died for some reason)!");
				this.user.setOnline(false);
				this.serverSynchronizationUnit.removeClientConnection(this);
				break;
			}
		}
		System.out.println("Should stop receiving now!");
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void addToSendQueue(Object o) {
		this.sendQueue.add(o);
	}
	
	public void processReceivedObjects(List<SyncListener> objects) {
		for (SyncListener o : objects) {
			SyncListener original = this.serverSynchronizationUnit.getObjectFromID(o.getSaveableClass(), o.getObjectID());
			if(!this.serverSynchronizationUnit.isValidUpdate(o)) {
				this.sendQueue.add(new ErrorMessage(original, o));
			} else {
				// Execute update
				if(original != null) {
					// Object exists and should be updated
					// TODO Maa ogsaa gaa inn i alle referansene i objektet for aa oppdatere disse!!!!!!!!!!
					switch(o.getSaveableClass()) {
					case Meeting:
						Meeting originalM = (Meeting) original;
						Meeting m = (Meeting) o;
						// Invite new users
						this.serverSynchronizationUnit.sendMeetingInvitations(m);
						// Sjekk om invitasjoner er blitt fjernet
						for (String originalInvID : originalM.getInvitations()) {
							boolean invitationFound = false;
							for (String newInvID : m.getInvitations()) {
								if(originalInvID.equalsIgnoreCase(newInvID)) {
									invitationFound = true;
								}
							}
							// Hvis invitasjonen er blitt fjernet
							if(!invitationFound) {
								Invitation deletedInvitation = (Invitation) this.serverSynchronizationUnit.getObjectFromID(SaveableClass.Invitation,originalInvID);
								deletedInvitation.setStatus(InvitationStatus.REVOKED);
								Notification not = new Notification(deletedInvitation, NotificationType.INVITATION_REVOKED, this.serverSynchronizationUnit.getNewKey(SaveableClass.Notification), deletedInvitation.getMeeting().getOwner());
								List<SyncListener> users = this.serverSynchronizationUnit.getObjectsFromID(SaveableClass.User, null);
								outer: for (SyncListener user : users) {
									for(Notification notification : ((User) user).getNotifications()) {
										if(notification.getInvitation().getID().equalsIgnoreCase(originalInvID)) {
											((User) user).addNotification(not);
											// TODO: Burde notifikasjonen med invitasjonen fjernes??
											break outer;
										}
									}
								}
							}
						}
						// TODO: Maa jeg gjoere noe med participants?
						// TODO: Maa jeg kalle fire paa alle invitasjonene? 
					case Appointment:
						// TODO: Antar at alt er sjekket (ogsaa romreservasjon), slik at fire tar seg av all oppdatering
						break;
					case Invitation:
						
						break;
					case Notification:
						// Antar at det bare er read som kan endres. Dette burde fire ta seg av
						break;
					case User:
						
						break;
					default:
						throw new RuntimeException("An unexpected object was received!");
					}
					this.serverSynchronizationUnit.fire(original.getSaveableClass(), original.getObjectID(), o);
				} else {
					// Object does not exist, but should be added
					this.serverSynchronizationUnit.addObject(o);
				}
				// TODO:
				// Notify users that care
				// Dette skjer delvis i addObject, og burde kanskje derfor ogsaa skje i fire???????
			}
		}
	}
}

package synclogic;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.List;
import model.Appointment;
import model.Invitation;
import model.InvitationStatus;
import model.Meeting;
import model.Notification;
import model.NotificationType;
import model.Room;
import model.SaveableClass;
import model.User;

public class DatabaseUnit {

	private static Connection conn = null;
	private static ArrayList<User> userArray;
	private static ArrayList<Appointment> eventArray;
	
	public DatabaseUnit() throws ConnectException {
		try {
			dbConnect();
		} catch (Exception e) {
			throw new ConnectException();
		} 
	}
	
	public void dbConnect() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		Properties props = new Properties();
		props.put("user","hannekot_26");
		props.put("password","gruppe26");
			
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/hannekot_X-cal", props);
	}
	
	public static void objectToDb(List<SyncListener> object) throws SQLException{
		if(object instanceof User){
			User user1 = (User)object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Username FROM User WHERE Username='" + user1.getUsername() +"'");
			String dateOfBirth = (User.getDateFormat().format(user1.getDateOfBirth()));
			while (rs.next()) {
					Statement update = conn.createStatement();
					update.executeUpdate("UPDATE User SET Password='" +user1.getPassword()+"', Email='"
							+user1.getEmail() +"', DateOfBirth='" + dateOfBirth + "', Phone='" 
							+user1.getPhone() +"', Surname='" +user1.getSurname() +"', Firstname='" +user1.getFirstname()
							+ "' WHERE Username='" +user1.getUsername() + "';") ;
					return;
			}	
			Statement insertInto = conn.createStatement();
			insertInto.executeUpdate("INSERT INTO User VALUES('" + user1.getUsername()+"','"
					+ user1.getPassword() +"','" + user1.getEmail() + "','" + dateOfBirth + "','"
					+ user1.getPhone() + "','" + user1.getSurname() + "','" + user1.getFirstname() + "','0');");
		}
		else if(object instanceof Room){
			Room room = (Room) object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT RoomID FROM Room WHERE RoomID='" + room.getId()+ "'");
			while(rs.next()){
					Statement update = conn.createStatement();
					update.executeUpdate("UPDATE Room SET Name='" + room.getName()+ "', Capacity='" + room.getCapacity()
								+ "' WHERE RoomID='" +room.getId() + "'");
					return;
			}
			Statement insertInto = conn.createStatement();
			insertInto.executeUpdate("INSERT INTO Room (Name, Capacity) VALUES('" + room.getName() + "','" + room.getCapacity() + "');");
			}

		else if(object instanceof Notification){
			Notification not= (Notification) object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NotificationID FROM Notification WHERE NOtficationID='" +not.getId() + "'");
			while(rs.next()){
					Statement update = conn.createStatement();
					ResultSet rs2 = update.executeQuery("UPDATE Notification SET Type='" + not.getType() 
							+"' WHERE NotificationID='" + not.getId() +"'");
					return;
			}
			Statement insertInto = conn.createStatement();
			ResultSet rs1 = insertInto.executeQuery("INSERT INTO Notification VALUES(" + not.getId() +"," 
					+ not.getType() +")");
		}
	//	conn.close();
	}
//gå igjennom alle objektene i lista og oppdater databasen etter hvilket objekt det er
//husk listene participants, invitationID, subscribesTo
	public static ArrayList<User> loadUser() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM User");
		ArrayList<User> userArray = new ArrayList<User>();
		while(rs.next()){
			String Username = rs.getString("Username");
			String Password = rs.getString("Password");
			String Email = rs.getString("Email");
			Date date = rs.getDate("DateOfBirth");
			int Phone = rs.getInt("Phone");
			String Surname = rs.getString("Surname");
			String Firstname = rs.getString("Firstname");
			int deleted = rs.getInt("Deleted");
			User user = new User(Firstname, Surname, Username,Password, Email, date, Phone);
			if(deleted == 1){
				user.setDeleted(true);
			}
			else{user.setDeleted(false);}
			
			userArray.add(user);
		}
		return userArray;
	}

	public static ArrayList<Room> loadRoom() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Room");
		ArrayList<Room> roomArray = new ArrayList<Room>();
		while(rs.next()){
			int RoomID = rs.getInt("RoomID");
			String RoomName = rs.getString("Name");
			int Capacity = rs.getInt("Capacity");
			Room room = new Room(RoomID, RoomName, Capacity);
			roomArray.add(room);
		}	
		return roomArray;
	}

	public static ArrayList<Appointment> loadEvent() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Event");
		while(rs.next()){
			int type = rs.getInt("Type");
			int eventID = rs.getInt("EventID");
			Date date = rs.getDate("Date");
			Date start = rs.getTime("Start");
			Date end = rs.getTime("End");
			String description = rs.getString("Description");
			String roomName = rs.getString("Location");
			int deleted = rs.getInt("Deleted");
			Statement sstm = conn.createStatement();
			ResultSet rss1 = sstm.executeQuery("SELECT Username FROM UserEvent WHERE EventID ='" +eventID +"'" );
			rss1.first();
			String usser = rss1.getString(1);
			int UserIndex = 0;
			for (int i = 0; i < userArray.size(); i++) {
				if(userArray.get(i).getUsername().equalsIgnoreCase(usser)){
					UserIndex = i;
					i=userArray.size();
				}
			}
			ArrayList<Room> room = loadRoom();
			int RoomIndex = 0;
			for (int i = 0; i < room.size(); i++) {
				if(room.get(i).getName().equalsIgnoreCase(roomName)){
					RoomIndex = i;
					i=room.size();
				}
			}
			if(type == 1 && deleted == 0){
				System.out.println(room.get(RoomIndex) +"+" + userArray.get(UserIndex));
				Meeting meeting = new Meeting(date, start, end, description, roomName,room.get(RoomIndex), eventID, userArray.get(UserIndex), false );
				eventArray.add(meeting);
			}
			else if(type ==1 && deleted == 1){
				Meeting meeting = new Meeting(date, start, end, description, roomName,room.get(RoomIndex), eventID, userArray.get(UserIndex), true );
				eventArray.add(meeting);
			}
			else if(type == 0 && deleted == 0){
				Appointment appment = new Appointment(date, start, end, description, roomName, room.get(RoomIndex), eventID, userArray.get(UserIndex), false);
				eventArray.add(appment);
			}
			else{
				Appointment appment = new Appointment(date, start, end, description, roomName, room.get(RoomIndex), eventID, userArray.get(UserIndex), true);
				eventArray.add(appment);
			}
			}
		return eventArray;
	}

	public static ArrayList<Invitation> loadInvitation() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Invitation");
		ArrayList<Invitation> invitationArray = new ArrayList<Invitation>();
		while(rs.next()){
			int invitationID = rs.getInt("InvitationID");
			int status = rs.getInt("Status");
			Statement stm = conn.createStatement();
			ResultSet rset = stm.executeQuery("SELECT EventID FROM InvitationTo WHERE InvitationID='" + invitationID +"'");
			rset.first();
			int eventID = rset.getInt("InvitationTo");
			int index = 0;
			for (int i = 0; i < eventArray.size(); i++) {
				Object obj = eventArray.get(i);
				if(((Meeting)obj).getId() == eventID){
						index = i;
						i = eventArray.size();
				}	
			}
			switch (status) {
			case 0:{
				Invitation invitation = new Invitation(InvitationStatus.NOT_ANSWERED, (Meeting)(eventArray.get(index)),(Integer.toString(invitationID)));
				invitationArray.add(invitation);
				break;
			}
			case 1:{
				Invitation invitation = new Invitation(InvitationStatus.ACCEPTED, (Meeting)(eventArray.get(index)),(Integer.toString(invitationID)));
				invitationArray.add(invitation);
				break;
			}
			case 2:{
				Invitation invitation = new Invitation(InvitationStatus.REJECTED, (Meeting)(eventArray.get(index)),(Integer.toString(invitationID)));
				invitationArray.add(invitation);
				break;	
			}
			case 3:{
				Invitation invitation = new Invitation(InvitationStatus.REVOKED, (Meeting)(eventArray.get(index)),(Integer.toString(invitationID)));
				invitationArray.add(invitation);
				break;	
			}
			case 4:{
				Invitation invitation = new Invitation(InvitationStatus.NOT_ANSWERED_TIME_CHANGED, (Meeting)(eventArray.get(index)),(Integer.toString(invitationID)));
				invitationArray.add(invitation);
				break;	

			}
			default:
				break;
			}			
		}
		return invitationArray;
	}

	public static ArrayList<Notification> loadNotifcation() throws SQLException{
		ArrayList<Notification> notificationArray = new ArrayList<Notification>();
		for (int i = 0; i < userArray.size(); i++) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Notification.NotificationID, type , TriggeredBy, Username" 
											+ "FROM Notification JOIN UserNotification ON "
											+ "Notification.NotificationID = UserNotification.NotificationID"
											+ "WHERE Username ='" + (userArray.get(i)).getUsername()+ ";");
			while(rs.next()){
				int notificationID = rs.getInt("NotificationiD");
				int type = rs.getInt("type");
				String triggeredBy = rs.getString("TriggeredBy");
				int userIndex = 0;
				for (int j = 0; j < userArray.size(); j++) {
					if(userArray.get(j).getUsername().equalsIgnoreCase(triggeredBy)){
						userIndex = j;
						j  = userArray.size();
					}
				}
				ArrayList<Invitation> invitationArray = loadInvitation();
				int invitationIndex = 0;
				for (int j = 0; j < invitationArray.size(); j++) {
					Statement sstm = conn.createStatement();
					ResultSet rs1 = sstm.executeQuery("SELECT InvitationID FROM BelongsTo WHERE NotificationID='" + notificationID + "'");
					rs1.first();
					int invitationID = rs1.getInt("InvitationID");
					if(invitationArray.get(j).getID().equalsIgnoreCase((Integer.toString(invitationID)))){
						invitationIndex = j;
						j = invitationArray.size();
					}
				}
				switch (type) {
				case 0:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.MEETING_CANCELLED, (Integer.toString(notificationID)), userArray.get(userIndex));				
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);					
					break;
				}
				case 1:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.INVITATION_RECEIVED, (Integer.toString(notificationID)), userArray.get(userIndex));
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);
					break;				
				}
				case 2:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.INVITATION_ACCEPTED, (Integer.toString(notificationID)), userArray.get(userIndex));
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);
					break;
				}
				case 3:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.INVITATION_REJECTED, (Integer.toString(notificationID)), userArray.get(userIndex));
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);
					break;				
				}
				case 4:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.INVITATION_REVOKED, (Integer.toString(notificationID)), userArray.get(userIndex));
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);
					break;				
				}
				case 5:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.MEETING_TIME_CHANGED, (Integer.toString(notificationID)), userArray.get(userIndex));
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);
					break;				
				}
				case 6:{
					Notification not = new Notification(invitationArray.get(invitationIndex), NotificationType.MEETING_CHANGE_REJECTED, (Integer.toString(notificationID)), userArray.get(userIndex));
					(userArray.get(i)).addNotification(not);
					notificationArray.add(not);
					break;				
				}
				default:
					break;
				}
			}
		}
		return notificationArray;
	}
	
	public static void addParticipants() throws SQLException{
		for (int i = 0; i < eventArray.size(); i++) {
			if(eventArray.get(i) instanceof Meeting){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt .executeQuery("SELECT UserNotification.Username, Notification.NotificationID, Invitation.InvitationID, Invitation.Status, Event.EventID"
											+"FROM UserNotification JOIN Notification ON UserNotification.NotificationID = Notification.NotificationID"
											+"JOIN BelongsTo ON Notification.NotificationID = BelongsTo.NotificationID"
											+"JOIN Invitation ON BelongsTo.InvitationID = Invitation.InvitationID"
											+"JOIN InvitationTo ON Invitation.InvitationID = InvitationTo.InvitationID"
											+"JOIN Event ON Event.EventID = InvitationTo.EventID"
											+"WHERE Event.EventID = '" + ((Meeting)(eventArray.get(i))).getId() +"' AND Invitation.Status ='1';");
				while(rs.next()){
					String username = rs.getString("Username");
					for (int j = 0; j < userArray.size(); j++) {
						if(userArray.get(i).getUsername().equalsIgnoreCase(username)){
							((Meeting)(eventArray.get(i))).addParticipant(userArray.get(j));
						}
					}
				}
			}
		}
	}
	
	public static void addInvitation() throws SQLException{
		for (int i = 0; i < eventArray.size(); i++) {
			if(eventArray.get(i) instanceof Meeting){				
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT InvitationID FROM InvitationTO WHERE EventID='" + ((Meeting)eventArray.get(i)).getId()+"';");
			while(rs.next()){
				int invitationID = rs.getInt("InvitationID");
				((Meeting)eventArray.get(i)).addInvitation((Integer.toString(invitationID)));
			}
			}	
		}
	}
	
	public static void addUserSubscription() throws SQLException{
		for (int i = 0; i < userArray.size(); i++) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT AuthorUsername FROM UserSubscription WHERE SubscriberUsername='"+ userArray.get(i).getUsername()+ "';");
			while(rs.next()){
				String username = rs.getString("AuthorUsername");
				for (int j = 0; j < userArray.size(); j++) {
					if(userArray.get(j).getUsername().equalsIgnoreCase(username)){
						userArray.get(i).addSubscription(userArray.get(j));
						j = userArray.size();
					}
				}
			}

		}
	}

	public String getNewKey(SaveableClass type){
		if(type.equals(SaveableClass.Appointment) || type.equals(SaveableClass.Meeting)){
			String AppKey = "";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT EventID FROM Event");
				rs.last();
				int eventID = rs.getInt("EventID");
				int appKey = eventID + 1;
				AppKey = Integer.toString(appKey);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return AppKey;
		}
		else if(type.equals(SaveableClass.Invitation)){
			String InvtKey = "";
			try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT InvitationID FROM Invitation");
			rs.last();
			int invtID = rs.getInt("InvitationID");
			int invtKey = invtID + 1;
			InvtKey = Integer.toString(invtKey);
			} catch(SQLException e){
				e.printStackTrace();
			}
			return InvtKey;
		}
		else if(type.equals(SaveableClass.Notification)){
			String NotKey = "";
			try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NotificationID FROM Notification");
			rs.last();
			int notID = rs.getInt("NotificationID");
			int notKey = notID + 1;
			NotKey = Integer.toString(notKey);
			} catch(SQLException e){
				e.printStackTrace();
			}
			return NotKey;
		}
		else{
			String RoomKey = "";
			try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT RoomID FROM Room");
			rs.last();
			int roomID = rs.getInt("RoomID");
			int roomKey = roomID + 1;
			RoomKey = Integer.toString(roomKey);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return RoomKey;
		}
	}
	
	public ArrayList<SyncListener> load() throws SQLException{
		ArrayList<SyncListener> loadArray = new ArrayList<SyncListener>();
		userArray = loadUser();
		eventArray = loadEvent();
		addParticipants();
		addUserSubscription();
		addInvitation();
		loadArray.addAll(userArray);
		loadArray.addAll(loadRoom());
		loadArray.addAll(eventArray);
		loadArray.addAll(loadInvitation());
		loadArray.addAll(loadNotifcation());
		
		return loadArray;
				
	} 
	
	public void closeConnection() throws SQLException{
		conn.close();
	}
	
	public static void main(String[] args) {
	}
}
	
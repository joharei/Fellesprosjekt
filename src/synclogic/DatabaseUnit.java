package synclogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import model.Appointment;
import model.Notification;
import model.Room;
import model.User;

public class DatabaseUnit {

	private static Connection conn = null;
	
	public void dbConnect() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		Properties props = new Properties();
		props.put("user","hannekot_26");
		props.put("password","gruppe26");
			
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/hannekot_X-cal", props);
	}
	
	public static void objectToDb(Object object) throws SQLException{
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
	
	public static ArrayList<Object> loadUser() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM User");
		ArrayList<Object> userArray = new ArrayList<Object>();
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
			userArray.add(user);
		}
		return userArray;
	}
	
	public static ArrayList<Object> loadRoom() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Room");
		ArrayList<Object> roomArray = new ArrayList<Object>();
		while(rs.next()){
			int RoomID = rs.getInt("RoomID");
			String RoomName = rs.getString("Name");
			int Capacity = rs.getInt("Capacity");
			Room room = new Room(RoomID, RoomName, Capacity);
			roomArray.add(room);
		}
		
		return roomArray;
	}
	
	public static ArrayList<Object> loadNotification() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Notification");
		ArrayList<Object> notificationArray = new ArrayList<Object>();
		while(rs.next()){
			int NotificationID = rs.getInt("NotificationID");
			int type = rs.getInt("type");
			String TriggeredBy = rs.getString("TriggeredBy");
			int EventID = rs.getInt("EventID");
			
		}
		return notificationArray;
	}

	
	public static ArrayList<Object> loadInvitation() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Invitation");
		ArrayList<Object> invitationArray = new ArrayList<Object>();
		while(rs.next()){
			int status = rs.getInt("Status");
			String Username =rs.getString("Username");
			int EventID = rs.getInt("EventID");
			
		}
		return invitationArray;
	}
	
	
	public void load(){
		//når servern starter så hentes alle objektene ut fra databasen og lage objektene som ligger i databasen 
		//så for hver user i databasen skal det lages et User objekt ved hjelp av user-klassen sin konstruktør.
		//lage mindre små metoder for User, Event, Notification osv, for så å kalle alle metodene i denne
		//legge de ferdig laga objektene i en liste
		
		
	} 
	
	
	
	public void closeConnection() throws SQLException
	{
		conn.close();
	}

}
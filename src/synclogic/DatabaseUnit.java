package synclogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		
		//sjekke om objekte ligger i databasen  object instance of User
		//hvis ja: oppdater databasen med ny informasjon om objectet
		//hvis nei: Legge til det nye objektet i databasen
	
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
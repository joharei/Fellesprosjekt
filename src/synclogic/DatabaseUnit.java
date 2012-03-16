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
	private String username;
	private String password;
	private String url;
	private Connection conn = null;
	public Connection dbConnect() throws ClassNotFoundException, SQLException{
		Class.forName("loadJDBCDriver");  //en adresse
	
		Properties connectionProps = new Properties();
		connectionProps.put("user", username);
		connectionProps.put("password", password);
		conn = DriverManager.getConnection(url, connectionProps);
		
		return conn;
	}
	
	public void objectToDB(Object object) throws SQLException{
		if(object instanceof User){
			User user = (User)object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Username FROM User WHERE Username='" + user.getUsername() +"'");
			while (rs.next()) {
			//	if(user.getUsername().equals(rs.getString("Username"))){
					Statement update = conn.createStatement();
					ResultSet rs2 = update.executeQuery("UPDATE User SET Passord='" +user.getPassword()+"', Email='"
							+user.getEmail() +"', DateOfBirth='" +user.getDateOfBirth() + "', Phone ='" 
							+user.getPhone() +"', SurName='" +user.getSurname() +"'FirstName='" +user.getFirstname()+
							"WHERE Username='" +user.getUsername() + "'") ;
					return;
			//	}
			}
			Statement insertInto = conn.createStatement();
			ResultSet rs1 = insertInto.executeQuery("INSERT INTO User VALUES(" + user.getUsername()+","
					+ user.getPassword() +"," + user.getEmail() + "," + user.getDateOfBirth() + "'"
					+ user.getPhone() + "'" + user.getSurname() + "'" + user.getFirstname() + ")");
		}
		else if(object instanceof Room){
			Room room = (Room) object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT RoomID FROM Room WHERE RoomID='" + room.getId() + "'");
			while(rs.next()){
			//	if(room.getId() == rs.getInt("RoomID")){
					Statement update = conn.createStatement();
					ResultSet rs2 = update.executeQuery("UPDATE Room SET Name='" + room.getName() + "', Capacity='" 
							+ room.getCapacity()+"' WHERE RoomID='" + room.getId() +"'");
					return;
				//}
			}
				Statement insertInto = conn.createStatement();
				ResultSet rs1 = insertInto.executeQuery("INSERT INTO Room VALUES(" + room.getId() + "," 
						+ room.getName() + "'" + room.getCapacity() + ")");
			}
		else if(object instanceof Appointment){
			Appointment appment = (Appointment) object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT AID FROM Appointment WHERE AID='" + appment.getId() +"'" );
			while(rs.next()){
			//	if(appment.getId() == rs.getInt("AID")){
					Statement update = conn.createStatement();
					ResultSet rs2 = update.executeQuery("UPDATE Appointment SET Date='" + appment.getDate() + "', Start='" 
							+appment.getStartTime() +"', End='" + appment.getEndTime() + "', Description='" 
							+ appment.getDescription() + "', Location='" + appment.getLocation() +"' WHERE AID='" +appment.getId() + "'");
					return;
			//	}
			}
			Statement insertInto = conn.createStatement();
			ResultSet rs1 = insertInto.executeQuery("INSERT INTO Appointment VALUES(" +appment.getId() +"," 
					+ appment.getDate() + "," + appment.getStartTime() +"," +appment.getEndTime() +"," 
					+ appment.getDescription() +"," + appment.getLocation() + ")" );
		}
		else if(object instanceof Notification){
			Notification not= (Notification) object;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NotificationID FROM Notification WHERE NOtficationID='" +not.getId() + "'");
			while(rs.next()){
				//if(not.getId() == rs.getInt("NotificationID")){
					Statement update = conn.createStatement();
					ResultSet rs2 = update.executeQuery("UPDATE Notification SET Type='" + not.getType() 
							+"' WHERE NotificationID='" + not.getId() +"'");
					return;
				//}
			}
			Statement insertInto = conn.createStatement();
			ResultSet rs1 = insertInto.executeQuery("INSERT INTO Notification VALUES(" + not.getId() +"," 
					+ not.getType() +")");
		}
		
		
		//sjekke om objekte ligger i databasen  object instance of User
		//hvis ja: oppdater databasen med ny informasjon om objectet
		//hvis nei: Legge til det nye objektet i databasen
	}
	
	public void load(){
		//når servern starter så hentes alle objektene ut fra databasen og lage objektene som ligger i databasen 
		//så for hver user i databasen skal det lages et User objekt ved hjelp av user-klassen sin konstruktør.
		
		
	} 
}
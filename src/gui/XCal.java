package gui;


import javax.swing.JTextField;

import synclogic.ClientSynchronizationUnit;

public class XCal {
	
	private static ClientSynchronizationUnit CSU;
	static JTextField usernameField;
	private static String serverIp = "78.91.80.54";
//	private static String serverIp = "localhost";
	public static void main(String[] args) {
		CSU=new ClientSynchronizationUnit();
		LogIn logIn = new LogIn();
		logIn.setVisible(true);
	}

	public static ClientSynchronizationUnit getCSU() {
		return CSU;
	}
	
	public static String getServerIp(){
		return serverIp;
	}
	
}

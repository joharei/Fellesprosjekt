package gui;


import java.awt.Dimension;

import javax.swing.JTextField;

import synclogic.ClientSynchronizationUnit;

public class XCal {
	
	private static ClientSynchronizationUnit CSU;
	static JTextField usernameField;
	public static void main(String[] args) {
		CSU=new ClientSynchronizationUnit();
		LogIn logIn = new LogIn();
		logIn.pack();
		logIn.setPreferredSize(new Dimension(200,600));
		logIn.setVisible(true);
	}

	public static ClientSynchronizationUnit getCSU() {
		return CSU;
	}
	
}

package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.synth.Region;

public class LogIn extends JPanel{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel username, password, logInPhoto;
	private GridBagConstraints gridMain, gridSlave;
	private GridBagLayout gbLayoutMain, gbLayoutSlave;
	private JButton logInButton;
	
	private JPanel subPanel;
	
	private ImageIcon logIn = new ImageIcon(getClass().getResource("art/logIn/login.png"));
	
	public LogIn(){
		
		subPanel = new JPanel();
		
		//main layout
		gbLayoutMain = new GridBagLayout();
		gridMain = new GridBagConstraints();
		setLayout(gbLayoutMain);
		
		//sub layout
		gbLayoutSlave = new GridBagLayout();
		gridSlave = new GridBagConstraints();
		gridSlave.ipadx = 10;
		
		//logIn photo
		logInPhoto = new JLabel();
		gridMain.gridx = 0;
		gridMain.gridy = 0;
		logInPhoto.setIcon(logIn);
		add(logInPhoto, gridMain);
		
		//main grid
		gridMain.gridx = 1;
		add(subPanel, gridMain);
		subPanel.setLayout(gbLayoutSlave);
		
		Font font1 = new Font("Helvetica LT Condensed", Font.BOLD, 20);
		Font font2 = new Font("Helvetica LT Condensed", Font.PLAIN, 20);
		
		
		//username 
		username = new JLabel("Username: ");
		username.setFont(font1);
		gridSlave.gridx = 0;
		gridSlave.gridy = 0;
		gridSlave.insets = new Insets(0, 50, 0, 0);
		subPanel.add(username, gridSlave);
		
		//username field
		usernameField = new JTextField();
		usernameField.setFont(font2);
		gridSlave.gridx = 1;
		gridSlave.gridy = 0;
		gridSlave.insets = new Insets(0, 0, 0, 0);
		usernameField.setColumns(20);
		subPanel.add(usernameField, gridSlave);
		
		//password
		password = new JLabel("Password: ");
		password.setFont(font1);
		gridSlave.gridx = 0;
		gridSlave.gridy = 1;
		gridSlave.insets = new Insets(0, 50, 0, 0);
		subPanel.add(password, gridSlave);
		
		//password field
		passwordField = new JPasswordField();
		passwordField.setFont(font2);
		gridSlave.gridx = 1;
		gridSlave.gridy = 1;
		gridSlave.insets = new Insets(0, 0, 0, 0);
		passwordField.setColumns(20);
		subPanel.add(passwordField, gridSlave);
		

		//login button
		logInButton = new JButton("Log In");
		logInButton.setFont(font1);
		gridSlave.gridx = 1;
		gridSlave.gridy = 2;
		gridSlave.insets = new Insets(10, 220, 0, 0);
		subPanel.add(logInButton, gridSlave);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Log In");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set dimension size
		frame.setContentPane(new LogIn());
		frame.setPreferredSize(new Dimension(1000,500));
		
		frame.pack();
		frame.setVisible(true);
	}

}

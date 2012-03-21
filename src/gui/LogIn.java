package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import synclogic.ClientSynchronizationUnit;

public class LogIn extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel username, password, logInPhoto;
	private GridBagConstraints gridMain, gridSlave;
	private GridBagLayout gbLayoutMain, gbLayoutSlave;
	private JButton logInButton;
	ClientSynchronizationUnit CSU;
	private JPanel subPanel;
	
	private ImageIcon logIn = new ImageIcon(getClass().getResource("art/logIn/login.png"));
	
	/**
	 * @param args
	 */
	
	public LogIn(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		addActionListeners();
	}
	
	private void addActionListeners() {
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("log in!!");
				JFrame gmain = new GUI();
				gmain.setVisible(true);
				gmain.pack();
				setVisible(false);
			}
		});
	}


}

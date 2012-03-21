package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

import synclogic.ClientSynchronizationUnit;

public class LogIn extends JFrame implements WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel username, password, logInPhoto;
	private GridBagConstraints gridMain, gridSlave;
	private GridBagLayout gbLayoutMain, gbLayoutSlave;
	private JButton logInButton;
	private JLabel nameError;
	
	private JPanel subPanel;
	private Icon icon = UIManager.getIcon("OptionPane.errorIcon");
	private ImageIcon logIn = new ImageIcon(getClass().getResource("art/logIn/login.png"));
	private JProgressBar current;
	/**
	 * @param args
	 */
	
	public LogIn(){
		
		
		XCal.getCSU().connectToServer("localhost", 1337);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		subPanel = new JPanel();
		/*
		 * 
		 * Glemte hva du viste meg før du dro for å lage den progressbar greia. 
		 * Lette litt rundt omkring på nettet, men fant ikke noe særlig. Lykke til :)
		 * Sees på fredag :)
		 * 
		 * Magnus
		 * 
		 * 
		 */
		
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
		
		
		//errorLabel
		nameError = new JLabel();
		nameError.setIcon(icon);
		nameError.setText("Invalid username and/or password");
		gridSlave.gridx=1;
		gridSlave.gridy=0;
		nameError.setVisible(false);
		subPanel.add(nameError,gridSlave);
		//username 
		username = new JLabel("Username: ");
		username.setFont(font1);
		gridSlave.gridx = 0;
		gridSlave.gridy = 1;
		gridSlave.insets = new Insets(0, 50, 0, 0);
		subPanel.add(username, gridSlave);
		
		//username field
		XCal.usernameField = new JTextField();
		XCal.usernameField.setFont(font2);
		gridSlave.gridx = 1;
		gridSlave.gridy = 1;
		gridSlave.insets = new Insets(0, 0, 0, 0);
		XCal.usernameField.setColumns(20);
		subPanel.add(XCal.usernameField, gridSlave);
		
		
		//password
		password = new JLabel("Password: ");
		password.setFont(font1);
		gridSlave.gridx = 0;
		gridSlave.gridy = 2;
		gridSlave.insets = new Insets(0, 50, 0, 0);
		subPanel.add(password, gridSlave);
		
		//password field
		passwordField = new JPasswordField();
		passwordField.setFont(font2);
		gridSlave.gridx = 1;
		gridSlave.gridy = 2;
		gridSlave.insets = new Insets(0, 0, 0, 0);
		passwordField.setColumns(20);
		subPanel.add(passwordField, gridSlave);
		

		//login button
		logInButton = new JButton("Log In");
		logInButton.setFont(font1);
		gridSlave.gridx = 1;
		gridSlave.gridy = 3;
		gridSlave.insets = new Insets(10, 220, 0, 0);
		subPanel.add(logInButton, gridSlave);
		
		addActionListeners();
	}
	
	private void addActionListeners() {
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("log in!!");
				char [] liste  = passwordField.getPassword();
				String passord="";
				for (char c : liste) {
					passord+=c;
				}
				try {
					if(XCal.getCSU().logIn(XCal.usernameField.getText(), passord)==false){
						System.out.println("feil brukernavn eller passord");
						nameError.setVisible(true);
						
					}
					else{
						System.out.println("Suksess!");
						JFrame gmain = new GUI();
						gmain.setVisible(true);
						gmain.pack();
						setVisible(false);
					}
				} catch (ConnectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		XCal.getCSU().disconnect();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


}

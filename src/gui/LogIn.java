package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
	private JDialog progressWindow;
	private JProgressBar progressBar;
	Thread progressThread;
	/**
	 * @param args
	 */
	
	public LogIn(){
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		subPanel = new JPanel();
		/*
		 * 
		 * Glemte hva du viste meg f�r du dro for � lage den progressbar greia. 
		 * Lette litt rundt omkring p� nettet, men fant ikke noe s�rlig. Lykke til :)
		 * Sees p� fredag :)
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
//		nameError.setText("Invalid username and/or password");
		gridSlave.gridx=1;
		gridSlave.gridy=0;
		nameError.setVisible(false);
		subPanel.add(nameError,gridSlave);
		
		try {
			XCal.getCSU().connectToServer(XCal.getServerIp(), 1337);
		} catch (IOException ex){
			nameError.setText("Could not connect to server! Please restart to try again.");
			nameError.setVisible(true);
		}
		
		
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
		JFrame nullFrame = null;
		progressWindow = new JDialog(nullFrame, "Logging in...", true);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressWindow.setBounds(getWidth()/2-300/2, getHeight()/2-75/2, 300, 75);
		progressWindow.setResizable(false);
		progressWindow.add(progressBar);
//		progressWindow.setVisible(true);
//			progressWindow.setModalityType(ModalityType.APPLICATION_MODAL);
//		progressWindow.pack();
	}
	
	private void addActionListeners() {
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressThread = new Thread(new ProgressBar());
				progressThread.start();
				
				char [] liste  = passwordField.getPassword();
				String passord="";
				for (char c : liste) {
					passord+=c;
				}
				try {
					
					if(XCal.getCSU().logIn(XCal.usernameField.getText(), passord)==false){
//						progressThread.interrupt();
//						progressWindow.removeAll();
//						progressWindow.dispose();
						progressWindow.setVisible(false);
						System.out.println("feil brukernavn eller passord");
						nameError.setText("Invalid username and/or password");
						nameError.setVisible(true);
						
					}
					else{
//						progressThread.interrupt();
//						progressWindow.dispose();
						progressWindow.setVisible(false);
						System.out.println("Suksess!");
						JFrame gmain = new GUI();
						gmain.setVisible(true);
						gmain.pack();
						setVisible(false);
					}
				} catch (ConnectException e1) {
					progressWindow.setVisible(false);
//					progressThread.interrupt();
//					progressWindow.dispose();
					nameError.setText("Could not connect to server!");
					nameError.setVisible(true);
				}
				
			}
		});
	}
	
	class ProgressBar implements Runnable{

		@Override
		public void run() {
			progressWindow.setVisible(true);
		}
		
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

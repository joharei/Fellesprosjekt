package synclogic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import model.SaveableClass;
import model.XmlSerializerX;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;
import nu.xom.ParsingException;

public class ClientSynchronizationUnit extends SynchronizationUnit implements PropertyChangeListener {
	
	private MessageQueue sendQueue, receiveQueue;
	private Connection connection;
	private LoginRequest loginRequest;
	private Thread thread;
	private boolean stopThread = false;
	
	public ClientSynchronizationUnit(){
		this.sendQueue = new MessageQueue();
		this.sendQueue.addPropertyChangeListener(this);
		thread = new Thread(new testClass());
	}
	
	@Override
	public void addToSendQueue(String o) {
		this.sendQueue.add(o);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (!this.thread.isAlive()){
			this.stopThread = false;
			this.thread.start();
		}
	}
	
	private synchronized void internalWait(int time){
		try {
			wait(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class testClass implements Runnable{

		@Override
		public void run() {
			internalWait(50);
			while(!stopThread){
				while (!sendQueue.isEmpty()){
					try {
						connection.send(sendQueue.pop());
					} catch (ConnectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					internalWait(30);
				}
				internalWait(50);
			}
		}
		
	}
	
	/**
	 * Sets up a connection to a server
	 * @param ipAddress		The IP address for the server to connect to
	 * @param port			The port to connect to
	 */
	public void connectToServer(String ipAddress, int port){
		// TODO: Should the port really be 9999?
		this.connection = new ConnectionImpl(9999);
		try {
			this.connection.connect(InetAddress.getByName(ipAddress), port);
			internalWait(50);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Logs in the user
	 * @param username		The username to log in
	 * @param password		The password that belongs to the username
	 */
	public void logIn(String username, String password){
		this.loginRequest = new LoginRequest(username, password);
		try {
			this.connection.send(XmlSerializerX.toXml(this.loginRequest, SaveableClass.LoginRequest));
			LoginRequest respons = (LoginRequest) XmlSerializerX.toObject(this.connection.receive());
			System.out.println(respons.getLoginAccepted());
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes this connection
	 */
	public void disconnect(){
		while(true){
			try {
				if (this.sendQueue.isEmpty()){
					internalWait(500);
					this.connection.close();
					this.stopThread = true;
					return;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			internalWait(50);
		}
	}
	
	
	
	public static void main(String[] args){
		ClientSynchronizationUnit syncUnit = new ClientSynchronizationUnit();
		syncUnit.connectToServer("78.91.83.49", 1337);
		syncUnit.logIn("joharei", "1234");
//		for (int i = 0; i<10; i++){
//			syncUnit.addToSendQueue("Element " + i);
//		}
		syncUnit.disconnect();
	}
	
}

package synclogic;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import model.User;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;

public class ServerSynchronizationUnit extends SynchronizationUnit {

	// Loaded objects
	private List<User> users;
	
	private List<Queue<String>> sendQueues, receiveQueues;
	private List<ClientHandler> activeUserConnections;
	private DatabaseUnit dbUnit;
	/**
	 * The connection that will be used to listen for incomming connections
	 */
	private Connection connection;
	
	public ServerSynchronizationUnit() {
		super();
		this.activeUserConnections = new ArrayList<ClientHandler>();
		this.sendQueues = new ArrayList<Queue<String>>();
		this.receiveQueues = new ArrayList<Queue<String>>();
		this.dbUnit = new DatabaseUnit();
		// TODO: LOADING!!!
		//dbUnit.load();
	}

	@Override
	public void addToSendQueue(String xml) {
		// TODO Auto-generated method stub
		throw new RuntimeException("NOT YET IMPLEMENTED!!");
	}

	public void listenForUserConnections(int port) {
		this.connection = new ConnectionImpl(port);
		while(true) {
			try {
				startUserSession(this.connection.accept());
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public User getUser(String username) {
		for (User u : this.users) {
			if(u.getUsername().equalsIgnoreCase(username)) {
				return u;
			}
		}
		return null;
	}
	
	private void startUserSession(Connection con) {
		ClientHandler ch = new ClientHandler(con, this);
		this.activeUserConnections.add(ch);
		new Thread(ch).start();
	}
	
}

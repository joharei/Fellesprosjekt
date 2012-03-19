package synclogic;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import model.SaveableClass;
import model.User;
import model.XmlSerializerX;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;
import nu.xom.Document;

public class ServerSynchronizationUnit extends SynchronizationUnit {

	private Queue<Document> sendQueue, receiveQueue;
	private Map<Connection, User> activeUserConnections;
	private DatabaseUnit dbUnit;
	/**
	 * The connection that will be used to listen for incomming connections
	 */
	private Connection connection;
	
	public ServerSynchronizationUnit() {
		super();
		this.activeUserConnections = new HashMap<Connection, User>();
		this.sendQueue = new LinkedList<Document>();
		this.receiveQueue = new LinkedList<Document>();
	}

	@Override
	public void addToSendQueue(Object o) {
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
	
	private void startUserSession(Connection con) {
		LoginRequest loginRequest = XmlSerializerX.toObject(con.receive());
		User user = (User) getObjectFromID(SaveableClass.User, loginRequest.getUsername());
	}

}

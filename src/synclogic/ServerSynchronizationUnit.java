package synclogic;

import java.util.List;
import java.util.Queue;

import model.User;
import no.ntnu.fp.net.co.Connection;
import nu.xom.Document;

public class ServerSynchronizationUnit extends SynchronizationUnit {

	private Queue<Document> sendQueue, receiveQueue;
	private List<Connection> connections;
	private List<User> activeUsers;
	private DatabaseUnit dbUnit;
	
	public ServerSynchronizationUnit() {
		super();
	}

	@Override
	public void addToSendQueue(Object o) {
		// TODO Auto-generated method stub
		throw new RuntimeException("NOT YET IMPLEMENTED!!");
	}

	
}

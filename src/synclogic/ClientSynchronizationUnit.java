package synclogic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.SaveableClass;
import model.XmlSerializerX;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;

public class ClientSynchronizationUnit extends SynchronizationUnit implements PropertyChangeListener {
	
	private MessageQueue sendQueue, receiveQueue;
	private ArrayList<String> test;
	private java.beans.PropertyChangeSupport propChangeSupp;
	private Connection connection;
	private LoginRequest loginRequest;
	
	public ClientSynchronizationUnit(){
		this.sendQueue = new MessageQueue();
		this.sendQueue.addPropertyChangeListener(this);
	}
	
	@Override
	public void addToSendQueue(String o) {
		this.sendQueue.add(o);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO: begin sending
	}
	
	void connectToServer(String username, String password, String ipAddress, int port){
		this.connection = new ConnectionImpl(9999);
		try {
			this.connection.connect(InetAddress.getByName(ipAddress), port);
			this.loginRequest = new LoginRequest(username, password);
			this.connection.send(XmlSerializerX.toXml(this.loginRequest, SaveableClass.LoginRequest));
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
	
	public static void main(String[] args){
		ClientSynchronizationUnit syncUnit = new ClientSynchronizationUnit();
//		syncUnit.connectToServer(username, password, ipAddress, port)
		syncUnit.addToSendQueue("Hei");
		syncUnit.addToSendQueue("Enda ett element");
	}
	
}

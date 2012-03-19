package synclogic;

import no.ntnu.fp.net.co.Connection;
import model.SaveableClass;
import model.User;
import model.XmlSerializerX;

public class ClientHandler implements Runnable {

	private Connection connection;
	private ServerSynchronizationUnit serverSynchronizationUnit;
	private User user;
	
	public ClientHandler(Connection con, ServerSynchronizationUnit ssu) {
		this.connection = con;
		this.serverSynchronizationUnit = ssu;
	}
	
	@Override
	public void run() {
		// Receive login request
		try {
			this.receive(this.login((LoginRequest) XmlSerializerX.toObject(this.connection.receive())));
		} catch (Exception e) {
			// Do nothing
		}
	}
	
	public boolean login(LoginRequest loginRequest) {
		try {
			// Get the user
			User user = serverSynchronizationUnit.getUser(loginRequest.getUsername());
			// Check if login is OK
			if(user == null) {
				loginRequest.setLoginAccepted(false);
			} else {
				loginRequest.setLoginAccepted(loginRequest.getUsername().equalsIgnoreCase(user.getUsername()) && user.getPassword().equals(loginRequest.getPassword()) ? true : false);
			}
			this.connection.send(XmlSerializerX.toXml(loginRequest, SaveableClass.LoginRequest));
			user.setOnline(loginRequest.getLoginAccepted());
			if(loginRequest.getLoginAccepted()) {
				this.user = user;
			}
			return loginRequest.getLoginAccepted();
		} catch (Exception e) {
			this.user = null;
			return false;
		}
	}
	
	public void receive(boolean isloggedIn) {
		while(true) {
			try {
				// TODO: Timeout!
				Object o = XmlSerializerX.toObject(this.connection.receive());
				if(o instanceof LoginRequest && !isloggedIn) {
					isloggedIn = this.login((LoginRequest) o);
				} else {
					// TODO: Behandle request!
				}
			} catch (Exception e) {
				// TODO
			}
		}
	}
}

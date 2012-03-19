package synclogic;

public class LoginRequest {

	private String username, password;
	private boolean loginAccepted;
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setLoginAccepted(boolean b) {
		this.loginAccepted = b;
	}
	
	public boolean getLoginAccepted() {
		return this.loginAccepted;
	}
}

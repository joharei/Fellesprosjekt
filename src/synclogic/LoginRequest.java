package synclogic;

public class LoginRequest {

	private String username, password;
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPaString() {
		return this.password;
	}
}

package synclogic;

public class LoginRequest {

	private String username, password;
	private boolean loginAccepted;
	
	//constants
	public static final String NAME_PROPERTY_CLASSTYPE = "loginRequest";
	public static final String NAME_PROPERTY_USERNAME = "username";
	public static final String NAME_PROPERTY_PASSWORD = "password";
	public static final String NAME_PROPERTY_LOGIN_ACCEPTED = "loginAccepted";
	public static final String NAME_PROPERTY_WEEK_NUMBER = "weeknb";
	
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

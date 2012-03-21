package synclogic;

public class ErrorMessage {

	private SyncListener validObject, invalidObject;
	
	public ErrorMessage(SyncListener validObject) {
		this.validObject = validObject;
	}
	
	public ErrorMessage(SyncListener validObject, SyncListener invalidObject) {
		this.validObject = validObject;
		this.invalidObject = invalidObject;
	}
	
	public SyncListener getValidObject() {
		return this.validObject;
	}
	
	public SyncListener getInvalidObject() {
		return this.invalidObject;
	}
}

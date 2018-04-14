package friendbook.exceptions;

public class InvalidPasswordException extends Exception {

	@Override
	public String getMessage() {
		return "Incorrect password.";
	}
	
}

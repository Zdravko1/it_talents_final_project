package friendbook.exceptions;

public class IncorrectUserNameException extends Exception {

	@Override
	public String getMessage() {
		return "Incorrect username.";
	}
	
}

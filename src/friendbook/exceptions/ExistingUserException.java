package friendbook.exceptions;

public class ExistingUserException extends Exception {

	
	@Override
	public String getMessage() {
		return "User with the same name and/or email exists already.";
	}
}

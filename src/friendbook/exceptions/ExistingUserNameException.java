package friendbook.exceptions;

public class ExistingUserNameException extends Exception{

	@Override
	public String getMessage() {
		return "Username has been taken.";
	}
	
}

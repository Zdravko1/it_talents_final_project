package friendbook.model.user;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.IncorrectUserNameException;
import friendbook.exceptions.InvalidEmailException;
import friendbook.exceptions.InvalidPasswordException;

public class User {

	private static final String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private static final int MIN_NAME_LENGTH = 6;
	private static final int MAX_NAME_LENGTH = 20;
	
	private int id;
	private String username;
	private String password; //bcrypt
	private String email;
	private String firstName;
	private String lastName;
	private int followers;
	
	private Set<User> following; //users who are followed by this user
	
	public User(String username, String password, String email, String firstName, String lastName) throws Exception {
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	public User(int id, String username, String password, String email, String firstName, String lastName) throws Exception {
		this(username, password, email, firstName, lastName);
		this.id = id;
	}
	
	
	//getters
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getFollowers() {
		return followers;
	}

	public Set<User> getFollowing() {
		return Collections.unmodifiableSet(following);
	}

	
	//setters
	public void setUsername(String username) throws Exception {
		if(nameCheck(username)) {
			this.username = username;
		}
	}

	public void setPassword(String password) throws InvalidPasswordException {
		if(passwordCheck(password)) {
			this.password = password;
		}
	}

	public void setEmail(String email) throws InvalidEmailException {
		if(emailCheck(email)) {
			this.email = email;
		}
	}

	public void setFirstName(String firstName) {
		if(firstName != null && firstName.length() > 1) {
			this.firstName = firstName;
		}
	}

	public void setLastName(String lastName) {
		if(lastName != null && lastName.length() > 1) {
			this.lastName = lastName;
		}
	}

	public void increaseFollowersByOne() {
		this.followers += 1;
	}

	public void addToFollowing(User u) {
		if(u != null) {
			this.following.add(u);
		}
	}
	
	
	//validations
	private boolean passwordCheck(String password) throws InvalidPasswordException {
		if(password.matches(User.PASS_REGEX)) {
			return true;
		}
		throw new InvalidPasswordException();
	}
	
	private boolean emailCheck(String email) throws InvalidEmailException {
		if(email.matches(User.EMAIL_REGEX)) {
			return true;
		}
		throw new InvalidEmailException();
	}
	
	private boolean nameCheck(String name) throws Exception{
		if(name != null 
				&& name.length() >= User.MIN_NAME_LENGTH 
				&& name.length() <= User.MAX_NAME_LENGTH 
				&& !UserDao.getInstance().existingUserNameCheck(name)){
			return true;
		}
		throw new IncorrectUserNameException();
	}
}

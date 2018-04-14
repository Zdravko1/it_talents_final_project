package friendbook.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.exceptions.ExistingUserException;
import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.IncorrectUserNameException;
import friendbook.exceptions.InvalidEmailException;
import friendbook.exceptions.InvalidPasswordException;
import friendbook.exceptions.WrongCredentialsException;
import friendbook.model.user.User;
import friendbook.model.user.UserDao;

public class UserManager {
	private static UserManager instance;

	public static synchronized UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	private UserManager() {
		
	}
	
	
	//TODO CHANGE
	public void sessionCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.isNew()) {
			request.getRequestDispatcher("login.html").forward(request, response);
		}
		else {
			request.getRequestDispatcher("landingPage.jsp").forward(request, response);
		}
	}
	
	public boolean login(String username, String password) throws SQLException, WrongCredentialsException {
		try {
			UserDao.getInstance().loginCheck(username, password);
			return true;
		} catch (SQLException e) {
			System.out.println("Sori, ama ima bug: " + e.getMessage());
			throw e;
		}
	}
//	
//	public boolean register(String username, String password, String email) throws InvalidPasswordException, IncorrectUserNameException, ExistingUserNameException, InvalidEmailException, ExistingUserException {
////		//validations
////		User u = new User(username, password, email);
////		try {
////			//validate name, pass and email
////			UserDao.getInstance().registerCheck(username, password, email);
////			//check if same user exists
////			UserDao.getInstance().existingUserCheck(username, email);
////			//save in db
////			UserDao.getInstance().saveUser(u);
////			return true;
////		} catch (SQLException e) {
////			System.out.println("Sori, ama ima bug: " + e.getMessage());
////			return false;
////		}
//	}
//	
//	public Collection<User> getAll(){
//		try {
//			return UserDao.getInstance().getAllUsers();
//		} catch (SQLException e) {
//			return Collections.EMPTY_LIST;
//		}
//	}
//
//	public User getUserFromId(int id) throws SQLException {
//		return UserDao.getInstance().getByID(id);
//	}
}

package friendbook.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
import friendbook.model.post.Post;
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
		if(session.isNew() || session.getAttribute("user") == null) {
			request.getRequestDispatcher("index.html").forward(request, response);
		}
		else {
			try {
				User user = (User) session.getAttribute("user");
				List<Post> posts = UserManager.getInstance().getPostsByUserID(user.getId());
				request.setAttribute("posts", posts);
			} catch (SQLException e) {
				System.out.println("SQL bug: " + e.getMessage());
			}
			request.getRequestDispatcher("index2.jsp").forward(request, response);
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
	
	public boolean register(User u) throws ExistingUserException, SQLException {
		try {
			//check if same user exists
			UserDao.getInstance().existingUserCheck(u.getUsername(), u.getEmail());
			//save in db
			UserDao.getInstance().saveUser(u);
			return true;
		} catch (SQLException e) {
			System.out.println("Sori, ama ima bug: " + e.getMessage());
			throw e;
		}
	}
	
	public User getUser(int id) throws SQLException {
		return UserDao.getInstance().getByID(id);
	}
	
	public User getUser(String username) throws SQLException {
		return UserDao.getInstance().getUserByUsername(username);
	}

	public List<Post> getPostsByUserID(long id) throws SQLException {
		return UserDao.getInstance().getPostsByUserID(id);
	}

	public boolean isPostLiked(User u, int id) throws SQLException {
		return UserDao.getInstance().isPostLiked(u, id);
	}
	
}

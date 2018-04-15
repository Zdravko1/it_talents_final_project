package friendbook.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import friendbook.exceptions.ExistingUserException;
import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.IncorrectUserNameException;
import friendbook.exceptions.InvalidEmailException;
import friendbook.exceptions.InvalidPasswordException;
import friendbook.exceptions.WrongCredentialsException;
import friendbook.model.comment.Comment;
import friendbook.model.post.Post;

public class UserDao implements IUserDao{
	
	private static UserDao instance;
	private Connection connection;

	public static UserDao getInstance() {
		if(instance == null) {
			synchronized (UserDao.class) {
				if(instance == null) {
					instance = new UserDao();
				}
			}
		}
		return instance;
	}
	
	private UserDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	
	
	@Override
	public User getUserByUsername(String username) throws SQLException {
		String query = "SELECT id, username, password, email, first_name, last_name FROM users WHERE username = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
			ps.close();
			return u;
		}
	}

	@Override
	public User getByID(int id) throws SQLException {
		User u = null;
		String query = "SELECT * FROM users WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
			ps.close();
			return u;
		}
	}

	@Override
	public void saveUser(User u) throws SQLException {
		try(PreparedStatement ps = connection.prepareStatement("INSERT INTO users(username, password, email, first_name, last_name) VALUES(?, ?, ?, ?, ?)")){
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getFirstName());
			ps.setString(5, u.getLastName());
			ps.executeUpdate();
			ps.close();
		}
		
	}

	@Override
	public void post(User u, Post post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commentToPost(User u, Comment comment, Post post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reply(User u, Comment comment, Comment reply) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void likePost(Post post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void likeComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void loginCheck(String username, String password) throws WrongCredentialsException, SQLException {
		try(PreparedStatement ps = connection.prepareStatement("SELECT username, password FROM users WHERE username = ? AND password = ?")){
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				throw new WrongCredentialsException();
			}
			ps.close();
		}
	}
	
	public void existingUserCheck(String username, String email) throws SQLException, ExistingUserException {
		try(PreparedStatement ps = connection.prepareStatement("SELECT username, email FROM users WHERE username = ? AND email = ?")){
			ps.setString(1, username);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();
			//TODO check if this works
			if(rs.next()) {
				throw new ExistingUserException();
			}
			ps.close();
		}
	}

	@Override
	public void existingUserNameCheck(String username) throws ExistingUserNameException, SQLException {
		try(PreparedStatement ps = connection.prepareStatement("SELECT username FROM users WHERE username = ?")){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			//TODO check if this works
			if(rs.next()) {
				throw new ExistingUserNameException();
			}
			ps.close();
		}
	}

}

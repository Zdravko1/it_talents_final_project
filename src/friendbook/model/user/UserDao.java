package friendbook.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import friendbook.exceptions.ExistingUserException;
import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.WrongCredentialsException;
import friendbook.model.comment.Comment;
import friendbook.model.post.Post;

public class UserDao implements IUserDao{
	
	private static UserDao instance;
	private Connection connection;

	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	private UserDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	
	@Override
	public User getByID(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User u) throws Exception {
		// TODO Auto-generated method stub
		
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

	@Override
	public void loginCheck(String username, String password) throws WrongCredentialsException, SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT username, password FROM users WHERE username = ? AND password = ?");
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()) {
			throw new WrongCredentialsException();
		}
	}

	@Override
	public boolean existingUserNameCheck(String username) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void existingEmailCheck(String email) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existingUserCheck(String username, String email) throws SQLException, ExistingUserException {
		PreparedStatement ps = connection.prepareStatement("SELECT username, email FROM users WHERE username = ? AND email = ?");
		ps.setString(1, username);
		ps.setString(2, email);
		ResultSet rs = ps.executeQuery();
		
		//TODO check if this works
		if(!rs.next()) {
			throw new ExistingUserException();
		}
	}

	
	
}

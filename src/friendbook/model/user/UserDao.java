package friendbook.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import friendbook.controller.PostManager;
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
	public User getUserByNames(String name) throws SQLException {
		String query = "SELECT id, username, password, email, first_name, last_name FROM users WHERE CONCAT(first_name,' ', last_name) = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
			ps.close();
			return u;
		}
	}

	@Override
	public User getByID(long id) throws SQLException {
		User u = null;
		String query = "SELECT * FROM users WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, id);
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

	@Override
	public List<Post> getPostsByUserID(long id) throws SQLException {
		LinkedList<Post> posts = new LinkedList<>();
		String query = "SELECT id, description FROM posts WHERE user_id = ? ORDER BY date DESC";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			User u = UserDao.getInstance().getByID(id);
			while(rs.next()) {
				Post p = new Post(rs.getInt("id"), u, rs.getString("description"));
				p.setLikes(PostManager.getInstance().getLikes(rs.getInt("id")));
				posts.addLast(p);
			}
			ps.close();
		}
		return posts;
	}

	public boolean isPostLiked(User u, int id) throws SQLException {
		String query = "SELECT * FROM users_likes_posts WHERE users_id = ? AND posts_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, u.getId());
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ps.close();
				return true;
			}
		}
		return false;
	}

	public List<String> getUsersNamesStartingWith(String term) throws SQLException {
		List<String> names = new ArrayList<>();
		String query = "SELECT concat(first_name,' ', last_name) AS name FROM users WHERE Concat(first_name, ' ', last_name) LIKE ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, term + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				names.add(rs.getString("name"));
			}
			ps.close();
		}
		return names;
	}

	public User getUserByUsername(String username) throws SQLException {
		String query = "SELECT * FROM users WHERE username = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
			ps.close();
			return u;
		}
	}

}

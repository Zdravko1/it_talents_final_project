package friendbook.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import friendbook.controller.PostManager;
import friendbook.exceptions.ExistingUserException;
import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.WrongCredentialsException;
import friendbook.model.comment.Comment;
import friendbook.model.post.Post;
import friendbook.model.post.PostDao;

public class UserDao implements IUserDao {

	private static UserDao instance;
	private Connection connection;

	public static UserDao getInstance() {
		if (instance == null) {
			synchronized (UserDao.class) {
				if (instance == null) {
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
			User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
					rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"));
			ps.close();
			return u;
		}
	}

	@Override
	public User getByID(long id) throws SQLException {
		User u = null;
		String query = "SELECT * FROM users WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"),
					rs.getString("first_name"), rs.getString("last_name"));
			ps.close();
			return u;
		}
	}

	@Override
	public void saveUser(User u) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO users(username, password, email, first_name, last_name) VALUES(?, ?, ?, ?, ?)")) {
			ps.setString(1, u.getUsername());
			String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
			ps.setString(2, hashedPassword);
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getFirstName());
			ps.setString(5, u.getLastName());
			ps.executeUpdate();
			ps.close();
		}

	}
		
	@Override
	public void followUser(User user, long followedId) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO users_has_users (user_id, user_id_follower) VALUES (?,?)")) {	
			ps.setLong(1, followedId);
			ps.setLong(2, user.getId());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	@Override
	public void loginCheck(String username, String password) throws WrongCredentialsException, SQLException {
		try (PreparedStatement ps = connection.prepareStatement("SELECT password FROM users WHERE username = ?")) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next() || !BCrypt.checkpw(password, rs.getString("password"))) {
				throw new WrongCredentialsException();
			}
			ps.close();
		}
	}

	@Override
	public void existingUserCheck(String username, String email) throws SQLException, ExistingUserException {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT username, email FROM users WHERE username = ? AND email = ?")) {
			ps.setString(1, username);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();
			// TODO check if this works
			if (rs.next()) {
				throw new ExistingUserException();
			}
			ps.close();
		}
	}

	@Override
	public void existingUserNameCheck(String username) throws ExistingUserNameException, SQLException {
		try (PreparedStatement ps = connection.prepareStatement("SELECT username FROM users WHERE username = ?")) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			// TODO check if this works
			if (rs.next()) {
				throw new ExistingUserNameException();
			}
			ps.close();
		}
	}

	@Override
	public List<Post> getPostsByUserID(long id) throws SQLException {
		LinkedList<Post> posts = new LinkedList<>();
		String query = "SELECT id, description FROM posts WHERE user_id = ? ORDER BY date DESC";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			User u = UserDao.getInstance().getByID(id);
			while (rs.next()) {
				Post p = new Post(rs.getInt("id"), u, rs.getString("description"));
				p.setLikes(PostManager.getInstance().getLikes(rs.getInt("id")));
				posts.addLast(p);
			}
			ps.close();
		}
		return posts;
	}
	
	@Override
	public boolean isPostLiked(User u, int id) throws SQLException {
		String query = "SELECT * FROM users_likes_posts WHERE user_id = ? AND post_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, u.getId());
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ps.close();
				return true;
			}
		}
		return false;
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public ArrayList<Post> getUserFeedByID(long id) throws SQLException {
		ArrayList<Post> feed = new ArrayList<>();
		String query = "SELECT id, image_video_path, description, date, user_id FROM posts WHERE user_id IN (SELECT user_id FROM users_has_users WHERE user_id_follower = ?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User u = getByID(rs.getInt("user_id"));
				Post p = new Post(rs.getInt("id"), rs.getString("image_video_path"), rs.getString("description"), rs.getDate("date"), u);
				p.setLikes(PostManager.getInstance().getLikes(p.getId()));
				p.setLikes(PostManager.getInstance().getLikes(rs.getInt("id")));
				feed.add(p);
			}
		}
		Collections.sort(feed, (p1, p2) -> (p2.getLikes()-p1.getLikes()));
		return feed;
	}

	@Override
	public boolean isFollower(User follower, long userId) throws SQLException {
		String query = "SELECT * FROM users_has_users WHERE user_id_follower = ? AND user_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, follower.getId());
			ps.setLong(2, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ps.close();
				return true;
			}
			ps.close();
		}
		return false;
	}

}

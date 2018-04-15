package friendbook.model.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import friendbook.model.user.DBManager;
import friendbook.model.user.User;
import friendbook.model.user.UserDao;

public class PostDao implements IPostDao {

	private static PostDao instance;
	private Connection connection;
	
	private PostDao() {
		this.connection = DBManager.getInstance().getConnection();
	}
	
	public static PostDao getInstance() {
		if(instance == null) {
			synchronized (PostDao.class) {
				if(instance == null) {
					instance = new PostDao();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void addPost(Post post) throws SQLException {
		String query;
		boolean hasImage = post.getImagePath() == null;
		if(hasImage) {
			query = "INSERT INTO posts(image_video_path, description, user_id) VALUES(?,?,?)";
		} else {
			query = "INSERT INTO posts(image_video_path, description, user_id) VALUES(null,?,?)";
		}
		PreparedStatement ps = connection.prepareStatement(query);
		if(hasImage) {
			ps.setString(1, post.getImagePath());
		}
		ps.setString(2, post.getText());
		ps.setLong(3, post.getUser().getId());
		ps.executeUpdate();
		ps.close();
		
	}

	@Override
	public void deletePost(long postId) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM posts WHERE id = ?");
		ps.setLong(1, postId);
		ps.close();
	}

	public int getLikesByID(int id) throws SQLException {
		int likes = 0;
		String query = "SELECT COUNT(user_id) AS likes FROM users_likes_posts WHERE post_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			likes = rs.getInt("likes");
		}
		return likes;
	}

	public void increasePostLike(User u, int id) throws SQLException {
		String query = "INSERT INTO users_likes_posts VALUES(?,?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, id);
			ps.setLong(2, u.getId());
			ps.executeUpdate();
			ps.close();
		}
	}

	public void decreasePostLike(User u, int id) throws SQLException {
		String query = "DELETE FROM users_likes_posts WHERE user_id = ? AND post_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, u.getId());
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
		}
	}
}

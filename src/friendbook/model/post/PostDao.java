package friendbook.model.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import friendbook.model.comment.Comment;
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
		if (instance == null) {
			synchronized (PostDao.class) {
				if (instance == null) {
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
		if (hasImage) {
			query = "INSERT INTO posts(image_video_path, description, user_id) VALUES(?,?,?)";
		} else {
			query = "INSERT INTO posts(image_video_path, description, user_id) VALUES(null,?,?)";
		}
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			if (hasImage) {
				ps.setString(1, post.getImagePath());
			}
			ps.setString(2, post.getText());
			ps.setLong(3, post.getUser().getId());
			ps.executeUpdate();
		}

	}

	@Override
	public void deletePost(long postId) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM posts WHERE id = ?")) {
			ps.setLong(1, postId);
		}
	}

	@Override
	public int getLikesByID(long id) throws SQLException {
		int likes = 0;
		String query = "SELECT COUNT(user_id) AS likes FROM users_likes_posts WHERE post_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			likes = rs.getInt("likes");
		}
		return likes;
	}
	

	@Override
	public void getAllPostsOfGivenUser(User user) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT id, image_video_path, description, date FROM posts WHERE user_id = ?")) {
			ps.setLong(1, user.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Post p = new Post(rs.getLong("id"), rs.getString("image_video_path"), rs.getString("desctription"), rs.getDate("date"), user);
				user.addPost(p);
			}
		}
	}

	@Override
	public void increasePostLike(User u, long id) throws SQLException {
		String query = "INSERT INTO users_likes_posts VALUES(?,?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			ps.setLong(2, u.getId());
			ps.executeUpdate();
		}
	}

	@Override
	public void decreasePostLike(User u, long id) throws SQLException {
		String query = "DELETE FROM users_likes_posts WHERE user_id = ? AND post_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, u.getId());
			ps.setLong(2, id);
			ps.executeUpdate();
		}
	}

}

package friendbook.model.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import friendbook.model.post.Post;
import friendbook.model.user.DBManager;
import friendbook.model.user.User;

public class CommentDao implements ICommentDao {

	private static CommentDao instance;
	private Connection connection;

	private CommentDao() {
		connection = DBManager.getInstance().getConnection();
	}

	public static CommentDao getInstance() {
		if (instance == null) {
			synchronized (Comment.class) {
				if (instance == null) {
					instance = new CommentDao();
				}
			}
		}
		return instance;
	}

	@Override
	public void likeComment(User user, Comment comment) throws SQLException {
		// TODO check if working
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO users_likes_comments (users_id, comments_id) VALUES (?,?)")) {
			ps.setLong(1, user.getId());
			ps.setLong(2, comment.getId());
			ps.executeUpdate();
		}
	}

	@Override
	public void removeLike(User user, Comment comment) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("DELETE FROM users_likes_comments WHERE users_id = ? AND comments_id = ?")) {
			ps.setLong(1, user.getId());
			ps.setLong(2, comment.getId());
			ps.executeUpdate();
		}
	}
	
	@Override
	public void getCommentsOfParentComment(Comment comment) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT text, user_id, date FROM comments WHERE user_id = ?")) {
			ps.setLong(1, comment.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Comment com = new Comment(rs.getLong("user_id"), comment.getPost(), comment.getId(),
						rs.getString("text"));
				com.setDate(rs.getTimestamp("date").toLocalDateTime());
				comment.addComment(com);
			}
		}
	}

	@Override
	public void addComment(long userId, Comment comment) throws SQLException {
		String query;
		boolean isParentless = comment.getParentComment() == null;
		if (isParentless) {
			query = "INSERT INTO comments (text, post_id, parent_id, user_id) VALUES (?,?,null,?)";
		} else {
			query = "INSERT INTO comments (text, post_id, parent_id, user_id) VALUES (?,?,?,?)";
		}
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, comment.getText());
			statement.setLong(2, comment.getPost());
			if (!isParentless) {
				statement.setLong(3, comment.getParentComment());
				statement.setLong(4, userId);
			}
			statement.setLong(3, userId);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			comment.setId(rs.getLong(1));
		}
		System.out.println("Added comment");
	}
	

	@Override
	public void getAndSetAllCommentsOfGivenPost(Post post) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT id, text, date, parent_id, user_id FROM comments WHERE post_id = ?")) {
			ps.setLong(1, post.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment(rs.getLong("user_id"), post.getId(), rs.getLong("parent_id"),
						rs.getString("text"));
				comment.setDate(rs.getTimestamp("date").toLocalDateTime());
				CommentDao.getInstance().getCommentsOfParentComment(comment);
				post.addComment(comment);
			}
		}
	}

	@Override
	public void deleteComment(long commentId) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?")) {
			statement.setLong(1, commentId);
			statement.executeUpdate();
		}
	}

	@Override
	public void changeComment(Comment comment) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement("UPDATE comments SET text = ? WHERE id = ?")) {
			statement.setString(1, comment.getText());
			statement.setLong(2, comment.getId());
			statement.executeUpdate();
		}
	}

	public boolean checkIfAlreadyLiked(User user, Comment comment) throws SQLException {
		// TODO check if working
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT users_id, comments_id FROM users_likes_comments WHERE users_id = ? AND comments_id = ?")) {
			ps.setLong(1, user.getId());
			ps.setLong(2, comment.getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		}
		return false;
	}
}

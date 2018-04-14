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
		if(instance == null) {
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
		//TODO check if working
		PreparedStatement ps = connection.prepareStatement("INSERT INTO users_likes_comments WHERE users_id = ?, comments_id = ?");
		ps.setLong(1, user.getId());
		ps.setLong(2, comment.getId());
		
		ps.close();
	}

	@Override
	public void addComment(Comment comment) throws SQLException {
		String query;
		boolean isParentless = comment.getParentComment() == null;
		if(isParentless) {
			query = "INSERT INTO comments (text, post_id, parent_id) VALUES (?,?,null)";
		} else {
			query = "INSERT INTO comments (text, post_id, parent_id) VALUES (?,?,?)";
		}
		PreparedStatement statement = connection.prepareStatement(query
				,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, comment.getText());
		statement.setLong(2, comment.getPost().getId());
		if(!isParentless) {
			statement.setLong(3, comment.getParentComment().getId());
		}
		
		statement.executeUpdate();
		ResultSet rs = statement.getGeneratedKeys();
		rs.next();
		comment.setId(rs.getLong(1));
		statement.close();
	}

	@Override
	public void deleteComment(Comment comment) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");
		statement.setLong(1, comment.getId());
		statement.executeUpdate();
		statement.close();
	}
	
	@Override
	public void changeComment(Comment comment) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE comments SET text = ? WHERE id = ?");
		statement.setString(1, comment.getText());
		statement.setLong(2, comment.getId());
		statement.executeUpdate();
		statement.close();
	}
}

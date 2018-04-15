package friendbook.model.comment;

import java.sql.SQLException;

import friendbook.model.user.User;

public interface ICommentDao {

	void addComment(User user, Comment comment) throws SQLException;

	void deleteComment(Comment comment) throws SQLException;

	void changeComment(Comment comment) throws SQLException;

	void likeComment(User user, Comment comment) throws SQLException;
	
	void removeLike(User user, Comment comment) throws SQLException;
	
	boolean checkIfAlreadyLiked(User user, Comment comment) throws SQLException;
}

package friendbook.model.comment;

import java.sql.SQLException;

import friendbook.model.post.Post;
import friendbook.model.user.User;

public interface ICommentDao {

	void addComment(long userId, Comment comment) throws SQLException;

	void deleteComment(long commentId) throws SQLException;

	void changeComment(Comment comment) throws SQLException;

	void likeComment(User user, Comment comment) throws SQLException;
	
	void removeLike(User user, Comment comment) throws SQLException;
	
	boolean checkIfAlreadyLiked(User user, Comment comment) throws SQLException;
	
	void getAndSetAllCommentsOfGivenPost(Post post) throws SQLException;
	
	void getCommentsOfParentComment(Comment comment) throws SQLException;
}

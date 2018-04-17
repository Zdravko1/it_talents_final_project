package friendbook.model.comment;

import java.sql.SQLException;

import friendbook.model.post.Post;
import friendbook.model.user.User;

public interface ICommentDao {

	void addComment(long userId, Comment comment) throws SQLException;

	void deleteComment(long commentId) throws SQLException;

	void changeComment(Comment comment) throws SQLException;

	void getAndSetAllCommentsOfGivenPost(Post post) throws SQLException;
	
	int getLikesByID(long id) throws Exception;

	void removeLike(long userId, long commentId) throws SQLException;

	boolean checkIfAlreadyLiked(long userId, long commentId) throws SQLException;

	void likeComment(long userId, long commentId) throws SQLException;
}

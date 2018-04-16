package friendbook.model.user;

import java.sql.SQLException;
import java.util.List;

import friendbook.exceptions.ExistingUserException;
import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.WrongCredentialsException;
import friendbook.model.comment.Comment;
import friendbook.model.post.Post;

public interface IUserDao {

	User getUserByNames(String name) throws SQLException;
	User getByID(long id) throws SQLException;
	void saveUser(User u) throws SQLException;
	void post(User u, Post post);
	void commentToPost(User u, Comment comment, Post post);
	void reply(User u, Comment comment, Comment reply);
	void likePost(Post post);
	void likeComment(Comment comment);
	void existingUserNameCheck(String username) throws ExistingUserNameException, SQLException;
	void existingUserCheck(String username, String email) throws SQLException, ExistingUserException;
	void loginCheck(String username, String password) throws WrongCredentialsException, SQLException;
	List<Post> getPostsByUserID(long id) throws SQLException;
}

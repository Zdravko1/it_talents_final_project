package friendbook.model.user;

import friendbook.model.comment.Comment;
import friendbook.model.post.Post;

public interface IUserDao {

	User getByID(int id) throws Exception;
	void saveUser(User u) throws Exception;
	void post(User u, Post post);
	void commentToPost(User u, Comment comment, Post post);
	void reply(User u, Comment comment, Comment reply);
	void likePost(Post post);
	void likeComment(Comment comment);
	void existingUserNameCheck(String username) throws Exception;
	void existingUserCheck(String username, String email) throws Exception;
	void loginCheck(String username, String password) throws Exception;
}

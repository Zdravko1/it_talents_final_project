package friendbook.model.post;

import java.sql.SQLException;

import friendbook.model.user.User;


public interface IPostDao {
	
	void addPost(Post post) throws Exception;
	void deletePost(long postId) throws Exception;
	int getLikesByID(long id) throws Exception;
	void increasePostLike(User u, long id) throws Exception;
	void decreasePostLike(User u, long id) throws Exception;
	
	
}

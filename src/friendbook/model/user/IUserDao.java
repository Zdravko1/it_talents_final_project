package friendbook.model.user;

import java.util.ArrayList;
import java.util.List;

import friendbook.model.post.Post;

public interface IUserDao {

	User getUserByNames(String name) throws Exception;
	User getByID(long id) throws Exception;
	void saveUser(User u) throws Exception;
	void existingUserNameCheck(String username) throws Exception;
	void existingUserCheck(String username, String email) throws Exception;
	void loginCheck(String username, String password) throws Exception;
	//TODO Delete, ima go i v postdao, i mai tam e po dobre
	List<Post> getPostsByUserID(long id) throws Exception;
	void followUser(User user, long followedId) throws Exception;
	ArrayList<Post> getUserFeedByID(long id) throws Exception;
	User getUserByUsername(String username) throws Exception;
	List<String> getUsersNamesStartingWith(String term) throws Exception;
	boolean isPostLiked(User u, int id) throws Exception;
	boolean isFollower(User follower, long userId) throws Exception;
}

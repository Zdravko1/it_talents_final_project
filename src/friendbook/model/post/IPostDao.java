package friendbook.model.post;

import java.sql.SQLException;

import friendbook.model.user.User;


public interface IPostDao {

	void deletePost(long postId) throws SQLException;
	
	int getLikesByID(long id) throws SQLException;
	
	void increasePostLike(User u, long id) throws SQLException;
	
	void decreasePostLike(User u, long id) throws SQLException;
	
	void getAllPostsOfGivenUser(User user) throws SQLException;

	void addPostWithImage(Post post) throws SQLException;

	void addPostWithoutImage(Post post) throws SQLException;
	
	String getPostImageById(long postId) throws SQLException;
}

package friendbook.model.post;

import java.sql.SQLException;


public interface IPostDao {
	
	void addPost(Post post) throws SQLException;

	void deletePost(Post post) throws SQLException;

}

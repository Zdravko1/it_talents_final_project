package friendbook.model.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import friendbook.model.user.DBManager;
import friendbook.model.user.User;
import friendbook.model.user.UserDao;

public class PostDao implements IPostDao {

	private static PostDao instance;
	private Connection connection;
	
	private PostDao() {
		this.connection = DBManager.getInstance().getConnection();
	}
	
	public static PostDao getInstance() {
		if(instance == null) {
			synchronized (PostDao.class) {
				if(instance == null) {
					instance = new PostDao();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void addPost(Post post) throws SQLException {
		
	}

	@Override
	public void deletePost(Post post) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPostWithText(Post post) throws SQLException {
		String query = "INSERT INTO posts(description, user_id) VALUES(?,?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, post.getText());
			ps.setInt(2, post.getUser().getId());
			ps.executeUpdate();
			ps.close();
		}
	}

}

package friendbook.model.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import friendbook.model.user.DBManager;

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
		// TODO Auto-generated method stub
	}

	@Override
	public void deletePost(Post post) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}

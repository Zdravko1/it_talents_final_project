package friendbook.controller;

import java.sql.SQLException;

import friendbook.model.comment.Comment;
import friendbook.model.comment.CommentDao;
import friendbook.model.user.User;

public class CommentManager {

	private static CommentManager instance;
	
	private CommentManager() {}
		

	public static synchronized CommentManager getInstance() {
		if (instance == null) {
			synchronized (CommentManager.class) {
				if (instance == null) {
					instance = new CommentManager();
				}
			}
		}
		return instance;
	}

	public void changeComment(Comment comment) throws SQLException {
		CommentDao.getInstance().changeComment(comment);
	}

	public void createComment(User user, Comment comment) throws SQLException {
		CommentDao.getInstance().addComment(user ,comment);
	}

	public void deleteComment(Comment comment) throws SQLException {
		CommentDao.getInstance().deleteComment(comment);
	}
}

package friendbook.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonElement;

import friendbook.model.comment.Comment;
import friendbook.model.comment.CommentDao;
import friendbook.model.post.Post;
import friendbook.model.post.PostDao;
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

	
	public void createComment(Comment comment) throws SQLException {
		CommentDao.getInstance().addComment(comment.getUserId() ,comment);
	}

	public void deleteComment(long commentId) throws SQLException {
		CommentDao.getInstance().deleteComment(commentId);
	}
	
	public boolean isCommentLiked(Long userId, Long commentId) throws SQLException {
		return CommentDao.getInstance().checkIfAlreadyLiked(userId, commentId);
	}


	public void decreaseCommentLikes(long id, long commentId) throws SQLException {
		CommentDao.getInstance().removeLike(id, commentId);
	}


	public void increaseCommentLikes(long userId, long commentId) throws SQLException{
		CommentDao.getInstance().likeComment(userId, commentId);
	}


	public int getLikes(long commentId) throws SQLException {
		return CommentDao.getInstance().getLikesByID(commentId);
	}


	public Comment getLastCommentByUserId(long id) throws SQLException{
		return CommentDao.getInstance().getLastCommentByUserId(id);
	}
}

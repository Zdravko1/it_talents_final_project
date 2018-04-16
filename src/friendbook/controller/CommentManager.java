package friendbook.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@SuppressWarnings("unchecked")
	public void createComment(Comment comment, HttpServletRequest request) throws SQLException {
		//TODO check if working
		CommentDao.getInstance().addComment(comment.getUser() ,comment);
		HashMap<Long, LinkedList<Comment>> posts = null;
		if(request.getAttribute("comments") == null) {
			// postId -> comment
			posts = new HashMap<>();	
		} else {
			posts = (HashMap<Long, LinkedList<Comment>>)request.getAttribute("comments");
			
		}
		if(!posts.containsKey(comment.getPost())) {
			LinkedList<Comment> comments = new LinkedList<>();
			comments.addFirst(comment);
			posts.put(comment.getPost(),comments);
		} else {
			posts.get(comment.getPost()).addFirst(comment);
		}
		request.setAttribute("posts", posts);
	}

	public void deleteComment(long commentId) throws SQLException {
		CommentDao.getInstance().deleteComment(commentId);
	}
	
	public void addPost(Post post, HttpSession session) throws SQLException {
		
	}
}

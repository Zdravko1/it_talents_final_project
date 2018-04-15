package friendbook.controller;

import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import friendbook.model.post.Post;
import friendbook.model.post.PostDao;
import friendbook.model.user.User;

public class PostManager {

	private static PostManager instance;
	
	private PostManager() {}
		

	public static synchronized PostManager getInstance() {
		if (instance == null) {
			synchronized (PostManager.class) {
				if (instance == null) {
					instance = new PostManager();
				}
			}
		}
		return instance;
	}
	
	public void addPost(Post post, HttpSession session) throws SQLException {
		PostDao.getInstance().addPost(post);
		if(session.getAttribute("posts") != null) {
			LinkedList<Post> posts = (LinkedList)session.getAttribute("posts");
			posts.addFirst(post);
			session.setAttribute("posts", posts);
		}
	}


	public int getLikes(int id) throws SQLException {
		return PostDao.getInstance().getLikesByID(id);
	}

	public void increasePostLikes(User u, int id) throws SQLException {
		PostDao.getInstance().increasePostLike(u, id);
	}


	public void decreasePostLikes(User u, int id) throws SQLException {
		PostDao.getInstance().decreasePostLike(u, id);
	}

}

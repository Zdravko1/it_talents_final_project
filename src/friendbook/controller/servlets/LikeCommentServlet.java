package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import friendbook.controller.CommentManager;
import friendbook.controller.UserManager;
import friendbook.model.user.User;

@WebServlet("/likeComment")
public class LikeCommentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long commentId = Long.parseLong(req.getParameter("like"));
		User u = (User)req.getSession().getAttribute("user");
		System.out.println(commentId);
		//check if this post was liked by the user before
		//remove like if so or add a like
		//if the page isnt his cash visited user's products in session so it can update the like count
		try {
			if(CommentManager.getInstance().isCommentLiked(u.getId(), commentId)) {
				synchronized (u) {
					CommentManager.getInstance().decreaseCommentLikes(u.getId(), commentId);
					int likes = CommentManager.getInstance().getLikes(commentId);
					System.out.println(likes);
					resp.getWriter().print(likes);
				}
//				req.setAttribute("posts", UserManager.getInstance().getPostsByUserID(u.getId()));
				//if on feed reload posts and page
				//if not "reloadVisitedUserPosts" checks if u are visiting someone's profile or not
				//and reloads corresponding page
//				if(!reloadFeedPosts(req, resp)) {
//					reloadVisitedORUserPosts(req, resp);
//				}
				
			} else {
				synchronized (u) {
					CommentManager.getInstance().increaseCommentLikes(u.getId(), commentId);
					resp.getWriter().print(CommentManager.getInstance().getLikes(commentId));
				}
			//	req.setAttribute("posts", UserManager.getInstance().getPostsByUserID(u.getId()));
//				if(!reloadFeedPosts(req, resp)) {
//					reloadVisitedORUserPosts(req, resp);
//				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
	}
	
	private boolean reloadFeedPosts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		User u = (User) req.getSession().getAttribute("user");
		boolean onFeed = req.getSession().getAttribute("feed") != null;
		if(onFeed) {
			req.getSession().setAttribute("feed", UserManager.getInstance().getUserFeed(u.getId()));
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return true;
		}
		return false;
	}
	
	private void reloadVisitedORUserPosts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		User visited = (User)req.getSession().getAttribute("visitedUser");
		if(visited != null) {
			req.getSession().setAttribute("visitedUserPosts", UserManager.getInstance().getPostsByUserID(visited.getId()));
		}
		req.getRequestDispatcher("index2.jsp").forward(req, resp);
	}

}

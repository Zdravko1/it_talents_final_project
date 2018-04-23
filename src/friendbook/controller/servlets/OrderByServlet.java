package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import friendbook.controller.UserManager;
import friendbook.model.post.Post;
import friendbook.model.user.User;

/**
 * Servlet implementation class OrderByServlet
 */
@WebServlet("/order")
public class OrderByServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String orderBy = req.getParameter("order");
		User u = (User)req.getSession().getAttribute("user");
		try {
			List<Post> posts = UserManager.getInstance().getPostsByUserID(u.getId());
			req.setAttribute("posts", orderBy(posts, orderBy));
			if(!reloadFeedPostsOrdered(req, resp, orderBy)) {
				reloadVisitedORUserPostsOrdered(req, resp, orderBy);
			}
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
	}

	
	
	private boolean reloadFeedPostsOrdered(HttpServletRequest req, HttpServletResponse resp, String orderBy) throws SQLException, ServletException, IOException {
		User u = (User) req.getSession().getAttribute("user");
		boolean onFeed = req.getSession().getAttribute("feed") != null;
		if(onFeed) {
			List<Post> posts = (List)UserManager.getInstance().getUserFeed(u.getId());
			req.getSession().setAttribute("feed", orderBy(posts, orderBy));
			req.getRequestDispatcher("index2.jsp").forward(req, resp);
			return true;
		}
		return false;
	}
	
	private void reloadVisitedORUserPostsOrdered(HttpServletRequest req, HttpServletResponse resp, String orderBy) throws SQLException, ServletException, IOException {
		User visited = (User)req.getSession().getAttribute("visitedUser");
		if(visited != null) {
			List<Post> posts = (List)UserManager.getInstance().getPostsByUserID(visited.getId());
			req.getSession().setAttribute("visitedUserPosts", orderBy(posts, orderBy));
		}
		req.getRequestDispatcher("index2.jsp").forward(req, resp);
	}
	
	private List<Post> orderBy(List<Post> posts, String order){
		System.out.println("vliza");
		switch(order) {
		case "likes": Collections.sort(posts, (p1, p2) -> (p2.getLikes()-p1.getLikes())); return posts;
			default: return posts;
		}
	}
}

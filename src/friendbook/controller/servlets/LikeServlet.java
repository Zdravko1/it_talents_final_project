package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import friendbook.controller.PostManager;
import friendbook.controller.Session;
import friendbook.controller.UserManager;
import friendbook.model.user.User;

/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("like"));
		User u = (User)req.getSession().getAttribute("user");
		
		//check if this post was liked by the user before
		//remove like if so or add a like
		//if the page isnt his cash visited user's products in session so it can update the like count
		try {
			if(UserManager.getInstance().isPostLiked(u, id)) {
				PostManager.getInstance().decreasePostLikes(u, id);
				req.setAttribute("posts", UserManager.getInstance().getPostsByUserID(u.getId()));
				reloadVisitedUserProducts(req, resp);
			} else {
				PostManager.getInstance().increasePostLikes(u, id);
				req.setAttribute("posts", UserManager.getInstance().getPostsByUserID(u.getId()));
				reloadVisitedUserProducts(req, resp);
			}
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
	}
	
	private void reloadVisitedUserProducts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		User visited = (User)req.getSession().getAttribute("visitedUser");
		if(visited != null) {
			req.getSession().setAttribute("visitedUserPosts", UserManager.getInstance().getPostsByUserID(visited.getId()));
		}
		req.getRequestDispatcher("index2.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}

}
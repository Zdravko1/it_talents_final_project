package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import friendbook.controller.UserManager;
import friendbook.model.post.Post;
import friendbook.model.user.User;

/**
 * Servlet implementation class FeedServlet
 */
@WebServlet("/feed")
public class FeedServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//remove visited user's object and name from session when "home" button is pressed
		req.getSession().removeAttribute("visitedUser");
		req.getSession().removeAttribute("visitedUserPosts");
		try {
			User u = (User) req.getSession().getAttribute("user");
			ArrayList<Post> feed = UserManager.getInstance().getUserFeed(u.getId());
			req.getSession().setAttribute("feed", feed);
			req.getRequestDispatcher("index2.jsp").forward(req, resp);
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
		
	}
	
}

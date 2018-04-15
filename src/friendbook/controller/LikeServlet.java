package friendbook.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import friendbook.model.user.User;

/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("like"));
		int id = Integer.parseInt(req.getParameter("like"));
		User u = (User)req.getSession().getAttribute("user");
		try {
			if(UserManager.getInstance().isPostLiked(u, id)) {
				PostManager.getInstance().decreasePostLikes(u, id);
				req.getRequestDispatcher("index2.jsp").forward(req, resp);
			} else {
				PostManager.getInstance().increasePostLikes(u, id);
				req.getRequestDispatcher("index2.jsp").forward(req, resp);
			}
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}

}

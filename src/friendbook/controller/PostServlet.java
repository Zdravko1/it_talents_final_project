package friendbook.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.model.post.Post;
import friendbook.model.post.PostDao;
import friendbook.model.user.User;

/**
 * Servlet implementation class AddPostServlet
 */
@WebServlet("/post")
public class AddPostServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Session.validateRequestIp(req, resp);
		
		HttpSession s = req.getSession();
		User user = (User)s.getAttribute("user");
		Post post = new Post(user, (String)req.getParameter("text"));
		try {
			PostManager.getInstance().addPost(post, s);
			System.out.println("Added post to database.");
			req.getRequestDispatcher("index2.jsp").forward(req, resp);
		} catch (SQLException e) {
			System.out.println("SQLBug: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Bug: " + e.getMessage());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}

}

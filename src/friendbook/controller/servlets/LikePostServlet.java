package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
@WebServlet("/likePost")
public class LikePostServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("like"));
		System.out.println(id);
		User u = (User)req.getSession().getAttribute("user");
		//check if this post was liked by the user before
		//remove like if so or add a like
		try {
			synchronized (u) {
				if(UserManager.getInstance().isPostLiked(u, id)) {
					PostManager.getInstance().decreasePostLikes(u, id);
					req.setAttribute("posts", UserManager.getInstance().getPostsByUserID(u.getId()));
					resp.getWriter().print(PostManager.getInstance().getLikes(id));
				} else {
					PostManager.getInstance().increasePostLikes(u, id);
					req.setAttribute("posts", UserManager.getInstance().getPostsByUserID(u.getId()));
					resp.getWriter().print(PostManager.getInstance().getLikes(id));
				}
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

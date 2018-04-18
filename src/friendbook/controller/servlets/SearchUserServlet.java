package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.controller.UserManager;
import friendbook.model.user.User;

/**
 * Servlet implementation class SearchUserServlet
 */
@WebServlet("/search")
public class SearchUserServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("user");
		String name = req.getParameter("user");
		//cash visited user's object and posts in session
		try {
			User visitedUser = UserManager.getInstance().getUser(name);
			//if the searched user is followed already put a flag and change the follow button to followed in jsp
			//or if this is the same user
			if(UserManager.getInstance().isFollower(user, visitedUser.getId()) || user.getId() == visitedUser.getId()) {
				visitedUser.setFollowed(true);
			}
			req.getSession().setAttribute("visitedUser", visitedUser);
			req.getSession().setAttribute("visitedUserPosts", UserManager.getInstance().getPostsByUserID(visitedUser.getId()));
			req.getRequestDispatcher("index2.jsp").forward(req, resp);
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}

}

package friendbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.exceptions.WrongCredentialsException;
import friendbook.model.post.Post;
import friendbook.model.user.User;
import friendbook.model.user.UserDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.validateRequestIp(request, response);
	//	UserManager.getInstance().sessionCheck(request, response);
		
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("pass");
			if(UserManager.getInstance().login(username, password)) {
				HttpSession session = request.getSession();
				User user = UserManager.getInstance().getUserByUsername(username);
				//get user's posts and put them in request
				List<Post> posts = UserManager.getInstance().getPostsByUserID(user.getId());
				session.setAttribute("user", user);
				request.setAttribute("posts", posts);
				request.getRequestDispatcher("index2.jsp").forward(request, response);
			}
			else {
 			    response.getWriter().write("Wrong username and/or password.");
			}
		} catch (WrongCredentialsException e) {
			response.getWriter().write(e.getMessage());
		} catch (Exception e) {
			response.getWriter().write("Some error occured: " + e.getMessage());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(request, response);
	}

}

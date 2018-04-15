package friendbook.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.model.comment.Comment;
import friendbook.model.user.User;



@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session.validateRequestIp(request, response);
		try {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		// TODO get current Post
		//Comment comment = new Comment(user, post, null, request.getParameter("text"));
		//CommentManager.getInstance().createComment(user, comment);
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
	}

}

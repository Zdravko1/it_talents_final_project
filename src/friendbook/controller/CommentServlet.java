package friendbook.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.model.comment.Comment;
import friendbook.model.post.Post;
import friendbook.model.post.PostDao;
import friendbook.model.user.User;



@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session.validateRequestIp(request, response);
		
		HttpSession s = request.getSession();
		User user = (User)s.getAttribute("user");
		int postId = Integer.parseInt(request.getParameter("currentPost"));
		Comment comment = new Comment(user, postId, null, request.getParameter("text"));
		try {
			CommentManager.getInstance().createComment(comment);
			request.getRequestDispatcher("index2.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("SQLBug: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Bug: " + e.getMessage());
		}
	}

}

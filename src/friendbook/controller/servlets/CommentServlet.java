package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import friendbook.controller.CommentManager;
import friendbook.controller.Session;
import friendbook.controller.UserManager;
import friendbook.model.comment.Comment;
import friendbook.model.post.Post;
import friendbook.model.post.PostDao;
import friendbook.model.user.User;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//TODO fix  bugavo e
//		Session.validateRequestIp(request, response);

		HttpSession s = request.getSession();
		User user = (User) s.getAttribute("user");
		long postId = Long.parseLong(request.getParameter("currentPost"));
		Comment comment = new Comment(user.getId(), postId, null, request.getParameter("text"));
		
		try {
			CommentManager.getInstance().createComment(comment);
			if(s.getAttribute("feed") != null || s.getAttribute("visitedUser") != null) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
			request.setAttribute("posts", UserManager.getInstance().getPostsByUserID(user.getId()));
			request.getRequestDispatcher("index2.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println("SQLBug: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Bug: " + e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session.validateRequestIp(request, response);
		// TODO make it so that only the user that created the post can delete it
		try {
			HttpSession s = request.getSession();
			User user = (User) s.getAttribute("user");
			long commentId = Long.parseLong(request.getParameter("commentId"));
			CommentManager.getInstance().deleteComment(commentId);
		} catch (Exception e) {
			System.out.println("Sql : " + e.getMessage());
		}
	}

}

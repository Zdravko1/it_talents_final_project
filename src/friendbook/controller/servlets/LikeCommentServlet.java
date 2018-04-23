package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import friendbook.controller.CommentManager;
import friendbook.controller.UserManager;
import friendbook.model.user.User;

@WebServlet("/likeComment")
public class LikeCommentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long commentId = Long.parseLong(req.getParameter("like"));
		User u = (User)req.getSession().getAttribute("user");
		System.out.println(commentId);
		//check if this post was liked by the user before
		//remove like if so or add a like
		try {
			if(CommentManager.getInstance().isCommentLiked(u.getId(), commentId)) {
				synchronized (u) {
					CommentManager.getInstance().decreaseCommentLikes(u.getId(), commentId);
					int likes = CommentManager.getInstance().getLikes(commentId);
					System.out.println(likes);
					resp.getWriter().print(likes);
				}
			} else {
				synchronized (u) {
					CommentManager.getInstance().increaseCommentLikes(u.getId(), commentId);
					resp.getWriter().print(CommentManager.getInstance().getLikes(commentId));
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Bug: " + e.getMessage());
		}
	}
}

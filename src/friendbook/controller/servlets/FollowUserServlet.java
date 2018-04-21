package friendbook.controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.storeconfig.IStoreFactory;

import com.google.gson.Gson;

import friendbook.controller.CommentManager;
import friendbook.controller.Session;
import friendbook.controller.UserManager;
import friendbook.model.user.User;


@WebServlet("/follow")
public class FollowUserServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Session.validateRequestIp(req, resp);
		//TODO check if working
		HttpSession s = req.getSession();
		User user = (User) s.getAttribute("user");
		long followedId = Long.parseLong(req.getParameter("followedId"));
		try {
			UserManager.getInstance().follow(user, followedId);
			System.out.println("followed");
			String json = new Gson().toJson(UserManager.getInstance().isFollower(user, followedId) ? "Followed" : "Follow");
			resp.getWriter().print(json);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Bug: " + e.getMessage());
		}
	}
	
}

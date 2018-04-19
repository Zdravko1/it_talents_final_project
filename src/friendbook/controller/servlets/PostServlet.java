package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import friendbook.controller.PostManager;
import friendbook.controller.Session;
import friendbook.controller.UserManager;
import friendbook.model.post.Post;
import friendbook.model.user.User;

/**
 * Servlet implementation class AddPostServlet
 */
@WebServlet("/post")
@MultipartConfig(fileSizeThreshold=1024*1024*2,
maxFileSize=1024*1024*5)
public class PostServlet extends HttpServlet {
//	private static final String SAVE_PATH="C:\\Users\\snape\\Desktop\\JavaFinalProject\\Friendbook.bg\\WebContent";
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//test upload
//		response.setContentType("text/html");
//        Part file = request.getPart("file");
//        String fileName=extractfilename(file);
// 	    file.write(SAVE_PATH + File.separator + fileName);
// 	    String filePath= fileName ;
 	    //test upload
 	    
//		Session.validateRequestIp(req, resp);
		User user = (User)request.getSession().getAttribute("user");
		Post post = new Post(user, (String)request.getParameter("text"));
		
		try {
			PostManager.getInstance().addPost(post, request);
			System.out.println("Added post to database.");
			
			String json = new Gson().toJson(UserManager.getInstance().getLastPostByUserId(user.getId()));
			response.getWriter().print(json);
			
//			UserManager.getInstance().sessionCheck(request, response);
		} catch (SQLException e) {
			System.out.println("SQLBug: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Bug: " + e.getMessage());
		}
	}
	
	 private String extractfilename(Part file) {
	        String cd = file.getHeader("content-disposition");
	        String[] items = cd.split(";");
	        for (String string : items) {
	            if (string.trim().startsWith("filename")) {
	                return string.substring(string.indexOf("=") + 2, string.length()-1);
	            }
	        }
	        return "";
	    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserManager.getInstance().sessionCheck(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Session.validateRequestIp(req, resp);
		//TODO make it so that only the user that created the post can delete it
		try {
		HttpSession s = req.getSession();
		User user = (User)s.getAttribute("user");
		long postId = Long.parseLong(req.getParameter("postId"));
		PostManager.getInstance().deletePost(postId);
		} catch (Exception e) {
			System.out.println("Sql : " + e.getMessage());
		}
	}

}

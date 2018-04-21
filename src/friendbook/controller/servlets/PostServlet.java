package friendbook.controller.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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


@WebServlet("/post")
@MultipartConfig
public class PostServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User)request.getSession().getAttribute("user");
		Post post = null;
		try{
			Part filePart = request.getPart("file");
			String name = extractfilename(filePart);
			if(!name.equals("")) {
				File file = new File("D:\\photos\\" + user.getUsername());
				if(!file.exists()) {
					file.mkdirs();
				}
				File f = new File("D:\\photos\\"+user.getUsername()+"\\"+name);
	
				InputStream is = filePart.getInputStream();
				OutputStream os = new FileOutputStream(f);
				int b = is.read();
				while(b != -1) {
					os.write(b);
					b = is.read();
				}
				post = new Post(user, (String)request.getParameter("text"), f.getAbsolutePath());
			}
		} catch(Exception e) {
			System.out.println("Bug: " + e.getMessage());
		}
		post = new Post(user, (String)request.getParameter("text"), null);

		try {
			PostManager.getInstance().addPost(post, request);
			System.out.println("Added post to database.");
			
			String json = new Gson().toJson(UserManager.getInstance().getLastPostByUserId(user.getId()));
			response.getWriter().print(json);
			
//			UserManager.getInstance().sessionCheck(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
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

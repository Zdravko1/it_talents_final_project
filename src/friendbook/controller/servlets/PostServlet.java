package friendbook.controller.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

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
		//test
		try {
			String image = request.getParameter("file");
			System.out.println(image);
			if(image != null) {
				image = image.split(",")[1].replaceAll(" ", "+");
				System.out.println(image);
				String imageName = "image"+image.substring(0, 10)+".jpg";
				File file = new File("D:\\photos\\" + user.getUsername());
				if(!file.exists()) {
					file.mkdirs();
				}
				file = new File("D:\\photos\\"+user.getUsername()+"\\"+imageName);
				file.createNewFile();
				
				decoder(image, file);
				
				post = new Post(user, (String)request.getParameter("text"), file.getAbsolutePath());
			} else {
				post = new Post(user, (String)request.getParameter("text"), null);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			PostManager.getInstance().addPost(post, request);
			System.out.println("Added post to database.");
			
			String json = new Gson().toJson(UserManager.getInstance().getLastPostByUserId(user.getId()));
			System.out.println(json);
			response.getWriter().print(json);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Bug2: " );
			e.printStackTrace();
		}
	}
	
	public static void decoder(String base64Image, File f) {
		try (FileOutputStream imageOutFile = new FileOutputStream(f)) {
			byte[] btDataFile = new sun.misc.BASE64Decoder().decodeBuffer(base64Image);
			imageOutFile.write(btDataFile);
			imageOutFile.flush();
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
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

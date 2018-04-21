package friendbook.controller.servlets;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import friendbook.model.post.PostDao;

/**
 * Servlet implementation class PictureServlet
 */
@WebServlet("/getPic")
public class PictureServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long postId = Long.parseLong(req.getParameter("postId"));
		try {
			String imagePath = PostDao.getInstance().getPostImageById(postId);
			if(imagePath != null) {
				File f = new File(imagePath);
				InputStream is = new FileInputStream(f);
				OutputStream os = resp.getOutputStream();
				int b = is.read();
				while(b != -1) {
					os.write(b);
					b = is.read();
				}
			}
			
		} catch (SQLException e) {
			resp.sendRedirect("index2.jsp");
		}
		
	}

}

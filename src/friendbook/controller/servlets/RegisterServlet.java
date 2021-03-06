package friendbook.controller.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import friendbook.controller.Session;
import friendbook.controller.UserManager;
import friendbook.exceptions.ExistingUserException;
import friendbook.exceptions.ExistingUserNameException;
import friendbook.exceptions.IncorrectUserNameException;
import friendbook.exceptions.InvalidEmailException;
import friendbook.exceptions.InvalidPasswordException;
import friendbook.model.user.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//bugvo e
//		Session.validateRequestIp(request, response);
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("confirm password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		if(password.compareTo(password2) !=0 ) {
			response.getWriter().write("Passwords don't match! ");
		}
		
		try {
			User u = new User();
			u.setUsername(username);
			u.setPassword(password);
			u.setEmail(email);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			UserManager.getInstance().register(u);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} catch (InvalidPasswordException | IncorrectUserNameException | ExistingUserNameException
				| InvalidEmailException | ExistingUserException e) {
			request.setAttribute("error", e.getMessage());
			response.getWriter().write(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Bug: " + e.getMessage());
		}
	}

}

package friendbook.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Session {

	public static void validateRequestIp(HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.isNew()) {
			session.setAttribute("ip", req.getRemoteAddr());
		} else {
			if (session.getAttribute("ip") != req.getRemoteAddr()) {
				session.invalidate();
				req.getRequestDispatcher("index2.jsp").forward(req, resp);
			}
		}
	}
}

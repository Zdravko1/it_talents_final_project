package friendbook.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class TestSearchServlet
 */
@WebServlet("/searchServlet")
public class AutoCompleteSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

            response.setContentType("application/json");
            try {
                    String term = request.getParameter("term");
                    System.out.println("Data from ajax call " + term);

                    ArrayList<String> list = (ArrayList<String>) UserManager.getInstance().getUsersNamesStartingWith(term);

                    String searchList = new Gson().toJson(list);
                    response.getWriter().write(searchList);
            } catch (Exception e) {
                    System.err.println(e.getMessage());
            }
    }
	
}

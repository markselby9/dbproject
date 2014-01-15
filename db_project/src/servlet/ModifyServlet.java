package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.UserOperator;

/**
 * Servlet implementation class ModifyServlet
 */
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String office = request.getParameter("office");
		String job = request.getParameter("job");
		try {
			UserOperator.modifyPeople(Integer.parseInt(id),
					Integer.parseInt(office), job);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("admin.jsp");
	}

}

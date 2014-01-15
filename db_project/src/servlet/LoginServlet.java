package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.Licence;
import bll.LicenceOperator;
import bll.Student;
import bll.Worker;
import bll.UserOperator;

/**
 * Servlet implementation class loginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String key = request.getParameter("logUsername");
		String password = request.getParameter("logPassword");
		String basepass = null;
		String type = null;
		try {
			basepass = UserOperator.getPassword(key);
			if (basepass == null) {
				// ���û���û�
				request.getSession().setAttribute("info", "usernameWrong");
				response.sendRedirect("login.jsp");
			} else if (password.equals(basepass)) {
				// ��Ϣ��ȷ,�ж��û���admin����worker����student
				type = UserOperator.getUserType(key);
				if (type.equals("admin")) {
					Worker admin = new Worker();
					request.getSession().setAttribute("user", admin);
					response.sendRedirect("admin.jsp");
				} else if (type.equals("worker")) {
					Worker worker = UserOperator.getWorker(key);
					request.getSession().setAttribute("user", worker);
					response.sendRedirect("worker.jsp");
				} else if (type.equals("student")) {
					Licence licence = LicenceOperator.haveLicence(key);
					// ����м���
					if (licence != null) {
						request.getSession().setAttribute("user", licence);
						response.sendRedirect("showLicence.jsp");
					} else // ���û�м���
					{
						Student student = UserOperator.getUser(key);
						request.getSession().setAttribute("user", student);
						response.sendRedirect("student.jsp");
					}
				}
			} else {
				// �������
				request.getSession().setAttribute("info", "passwordWrong");
				response.sendRedirect("login.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

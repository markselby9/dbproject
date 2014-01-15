package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.UserOperator;

/**
 * Servlet implementation class changeKeyServlet
 */
public class ChangeKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeKeyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String oldPassword = request.getParameter("old");
		String newPassword = request.getParameter("new");
		try {
			//先判断旧密码输的对不对
			if(UserOperator.checkPassword(username, oldPassword)){
				//如果正确则修改密码
				UserOperator.changePassword(username, newPassword);
				response.sendRedirect("changeKey.jsp?state=right&username=" + username);
			}else{
				response.sendRedirect("changeKey.jsp?state=wrong&username=" + username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}

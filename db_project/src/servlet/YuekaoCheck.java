/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bll.*;
import java.sql.*;

/**
 * 
 * @author fengchaoyi
 */
public class YuekaoCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet yuekaocheck</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet yuekaocheck at "
					+ request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// processRequest(request, response);
		DBBean db = new DBBean();
		try {
			db.Connect();
			PreparedStatement ps;
			ps = db.executePreparedStatement("select * from test_info order by test_Time asc;");
			ResultSet rs = ps.getResultSet();
			if (rs != null) {
				rs.next();
				String a = rs.getString("stu_test_info_ID");
				ps = db.executePreparedStatement("select * from stu_test_info where stu_test_info_ID = "
						+ a + ";");
			}
			rs = ps.getResultSet();
			if (rs != null) {
				rs.next();
				String id = rs.getString("stu_ID");
				ps = db.executePreparedStatement("select * from stu_info where stu_ID = "
						+ id + ";");
			}
			rs = ps.getResultSet();
			if (rs != null) {
				rs.next();
				String name = rs.getString("stu_Name");
			}
			ps = db.executePreparedStatement("DELETE FROM `stu_test_info` WHERE `stu_test_info_ID`='15';");
			ps.executeUpdate();
			String time = "2014-1-7";
			System.out.println("=========2014-1-7=====================");
			String sql = "INSERT INTO `master_db` (`Action_ID`,`Operator_ID`,`Operator_time`,`Operation_content`)VALUES(1,7,'"
					+ time + "','删除多余约考考生');";
			ps = db.executePreparedStatement(sql);
			ps.executeUpdate();
			System.out.println(sql);
			response.sendRedirect("worker.jsp?nameset=y");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("worker.jsp");
		}

	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}

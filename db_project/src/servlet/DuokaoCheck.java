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
import servlet.*;
import java.sql.*;

/**
 * 
 * @author fengchaoyi
 */
public class DuokaoCheck extends HttpServlet {
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
			out.println("<title>Servlet duokaocheck</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet duokaocheck at "
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
			PreparedStatement ps = db
					.executePreparedStatement("select * from subject_grade where stu_ID in (select stu_ID from subject_grade group by stu_ID, subject_ID having count(stu_ID) > 2)   ");
			ResultSet rs = ps.getResultSet();
			if (rs != null) {
				rs.next();
				String id = rs.getString("stu_ID");
				// response.sendRedirect("worker.jsp?idset=" + "id");
			}
			String time = "2014-1-7";
			System.out.println("=========2014-1-7=====================");
			String sql = "INSERT INTO `master_db` (`Action_ID`,`Operator_ID`,`Operator_time`,`Operation_content`)VALUES(2,7,'"
					+ time + "','有考生同场次成绩超过两个');";
			ps = db.executePreparedStatement(sql);
			ps.executeUpdate();
			response.sendRedirect("worker.jsp?idset=" + "id");
			db.close();
			// response.sendRedirect("worker.jsp?idset=" + id);}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("worker.jsp");
		}
	}

}

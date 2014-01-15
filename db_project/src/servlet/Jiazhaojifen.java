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
import java.util.*;

/**
 * 
 * @author fengchaoyi
 */
public class Jiazhaojifen extends HttpServlet {
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
			out.println("<title>Servlet Jiazhaojifen</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet Jiazhaojifen at "
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
		ArrayList<Fenshu> res = new ArrayList<Fenshu>();
		Fenshu fs = new Fenshu();
		DBBean db = new DBBean();
		try {
			db.Connect();
			ResultSet rs = db
					.executeQuery("select * from licence_violation_info order by licence_ID asc;");
			System.out.println(" ResultSet rs");
			while (rs.next()) {
				fs = new Fenshu();
				System.out.println("1");
				String scoreommitted = rs.getString("score_omitted");
				System.out.println("2");
				if (rs.wasNull()) {
					System.out.println("was null");
					scoreommitted = "0";
					fs.setKoufen("0");
				} else {
					System.out.println("not was null");
					fs.setKoufen(rs.getString("score_omitted"));
				}
				fs.setLicenceID(rs.getString("licence_ID"));
				System.out
						.println("SELECT * FROM licence_info where licence_ID = '"
								+ rs.getString("licence_ID") + "';");
				ResultSet t = db
						.executeQuery("SELECT * FROM licence_info where licence_ID = '"
								+ rs.getString("licence_ID") + "';");
				System.out.println("  ResultSet t = d");
				t.next();
				fs.setOfficeID(t.getString("office_ID"));
				fs.setReason(rs.getString("violation_reason"));
				fs.setTime(rs.getString("violation_time"));
				fs.setShengyufen(Integer.parseInt(t.getString("score"))
						- Integer.parseInt(scoreommitted) + "");
				res.add(fs);
			}
			db.close();
			System.out.println(" db.close();");
			request.getSession().setAttribute("res", res);
			response.sendRedirect("worker.jsp?res=res");
		} catch (Exception e) {
			e.printStackTrace();
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

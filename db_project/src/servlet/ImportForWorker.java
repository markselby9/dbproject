/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import bll.DBBean;
import bll.*;

/**
 * 
 * @author fengchaoyi
 */
public class ImportForWorker extends HttpServlet {
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
			out.println("<title>Servlet importforWorker</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet importforWorker at "
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
		request.setCharacterEncoding("GBK");
		String fileType = "xls, XLS";
		String filePath = "";

		String path = super.getServletContext().getRealPath("/excel");
		System.out.println("path: " + path);
		SmartUpload upload = new SmartUpload();

		upload.initialize(getServletConfig(), request, response);
		upload.setAllowedFilesList(fileType);
		upload.getRequest();
		try {
			upload.upload();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("response.sendRedirect(\"worker.jsp\");");
			response.sendRedirect("worker.jsp");
			return;
		}

		upload.getRequest();

		System.out.println("upload finish");
		Files files = upload.getFiles();
		com.jspsmart.upload.File file = files.getFile(0);
		String ext = file.getFileExt();
		if (ext != "") {
			//处理申请导入
			if ("shenqing".equals(file.getFieldName())) {
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					// if (!file.exists())
					// file.createNewFile();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);
					// 获得第一个工作表对象
					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement ps;
					for (int i = 1; i < sheet.getRows(); i++) {
						if (sheet.getCell(7, i).getContents().length() != 0) {
							String shentixinxi = sheet.getCell(7, i)
									.getContents();
							System.out.println("shentixinxi" + shentixinxi);
							String jujueyuanyin = "";
							String t = "";
							if (shentixinxi.equals("0")) {
								jujueyuanyin = "通过";
								t = "Y";
							} else if (shentixinxi.equals("1")) {
								jujueyuanyin = "辨色问题";
								t = "N";
							} else if (shentixinxi.equals("2")) {
								jujueyuanyin = "运动神经";
								t = "N";
							} else if (shentixinxi.equals("3")) {
								jujueyuanyin = "肢体残疾";
								t = "N";
							}
							String sql = "INSERT INTO stu_info(`stu_ID`,`ID_Type`,`ID`,`stu_Name`,`stu_Gender`,"
									+ "`birthday`,`nationality`,`home_Address`,`pass`,`reject_Reason`,`apply_Time`,`worker_ID`)"
									+ "VALUES('"
									+ i
									+ "','"
									+ sheet.getCell(0, i).getContents()
									+ "','"
									+ sheet.getCell(1, i).getContents()
									+ "','"
									+ sheet.getCell(2, i).getContents()
									+ "','"
									+ sheet.getCell(3, i).getContents()
									+ "','"
									+ sheet.getCell(4, i).getContents()
									+ "','"
									+ sheet.getCell(5, i).getContents()
									+ "','"
									+ sheet.getCell(6, i).getContents()
									+ "','"
									+ t
									+ "','"
									+ jujueyuanyin
									+ "','"
									+ sheet.getCell(8, i).getContents()
									+ "','"
									+ sheet.getCell(9, i).getContents() + "');";
							System.out.println(sql);
							ps = db.executePreparedStatement(sql);
							ps.executeUpdate(sql);
							UserOperator.addUsername(sheet.getCell(1, i).getContents());
						}
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
                                        
                                        Account acc = new Account(), acc2 = new Account();
                                        ResultSet rs = db.executeQuery("select * from login_info where user_type = 'student';");
                                        rs.next();
                                        acc.setUsername(rs.getString("username"));
                                        acc.setPassword(rs.getString("password"));
                                        rs.next();
                                        acc2.setUsername(rs.getString("username"));
                                        acc2.setPassword(rs.getString("password"));
                                        acc.setNext(acc2);
                                        request.getSession().setAttribute("acc", acc);
                                        
					db.close();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else if ("kemushijian".equals(file.getFieldName())) {
				System.out.println("kemushijian daoru");
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					Workbook book = Workbook.getWorkbook(f);
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement s;
					for (int i = 1; i < sheet.getRows(); i++) {
						String sql = "INSERT INTO test_info(`test_ID`,`subject`,`test_Time`,`place`,`office_ID`)VALUES('"
								+ sheet.getCell(4, i).getContents()
								+ "','"
								+ sheet.getCell(0, i).getContents()
								+ "','"
								+ sheet.getCell(1, i).getContents()
								+ "','"
								+ sheet.getCell(2, i).getContents()
								+ "','"
								+ sheet.getCell(3, i).getContents() + "');";
						System.out.println(sql);
						s = db.executePreparedStatement(sql);
						s.executeUpdate();
						// l.setLicence_ID(sheet.getCell(i, i));
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
					db.close();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else if ("yuekao".equals(file.getFieldName())) {
				System.out.println("约考，导入部分信息，没有成绩");
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					Workbook book = Workbook.getWorkbook(f);
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement ps;
					for (int i = 1; i < sheet.getRows(); i++) {
						// //////////////////////////worker_name还没写
						String pass = "";
						String reject = "";
						String subject = sheet.getCell(1, i).getContents();
						String time = sheet.getCell(0, i).getContents();

						ResultSet rs = db
								.executeQuery("select * from test_info where subject='"
										+ subject
										+ "' and test_time='"
										+ time
										+ "';");
						if (rs.next()) {
							pass = "Y";
							reject = "通过";
						} else {
							pass = "N";
							reject = "没有场地";
						}

						String reason = "";
						String sql = "INSERT INTO stu_test_info(`stu_test_info_ID`,`test_Time`,`subject`,`ID_Type`,`ID`,`office_ID`,`apply_Pass`,`reject_Reason`)VALUES('"
								+ i
								+ "','"
								+ sheet.getCell(0, i).getContents()
								+ "','"
								+ sheet.getCell(1, i).getContents()
								+ "','"
								+ sheet.getCell(2, i).getContents()
								+ "','"
								+ sheet.getCell(3, i).getContents()
								+ "','"
								+ sheet.getCell(4, i).getContents()
								+ "','" + pass + "','" + reject + "');";
						System.out.println(sql);
						ps = db.executePreparedStatement(sql);
						ps.executeUpdate();
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
					db.close();
					// response.sendRedirect("worker.jsp?daoru=yuekao");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			System.out.println("finish");
			response.sendRedirect("worker.jsp?daoru=success");
		}else {
			// 说明没有文件
			response.sendRedirect("worker.jsp?upload=failed");
		}
	}

}

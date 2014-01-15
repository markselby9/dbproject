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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import bll.DBBean;
import java.sql.ResultSet;

/**
 * 
 * @author fengchaoyi
 */
public class ImportGrade extends HttpServlet {
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
			out.println("<title>Servlet importgrade</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet importgrade at "
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
		// upload.setAllowedFilesList("xls, XLS");
		upload.getRequest();
		try {
			// System.out.println(upload.getFiles().getFile(0).getFileName());
			upload.upload();
			System.out.println(upload.getFiles().getFile(0).getFileExt());
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
		if (files != null) {
			com.jspsmart.upload.File file = files.getFile(0);
			if (file.isMissing())
				response.sendRedirect("worker.jsp");
			if ("lilunyi".equals(file.getFieldName())) {
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);

					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement s;
					for (int i = 1; i < sheet.getRows(); i++) {

						if (sheet.getCell(1, i).getContents().length() != 0) {
							/*
                        */
							String ID = sheet.getCell(1, i).getContents();
							String grade = "";

							System.out
									.println("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='理论一';");
							ResultSet rs = db
									.executeQuery("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='理论一';");
							String valid;
							rs.next();
							int count = rs.getInt("rowcount");
							System.out.println("rs.getrow = " + count);
							if ((count != 0) && (count <= 2)) {
								valid = "Y";
							} else {
								valid = "N";
							}
							String sql = "INSERT INTO subject_grade(`subject_ID`,`stu_ID`,`worker_ID`,`licence_type`,`ID`,`worker_Num`,`test_Num`,`grade`,`wrong_Num`,`subject_Time`,`valid`)VALUES("
									+ "1,"
									+ i
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(0, i).getContents()
									+ ","
									+ sheet.getCell(1, i).getContents()
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(3, i).getContents()
									+ ","
									+ sheet.getCell(4, i).getContents()
									+ ",'"
									+ sheet.getCell(5, i).getContents()
									+ "','none','" + valid + "');";

							System.out.println(sql);
							s = db.executePreparedStatement(sql);
							s.executeUpdate();
						}
						// l.setLicence_ID(sheet.getCell(i, i));
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
					db.close();
				} catch (Exception e) {
					System.out.println(e.toString());
					response.sendRedirect("worker.jsp");
				}
			} else if ("liluner".equals(file.getFieldName())) {
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);

					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement s;
					for (int i = 1; i < sheet.getRows(); i++) {
						if (sheet.getCell(1, i).getContents().length() != 0) {
							String ID = sheet.getCell(1, i).getContents();

							System.out
									.println("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='理论二';");
							ResultSet rs = db
									.executeQuery("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='理论二';");
							String valid;
							rs.next();
							int count = rs.getInt("rowcount");
							System.out.println("rs.getrow = " + count);
							if ((count != 0) && (count <= 2)) {
								valid = "Y";
							} else {
								valid = "N";
							}
							String sql = "INSERT INTO subject_grade(`subject_ID`,`stu_ID`,`worker_ID`,`licence_type`,`ID`,`worker_Num`,`test_Num`,`grade`,`wrong_Num`,`subject_Time`,`valid`)VALUES("
									+ "2,"
									+ i
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(0, i).getContents()
									+ ","
									+ sheet.getCell(1, i).getContents()
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(3, i).getContents()
									+ ","
									+ sheet.getCell(4, i).getContents()
									+ ",'"
									+ sheet.getCell(5, i).getContents()
									+ "','none','" + valid + "');";

							System.out.println(sql);
							s = db.executePreparedStatement(sql);
							s.executeUpdate();
						}
					}
					// s = db.executePreparedStatement(l.getSentence());
					// l.setLicence_ID(sheet.getCell(i, i));

					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
					db.close();
				} catch (Exception e) {
					System.out.println(e.toString());
					// response.sendRedirect("worker.jsp");
				}
			} else if ("kemuer".equals(file.getFieldName())) {
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);

					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement s;
					for (int i = 1; i < sheet.getRows(); i++) {

						if (sheet.getCell(1, i).getContents().length() != 0) {
							String ID = sheet.getCell(1, i).getContents();

							System.out
									.println("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='科目二';");
							ResultSet rs = db
									.executeQuery("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='科目二';");
							String valid;
							rs.next();
							int count = rs.getInt("rowcount");
							System.out.println("rs.getrow = " + count);
							if ((count != 0) && (count <= 2)) {
								valid = "Y";
							} else {
								valid = "N";
							}

							String koufenyuanyin = sheet.getCell(4, i)
									.getContents();
							String wrong = "";
							int score = 0;
							if (koufenyuanyin.contains("0")) {
								score = 0;
							} else {
								if (koufenyuanyin.contains("7")) {
									score += 10;
								}
								if (koufenyuanyin.contains("8")) {
									score += 10;
								}
								if (koufenyuanyin.contains("12")) {
									score += 10;
								}
								if (koufenyuanyin.contains("15")) {
									score += 10;
								} else {
									score += 20;
								}
							}
							if (score >= 20) {
								wrong = "不及格";
							} else {
								wrong = "及格";
							}
							/*
							 * if (sheet.getCell(1, i).getContents().length() !=
							 * 0){ String sql =
							 * "INSERT INTO subject_grade(`subject_ID`,`stu_ID`,`worker_ID`,`licence_type`,`ID`,`worker_Num`,`test_Num`,`grade`,`wrong_Num`,`subject_Time`)VALUES("
							 * + "1," + i + "," + sheet.getCell(2,
							 * i).getContents() + "," + sheet.getCell(0,
							 * i).getContents() + "," + sheet.getCell(1,
							 * i).getContents() + "," +
							 * sheet.getCell(2,i).getContents() + "," +
							 * sheet.getCell(3, i).getContents() + "," +
							 * sheet.getCell(4, i).getContents() + ",'" +
							 * sheet.getCell(5, i).getContents() + "','none');";
							 * 
							 * System.out.println(sql); s =
							 * db.executePreparedStatement(sql);
							 * s.executeUpdate();}
							 */
							String sql = "INSERT INTO subject_grade(`subject_ID`,`stu_ID`,`worker_ID`,`licence_type`,`ID`,`worker_Num`,`test_Num`,`grade`,`wrong_Num`,`subject_Time`,`valid`)VALUES("
									+ "3,"
									+ i
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(0, i).getContents()
									+ ","
									+ sheet.getCell(1, i).getContents()
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(3, i).getContents()
									+ ",'"
									+ wrong
									+ "','"
									+ sheet.getCell(4, i).getContents()
									+ "','"
									+ sheet.getCell(5, i).getContents()
									+ "','"
									+ valid + "');";
							System.out.println(sql);
							s = db.executePreparedStatement(sql);
							s.executeUpdate();
						}
						// s = db.executePreparedStatement(l.getSentence());
						// l.setLicence_ID(sheet.getCell(i, i));
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
					db.close();
				} catch (Exception e) {
					System.out.println(e.toString());
					response.sendRedirect("worker.jsp");
				}
			} else if ("kemusan".equals(file.getFieldName())) {
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);

					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement s;
					for (int i = 1; i < sheet.getRows(); i++) {

						if (sheet.getCell(1, i).getContents().length() != 0) {
							String ID = sheet.getCell(1, i).getContents();

							System.out
									.println("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='科目三';");
							ResultSet rs = db
									.executeQuery("select count(*) as rowcount from stu_test_info where ID='"
											+ ID
											+ "' and apply_Pass='Y' and subject='科目三';");
							String valid;
							rs.next();
							int count = rs.getInt("rowcount");
							System.out.println("rs.getrow = " + count);
							if ((count != 0) && (count <= 2)) {
								valid = "Y";
							} else {
								valid = "N";
							}
							String koufenyuanyin = sheet.getCell(4, i)
									.getContents();
							String wrong = "";
							int score = 0;
							if (koufenyuanyin.contains("0")) {
								score = 0;
							} else {
								if (koufenyuanyin.contains("3")) {
									score += 10;
								}
								if (koufenyuanyin.contains("8")) {
									score += 10;
								} else {
									score += 20;
								}
							}
							if (score >= 20) {
								wrong = "不及格";
							} else {
								wrong = "及格";
							}
							String sql = "INSERT INTO subject_grade(`subject_ID`,`stu_ID`,`worker_ID`,`licence_type`,`ID`,`worker_Num`,`test_Num`,`grade`,`wrong_Num`,`subject_Time`,`valid`)VALUES("
									+ "4,"
									+ i
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(0, i).getContents()
									+ ","
									+ sheet.getCell(1, i).getContents()
									+ ","
									+ sheet.getCell(2, i).getContents()
									+ ","
									+ sheet.getCell(3, i).getContents()
									+ ",'"
									+ wrong
									+ "','"
									+ sheet.getCell(4, i).getContents()
									+ "','"
									+ sheet.getCell(5, i).getContents()
									+ "','"
									+ valid + "');";
							System.out.println(sql);
							s = db.executePreparedStatement(sql);
							s.executeUpdate();
						}
						// s = db.executePreparedStatement(l.getSentence());
						// l.setLicence_ID(sheet.getCell(i, i));
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
					db.close();
				} catch (Exception e) {
					System.out.println(e.toString());
					response.sendRedirect("worker.jsp");
				}
			}
		}

		response.sendRedirect("worker.jsp?daoru=chengji");
	}

}

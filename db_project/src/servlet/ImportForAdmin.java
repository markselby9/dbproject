/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jspsmart.upload.*;
import bll.*;
import jxl.*;
import java.io.File;
import java.sql.*;

/**
 * 
 * @author fengchaoyi
 */
public class ImportForAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportForAdmin() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		String fileType = "xls, XLS";

		String path = super.getServletContext().getRealPath("/excel");
		SmartUpload upload = new SmartUpload();

		upload.initialize(getServletConfig(), request, response);
		upload.setAllowedFilesList(fileType);
		System.out.println("path: " + path);
		try {
			upload.upload();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("admin.jsp");
			return;
		}

		upload.getRequest();

		System.out.println("upload finish");
		Files files = upload.getFiles();
		com.jspsmart.upload.File file = files.getFile(0);
		String ext = file.getFileExt();
		if (ext != "") {
			// 处理驾照导入
			if ("jiazhao".equals(file.getFieldName())) {
				System.out.println("jiazhao daoru");
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);
					// 获得第一个工作表对象
					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement ps1 = null;
					PreparedStatement ps2 = null;
					for (int i = 1; i < sheet.getRows(); i++) {
						/*
                         * 
                         */
						String sql = "INSERT INTO licence_info(`licence_ID`,`ID`,`ID_Type`,"
								+ "`stu_Gender`,`score`,`licence_State`,`stu_Name`,`nationality`,`birthday`,"
								+ "`home_Address`,`get_Licence_Time`,`allowed_Car_Type`,`valid_Time`,`office_ID`,"
								+ "`photo_Address`)values("
								+ sheet.getCell(11, i).getContents()
								+ ","
								+ sheet.getCell(6, i).getContents()
								+ ",'"
								+ sheet.getCell(5, i).getContents()
								+ "','"
								+ sheet.getCell(1, i).getContents()
								+ "','"
								+ sheet.getCell(12, i).getContents()
								+ "','"
								+ "valid"
								+ "','"
								+ sheet.getCell(0, i).getContents()
								+ "','"
								+ sheet.getCell(3, i).getContents()
								+ "','"
								+ sheet.getCell(2, i).getContents()
								+ "','"
								+ sheet.getCell(4, i).getContents()
								+ "','"
								+ sheet.getCell(7, i).getContents()
								+ "','"
								+ sheet.getCell(8, i).getContents()
								+ "','"
								+ sheet.getCell(9, i).getContents()
								+ "','"
								+ sheet.getCell(10, i).getContents()
								+ "','"
								+ "images/head/5.jpg" + "');";
						String sqlForLogin = "INSERT INTO login_info(username, password, user_type) VALUES ('"
								+ sheet.getCell(6, i).getContents()
								+ "','"
								+ sheet.getCell(6, i).getContents()
								+ "','"
								+ "student" + "');";
						System.out.println(sql);
						System.out.println(sqlForLogin);
						ps1 = db.executePreparedStatement(sql);
						ps1.executeUpdate(sql);
						ps2 = db.executePreparedStatement(sqlForLogin);
						ps2.executeUpdate(sqlForLogin);
						// l.setLicence_ID(sheet.getCell(i, i));
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				response.sendRedirect("admin.jsp?daoru=success1");
			}
			// 处理违章导入
			else if ("weizhang".equals(file.getFieldName())) {
				System.out.println("weizhang daoru");
				try {
					file.saveAs(path);
					File f = new File(path);
					DBBean db = new DBBean();
					db.Connect();
					System.out
							.println("Workbook book = Workbook.getWorkbook(f);");
					Workbook book = Workbook.getWorkbook(f);
					// 获得第一个工作表对象
					System.out.println("Sheet sheet = book.getSheet(0);");
					Sheet sheet = book.getSheet(0);
					System.out.println("rows = " + sheet.getRows());
					System.out.println("columns = " + sheet.getColumns());
					PreparedStatement ps = null;
					String insertDataSQL = "";
					for (int i = 1; i < sheet.getRows(); i++) {
						// 防止有空的情况

						if (sheet.getCell(4, i).getContents().equals("")) {
							// 如果最后两项都为空
							if (sheet.getCell(5, i).getContents().equals("")) {
								insertDataSQL = "INSERT INTO licence_violation_info(`licence_ID`,`violation_time`,`violation_reason`,`violation_type`)VALUES('"
										+ sheet.getCell(0, i).getContents()
										+ "','"
										+ sheet.getCell(1, i).getContents()
										+ "','"
										+ sheet.getCell(2, i).getContents()
										+ "','"
										+ sheet.getCell(3, i).getContents()
										+ "');";
							}
							// 如果forbidden_year为空
							else {
								insertDataSQL = "INSERT INTO licence_violation_info(`licence_ID`,`violation_time`,`violation_reason`,`violation_type`,`score_omitted`)VALUES('"
										+ sheet.getCell(0, i).getContents()
										+ "','"
										+ sheet.getCell(1, i).getContents()
										+ "','"
										+ sheet.getCell(2, i).getContents()
										+ "','"
										+ sheet.getCell(3, i).getContents()
										+ "','"
										+ sheet.getCell(5, i).getContents()
										+ "');";
							}
						} else
						// 如果score_omitted为空
						if (sheet.getCell(5, i).getContents().equals("")) {
							insertDataSQL = "INSERT INTO licence_violation_info(`licence_ID`,`violation_time`,`violation_reason`,`violation_type`,`forbidden_year`)VALUES('"
									+ sheet.getCell(0, i).getContents()
									+ "','"
									+ sheet.getCell(1, i).getContents()
									+ "','"
									+ sheet.getCell(2, i).getContents()
									+ "','"
									+ sheet.getCell(3, i).getContents()
									+ "','"
									+ sheet.getCell(4, i).getContents() + "');";
						}
						// 信息完整
						else {
							insertDataSQL = "INSERT INTO licence_violation_info(`licence_ID`,`violation_time`,`violation_reason`,`violation_type`,`forbidden_year`,`score_omitted`)VALUES('"
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
									+ sheet.getCell(5, i).getContents() + "');";
						}
						System.out.println(insertDataSQL);
						ps = db.executePreparedStatement(insertDataSQL);
						ps.executeUpdate(insertDataSQL);
					}
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("As shown above, insert complete.");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				response.sendRedirect("admin.jsp?daoru=success2");
			}
			System.out.println("finish");
		} else {
			// 说明没有文件
			response.sendRedirect("admin.jsp?upload=failed");
		}
	}
}

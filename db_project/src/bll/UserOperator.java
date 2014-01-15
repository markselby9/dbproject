package bll;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Handle all user affairs
 * 
 * @author WangYuanbin
 * 
 */
public class UserOperator {
	// Here defines how to connector the database.
	private static final String connectionString = "jdbc:mysql://localhost:3306/db_project";
	// username for mysql, 'root' by default.
	private static final String dbUsername = "root";
	// user password for mysql, change to yours.
	private static final String dbPassword = "219739";
	// load the mysql driver to the memory.
	// without this driver the program will not know how to connect to the
	// database.
	// we only need to load this driver ONCE.
	// so the code is in the static constructor.
	static {
		try {
			// class name for mysql driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a User's password in the database
	 * 
	 * @return String password
	 * @throws SQLException
	 */
	public static String getPassword(String key) throws SQLException {
		String password = null;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getPasswordSQL = "SELECT password from login_info where username ='"
				+ key + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(getPasswordSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return password;
	}

	/**
	 * Check if the password for the user is correct
	 * 
	 * @return boolean correct
	 * @throws SQLException
	 */
	public static boolean checkPassword(String username, String password)
			throws SQLException {
		boolean correct = false;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getPasswordSQL = "SELECT * from login_info where username = '"
				+ username + "' and password ='" + password + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(getPasswordSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				correct = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return correct;
	}

	/**
	 * Get a User's type in the database
	 * 
	 * @return String type
	 * @throws SQLException
	 */
	public static String getUserType(String username) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getUserTypeSQL = "SELECT user_type from login_info where username ='"
				+ username + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(getUserTypeSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("user_type");
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return null;
	}

	/**
	 * Get a work's all information in the database
	 * 
	 * @return a user
	 * @throws SQLException
	 */
	public static Worker getWorker(String username) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getWorkerSQL = "";
		int worker_ID = 0;
		worker_ID = Integer.parseInt(username);
		getWorkerSQL = "SELECT * from worker where worker_ID ='" + worker_ID
				+ "'";
		Worker worker = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(getWorkerSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				String worker_Name = rs.getString("worker_Name");
				int office_ID = rs.getInt("office_ID");
				String job = rs.getString("job");
				worker = new Worker(worker_ID, worker_Name, office_ID, job);
			}
			return worker;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}

		return null;
	}

	/**
	 * Insert the information into TABLE office
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static int addOfficeInfo() throws SQLException {
		// if it is successful
		int success = 0;

		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllPeopleSQL = "select * from office";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllPeopleSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			if (rs.next()) {
				success = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

		// Information already exist! no need to reinsert
		if (success == -1) {
			return -1;
		}

		// Read from excel
		Workbook rwb = null;
		try {
			// Construct Workbook from InputStream
			InputStream is = new FileInputStream("交管所.xls");
			rwb = Workbook.getWorkbook(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Get Sheet
		Sheet worksheet = rwb.getSheet(0);

		// Write data
		int colNum = worksheet.getColumns();
		int rowNum = worksheet.getRows();
		String insertDataSQL = "";
		PreparedStatement ps2 = null;
		String data[] = new String[colNum];
		for (int i = 1; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				Cell cji = worksheet.getCell(j, i);
				data[j] = cji.getContents();
			}
			insertDataSQL = "INSERT INTO office VALUES ('" + data[0] + "', '"
					+ data[1] + "', '" + data[2] + "', '" + data[3] + "', '"
					+ data[4] + "', '" + data[5] + "')";
			try {
				// Prepare the statement according to the SQL statement.
				ps2 = c.prepareStatement(insertDataSQL);
				// Execute the Query
				ps2.executeUpdate(insertDataSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Close the connection after using.
		if (ps2 != null) {
			ps2.close();
		}
		if (c != null) {
			c.close();
		}

		return 0;
	}

	/**
	 * Insert the information of admins & workers into TABLE worker
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static int addPeopleIntoWorker() throws SQLException {
		// if it is successful
		int success = 0;

		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllPeopleSQL = "select * from worker";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllPeopleSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			if (rs.next()) {
				success = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

		// Information already exist! no need to reinsert
		if (success == -1) {
			return -1;
		}

		// Read from excel
		Workbook rwb = null;
		try {
			// Construct Workbook from InputStream
			InputStream is = new FileInputStream("交管所工作人员.xls");
			rwb = Workbook.getWorkbook(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Get Sheet
		Sheet worksheet = rwb.getSheet(0);

		// Write data
		int colNum = worksheet.getColumns();
		int rowNum = worksheet.getRows();
		String insertDataSQL = "";
		String insertToLoginSQL1 = "";
		String insertToLoginSQL2 = "";
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		String data[] = new String[colNum];
		// 添加admin
		insertToLoginSQL1 = "INSERT INTO login_info(username, password, user_type) VALUES ('admin','admin','admin');";
		try {
			// Prepare the statement according to the SQL statement.
			ps3 = c.prepareStatement(insertToLoginSQL1);
			// Execute the Query
			ps3.executeUpdate(insertToLoginSQL1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 1; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				Cell cji = worksheet.getCell(j, i);
				data[j] = cji.getContents();
			}
			// 将交管所工作人员信息加入worker中
			insertDataSQL = "INSERT INTO worker VALUES ('" + data[0] + "','"
					+ data[1] + "','" + data[2] + "','" + data[3] + "');";
			// 同时将其id作为用户名和密码存入login_info中
			insertToLoginSQL2 = "INSERT INTO login_info(username, password, user_type) VALUES ('"
					+ data[0] + "','" + data[0] + "', 'worker');";
			try {
				// Prepare the statement according to the SQL statement.
				ps2 = c.prepareStatement(insertDataSQL);
				// Execute the Query
				ps2.executeUpdate(insertDataSQL);
				// Prepare the statement according to the SQL statement.
				ps4 = c.prepareStatement(insertToLoginSQL2);
				// Execute the Query
				ps4.executeUpdate(insertToLoginSQL2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Close the connection after using.
		if (ps2 != null) {
			ps2.close();
		}
		if (c != null) {
			c.close();
		}

		return 0;
	}

	/**
	 * Get the admin's office
	 * 
	 * @return a office
	 * @throws SQLException
	 */
	public static Office getOffice(int admin_ID) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getOfficeSQL = "SELECT * from office where admin_ID ='"
				+ admin_ID + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Office office = null;
		try {
			ps = c.prepareStatement(getOfficeSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				int office_ID = rs.getInt("office_ID");
				String province = rs.getString("province");
				String city = rs.getString("city");
				String district = rs.getString("district");
				String street = rs.getString("street");
				// add this user to the list
				office = new Office(office_ID, province, city, district,
						street, admin_ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return office;
	}

	/**
	 * Get all workers of the specific office in the database
	 * 
	 * @return a list contains all workers in the office
	 * @throws SQLException
	 */
	public static List<Worker> getAllWorkers() throws SQLException {
		List<Worker> workers = new ArrayList<Worker>();
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllWorkerSQL = "SELECT * from worker where job ='管理员'";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllWorkerSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			while (rs.next()) {
				// get int value
				int worker_ID = rs.getInt("worker_ID");
				int office_ID = rs.getInt("office_ID");
				// get string value
				String worker_Name = rs.getString("worker_Name");
				String job = rs.getString("job");
				// add this person to the list
				workers.add(new Worker(worker_ID, worker_Name, office_ID, job));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return workers;
	}

	/**
	 * Get one people in the worker database
	 * 
	 * @return Worker person
	 * @throws SQLException
	 */
	public static Worker getOnePerson(int id) throws SQLException {
		Worker person = null;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getOnePersonSQL = "SELECT * from worker where worker_ID ='" + id
				+ "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getOnePersonSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			while (rs.next()) {
				String worker_Name = rs.getString("worker_Name");
				int office_ID = rs.getInt("office_ID");
				String job = rs.getString("job");
				// add this person to the list
				person = new Worker(id, worker_Name, office_ID, job);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return person;
	}

	/**
	 * Modify a person's information in the worker database
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static void modifyPeople(int worker_ID, int office_ID, String job)
			throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String updatePeopleSQL = "UPDATE worker set office_ID ='" + office_ID
				+ "', job ='" + job + "' WHERE worker_ID ='" + worker_ID + "'";
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(updatePeopleSQL);
			ps.executeUpdate(updatePeopleSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
	}

	/**
	 * Modify a person's information in the worker database
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static void changePassword(String username, String password)
			throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String updatePeopleSQL = "UPDATE login_info set password ='" + password
				+ "' WHERE username ='" + username + "'";
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(updatePeopleSQL);
			ps.executeUpdate(updatePeopleSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
	}

	/**
	 * Delete a person in the database
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static void deletePeople(int id) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String deletePeopleSQL = "DELETE FROM worker where worker_ID ='" + id
				+ "'";
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(deletePeopleSQL);
			ps.executeUpdate(deletePeopleSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
	}

	/**
	 * Insert a person in the database
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static void addPeople(String worker_Name, int office_ID, String job)
			throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String insertPeopleSQL = "INSERT INTO worker(worker_Name, office_ID, job) values('"
				+ worker_Name + "','" + office_ID + "','" + job + "');";
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(insertPeopleSQL);
			ps.executeUpdate(insertPeopleSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
	}

	/**
	 * Insert a student in the database for logging in
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static void addUsername(String ID) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		// 先判断表里是否已有该用户名
		boolean exist = false;
		String checkStudentSQL = "SELECT * FROM login_info where username ='"
				+ ID + "'";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			ps1 = c.prepareStatement(checkStudentSQL);
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps1 != null) {
				ps1.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
		}
		if (exist == true) {
			return;
		}
		// 否则不存在
		String insertStudentSQL = "INSERT INTO login_info(username, password, user_type) values('"
				+ ID + "','" + ID + "','student');";
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(insertStudentSQL);
			ps.executeUpdate(insertStudentSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
	}

	/**
	 * Get a user in the database
	 * 
	 * @return a user
	 * @throws SQLException
	 */
	public static Student getUser(String key) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getStudentSQL = "SELECT * from stu_info where ID ='" + key + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Student student = null;
		try {
			ps = c.prepareStatement(getStudentSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 取其所有信息汇总至user中
				int stu_ID = rs.getInt("stu_ID");
				int ID_Type = rs.getInt("ID_Type");
				String ID = rs.getString("ID");
				String stu_Name = rs.getString("stu_Name");
				String stu_Gender = rs.getString("stu_Gender");
				Timestamp birthday = rs.getTimestamp("birthday");
				String nationality = rs.getString("nationality");
				String home_Address = rs.getString("home_Address");
				String pass = rs.getString("pass");
				String reject_Reason = rs.getString("reject_Reason");
				Timestamp apply_Time = rs.getTimestamp("apply_Time");
				int worker_ID = rs.getInt("worker_ID");
				// add this user to the list
				student = new Student(stu_ID, ID_Type, ID, stu_Name,
						stu_Gender, birthday, nationality, home_Address, pass,
						reject_Reason, apply_Time, worker_ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return student;
	}

	/**
	 * Get the pass state of a student in stu_test_info
	 * 
	 * @return String state
	 * @throws SQLException
	 */
	public static String getPass(String ID) throws SQLException {
		String state = null;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getPasswordSQL = "SELECT apply_Pass from stu_test_info where ID ='"
				+ ID + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(getPasswordSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				state = rs.getString("apply_Pass");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return state;
	}

	/**
	 * Get the reject reason of a student in stu_test_info
	 * 
	 * @return String reason
	 * @throws SQLException
	 */
	public static String getReason(String ID) throws SQLException {
		String reason = null;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		String getPasswordSQL = "SELECT reject_Reason from stu_test_info where ID ='"
				+ ID + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(getPasswordSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				reason = rs.getString("reject_Reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return reason;
	}

}
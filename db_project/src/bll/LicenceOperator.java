package bll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Handle all licence affairs
 * 
 * @author WangYuanbin
 * 
 */
public class LicenceOperator {
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
	 * Test if licences already exist in the database
	 * 
	 * @return boolean exist
	 * @throws SQLException
	 */
	public static boolean haveLicences() throws SQLException {
		boolean exist = false;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllLicenceSQL = "select * from licence_info";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllLicenceSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			if (rs.next()) {
				exist = true;
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

		return exist;
	}

	/**
	 * Test if licences already exist in the database
	 * 
	 * @return boolean exist
	 * @throws SQLException
	 */
	public static Licence haveLicence(String ID) throws SQLException {
		Licence licence = null;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllLicenceSQL = "select * from licence_info where ID ='" + ID
				+ "'";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllLicenceSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			while (rs.next()) {
				int licence_ID = rs.getInt("licence_ID");
				String stu_Name = rs.getString("stu_Name");
				String stu_Gender = rs.getString("stu_Gender");
				int ID_Type = rs.getInt("ID_Type");
				String nationality = rs.getString("nationality");
				Timestamp birthday = rs.getTimestamp("birthday");
				String home_Address = rs.getString("home_Address");
				String licence_State = rs.getString("licence_State");
				Timestamp get_Licence_Time = rs.getTimestamp("get_Licence_Time");
				String allowed_Car_Type = rs.getString("allowed_Car_Type");
				String valid_Time = rs.getString("valid_Time");
				int office_ID = rs.getInt("office_ID");
				int score = rs.getInt("score");
				String photo_Address = rs.getString("photo_Address");
				licence = new Licence(licence_ID, stu_Name, stu_Gender,
						ID_Type, ID, nationality, birthday, home_Address,
						licence_State, get_Licence_Time, allowed_Car_Type,
						valid_Time, office_ID, score, photo_Address);
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

		return licence;
	}

	/**
	 * Test if vioLicences already exist in the database
	 * 
	 * @return boolean exist
	 * @throws SQLException
	 */
	public static boolean haveVioLicences() throws SQLException {
		boolean exist = false;
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllVioLicenceSQL = "select * from licence_violation_info";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllVioLicenceSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			if (rs.next()) {
				exist = true;
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

		return exist;
	}

	/**
	 * Add a licence in the database
	 * 
	 * @return none
	 * @throws SQLException
	 */
	public static void addLicence(String ID) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		// 先得到该ID的所有信息
		String getIDInfoSQL = "SELECT * FROM stu_info where ID='" + ID + "'";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			// prepare the statement according to the SQL statement.
			ps1 = c.prepareStatement(getIDInfoSQL);
			// Execute the Query
			rs1 = ps1.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			while (rs1.next()) {
				// 得到相关信息
				String stu_Name = rs1.getString("stu_Name");
				String stu_Gender = rs1.getString("stu_Gender");
				int ID_Type = rs1.getInt("ID_Type");
				String nationality = rs1.getString("nationality");
				Timestamp birthday = rs1.getTimestamp("birthday");
				String home_Address = rs1.getString("home_Address");
				// 插入！
				String insertLicenceSQL = "";
				if (stu_Gender.equals("男")) {
					insertLicenceSQL = "INSERT INTO licence_info(stu_Name, stu_Gender, ID_Type, ID, nationality, birthday, home_Address, licence_State, get_Licence_Time, allowed_Car_Type, valid_Time, office_ID, score, photo_Address) values('"
							+ stu_Name
							+ "','"
							+ stu_Gender
							+ "','"
							+ ID_Type
							+ "','"
							+ ID
							+ "','"
							+ nationality
							+ "','"
							+ birthday
							+ "','"
							+ home_Address
							+ "', 'valid', now(), 'C1', '10年', '1', '12', 'images/head/1.jpg');";
				} else {
					insertLicenceSQL = "INSERT INTO licence_info(stu_Name, stu_Gender, ID_Type, ID, nationality, birthday, home_Address, licence_State, get_Licence_Time, allowed_Car_Type, valid_Time, office_ID, score, photo_Address) values('"
							+ stu_Name
							+ "','"
							+ stu_Gender
							+ "','"
							+ ID_Type
							+ "','"
							+ ID
							+ "','"
							+ nationality
							+ "','"
							+ birthday
							+ "','"
							+ home_Address
							+ "', 'valid', now(), 'C1', '10年', '1', '12', 'images/head/5.jpg');";
				}
				PreparedStatement ps2 = null;
				try {
					ps2 = c.prepareStatement(insertLicenceSQL);
					ps2.executeUpdate(insertLicenceSQL);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// Notice! Always close the connection after using.
					if (ps2 != null) {
						ps2.close();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Notice! Always close the connection after using.
			if (rs1 != null) {
				rs1.close();
			}
			if (ps1 != null) {
				ps1.close();
			}
			if (c != null) {
				c.close();
			}
		}

	}

}

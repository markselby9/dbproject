package bll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle all licence affairs
 * 
 * @author WangYuanbin
 * 
 */
public class GradeOperator {
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
	 * get Valid ID List for the specific subject
	 * 
	 * @return List<String> IDs
	 * @throws SQLException
	 */
	public static List<String> getIDList(int subject_ID) throws SQLException {
		List<String> IDs = new ArrayList<String>();
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String getAllIDSQL = "select * from subject_grade where subject_ID ='" + subject_ID + "'";
		try {
			// prepare the statement according to the SQL statement.
			ps = c.prepareStatement(getAllIDSQL);
			// Execute the Query
			rs = ps.executeQuery();
			// get data line by line.
			// stops if rs.next() is false, which means no more lines available
			while (rs.next()) {
				String ID = rs.getString("ID");
				String grade = rs.getString("grade");
				String valid = rs.getString("valid");
				if(valid.equals("Y") && (Integer.parseInt(grade)>80)){					
					IDs.add(ID);
				}
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
		return IDs;
	}
	
	/**
	 * get the valid ID List after test 2
	 * 
	 * @return boolean exist
	 * @throws SQLException
	 */
	public static List<String> deleteInvalidIDFor2(List<String> IDs) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		for(int i = 0; i < IDs.size(); i++){
			String getAllIDSQL = "select * from subject_grade where ID ='" + IDs.get(i) + "' and subject_ID = '2' and grade > 80";
			try {
				// prepare the statement according to the SQL statement.
				ps = c.prepareStatement(getAllIDSQL);
				// Execute the Query
				rs = ps.executeQuery();
				// get data line by line.
				// stops if rs.next() is false, which means no more lines available
				if (!rs.next()) {					
					System.out.println("i"+i);
					IDs.remove(IDs.get(i));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// Notice! Always close the connection after using.
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		return IDs;
	}
	
	/**
	 * get the valid ID List after test 3
	 * 
	 * @return boolean exist
	 * @throws SQLException
	 */
	public static List<String> deleteInvalidIDFor3(List<String> IDs) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		for(int i = 0; i < IDs.size(); i++){
			String getAllIDSQL = "select * from subject_grade where ID ='" + IDs.get(i) + "' and subject_ID = '3' and grade = '及格'";
			try {
				// prepare the statement according to the SQL statement.
				ps = c.prepareStatement(getAllIDSQL);
				// Execute the Query
				rs = ps.executeQuery();
				// get data line by line.
				// stops if rs.next() is false, which means no more lines available
				if (!rs.next()) {					
					IDs.remove(IDs.get(i));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		
		return IDs;
	}
	
	/**
	 * get the valid ID List after test 4
	 * 
	 * @return boolean exist
	 * @throws SQLException
	 */
	public static List<String> deleteInvalidIDFor4(List<String> IDs) throws SQLException {
		// notice, this Connection is java.sql.Connection
		// try to get the connection by DriverManager.
		// you should always obtain a Connection before you query the database
		Connection c = DriverManager.getConnection(connectionString,
				dbUsername, dbPassword);
		PreparedStatement ps = null;
		ResultSet rs = null;
		for(int i = 0; i < IDs.size(); i++){
			String getAllIDSQL = "select * from subject_grade where ID ='" + IDs.get(i) + "' and subject_ID = '4' and grade = '及格'";
			try {
				// prepare the statement according to the SQL statement.
				ps = c.prepareStatement(getAllIDSQL);
				// Execute the Query
				rs = ps.executeQuery();
				// get data line by line.
				// stops if rs.next() is false, which means no more lines available
				if (!rs.next()) {					
					IDs.remove(IDs.get(i));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		return IDs;
	}

}

package bll;

import java.sql.*;

public class DBBean {
	private Connection con = null;
        private PreparedStatement ps = null;
	private Statement stmt;
	private ResultSet rs;
	
	public DBBean() {
		super();
	}
	
	public void Connect() throws Exception {
		String url = "jdbc:mysql://localhost:3306/db_project";
		if(this.con == null) {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection(url, "root", "219739");
		}
	}
        
        public PreparedStatement executePreparedStatement(String sql) throws Exception {
            this.ps = this.con.prepareStatement(sql);
            return this.ps;
        }
	
	public ResultSet executeQuery(String sql) throws Exception {
		if(this.con == null)
			throw new Exception("没有链接对象");
		this.stmt = this.con.createStatement();
		this.rs = this.stmt.executeQuery(sql);
		return this.rs;
	}
	
	public int executeUpdate(String sql) throws Exception {
		if(con == null)
			throw new Exception("没有连接对象");
		this.stmt = this.con.createStatement();
		return this.stmt.executeUpdate(sql);
	}
	
	public void close() {
		try {
			if(this.rs != null)
				this.rs.close();
			this.stmt.close();
			this.con.close();
		}
		catch(Exception e) {}
	}
}

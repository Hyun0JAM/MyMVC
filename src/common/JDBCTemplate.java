package common;
import java.sql.*;

public class JDBCTemplate {
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "userxe", "passxe");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public void close(Connection con) {
		try {
			if(con!=null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void commit(Connection con) {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void rollback(Connection con) {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(Statement stmt) {
		try {
			if(stmt!=null) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(ResultSet rs) {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

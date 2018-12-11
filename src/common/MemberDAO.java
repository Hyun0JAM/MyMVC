package common;

import java.sql.*;

//member.model.dao.MemberDao
public class MemberDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public boolean changePwdChange(Connection con,String userid) throws SQLException {
		String sql =" select count(*) as cnt from member where userid=? and last_modified <= sysdate-90 ";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userid);
		rs = pstmt.executeQuery();
		rs.next();
		if(rs.getInt("cnt")==1) return true;
		else return false;
	}
}

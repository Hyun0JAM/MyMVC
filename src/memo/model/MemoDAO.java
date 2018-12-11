package memo.model;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

import jdbc.util.AES256;
import my.util.MyKey;

/* 아파치 톰캣이 제공하는 DBCP(DB Connection Pool)를 이용하여 MemoDAO 클래스를 생성 */
public class MemoDAO implements interMemoDAO{
	private DataSource ds = null; // 객체 변수 ds는 아파치 톰캣이 제공하는 dbcp이다.
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	AES256 aes = null;
	
	/* MemoDAO 생성자에서 해야할 일 
	 * 아파치 톰캣이 제공하는 dbcp객체인 ds를 얻어오는 것이다.
	 */
	public MemoDAO() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");
			String key = MyKey.key; // 암호화 복호화 키
			aes = new AES256(key);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//사용한 자원을 반납하는 메소드 생성하기
	public void close() {
		try {
			if(rs!=null) {
				rs.close();
				rs = null;
			}
			if(pstmt!=null) {
				pstmt.close();
				pstmt = null;
			}
			if(conn!=null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int memoInsert(MemoVO memovo) throws SQLException{
		int result=0;
		try {
			conn = ds.getConnection(); //dbcp객체 ds를 통해 톰캣의 context.xml에 설정 되어진 Connection객체를 빌려 오는 것 이다.
			String sql=" insert into jsp_memo(idx,fk_userid,name,msg,writedate,cip,status) "
				     + " values(jsp_memo_idx.nextval,?,?,?,default,?,default) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memovo.getFk_userid());
			pstmt.setString(2, memovo.getName());
			pstmt.setString(3, memovo.getMsg());
			pstmt.setString(4, memovo.getCip());
			result = pstmt.executeUpdate();
		}
		finally {close();}
		return result;
	}
	@Override
	public int getTotalCount() throws SQLException {
		int count = 0;
		conn = ds.getConnection();
		try {
			String sql = "select count(*) as CNT "+
						 " from jsp_memo "+
						 " where status = 1 ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("CNT");
		} finally {
			close();
		}
		return count;
	}
	@Override
	public int getTotalCount(String fk_userid) throws SQLException {
		int count = 0;
		conn = ds.getConnection();
		try {
			String sql = "select count(*) as CNT "+
						 " from jsp_memo "+
						 " where 1=1 and fk_userid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fk_userid);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("CNT");
		} finally {
			close();
		}
		return count;
	}
	@Override
	public List<MemoVO> getAllMemo() throws SQLException {
		List<MemoVO> memolist = null;
		try {
			conn = ds.getConnection(); //dbcp객체 ds를 통해 톰캣의 context.xml에 설정 되어진 Connection객체를 빌려 오는 것 이다.
			String sql = " select idx,fk_userid,name,msg,to_char(writedate,'yyyy-mm-dd hh24:mi:ss') as writedate,cip,status "
					   + " from jsp_memo "
					   + " where status=1 "
					   + " order by idx desc ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) {  
					memolist = new ArrayList<MemoVO>();
				}
				int idx = rs.getInt("IDX");
				String fk_userid = rs.getString("fk_userid");
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				String writedate = rs.getString("writedate");
				String cip = rs.getString("cip");
				int status = rs.getInt("status");
				MemoVO memovo = new MemoVO();
				memovo.setIdx(idx);
				memovo.setFk_userid(fk_userid);
				memovo.setName(name);
				memovo.setMsg(msg);
				memovo.setWritedate(writedate);
				memovo.setCip(cip);
				memovo.setStatus(status);
				memolist.add(memovo);
			}
		}
		finally {close();}
		return memolist;
	}
	@Override
	public List<MemoVO> getAllMemo(int sizePerPage,int currentShowPageNo) throws SQLException {
		List<MemoVO> memolist = null;
		try {
			conn = ds.getConnection(); //dbcp객체 ds를 통해 톰캣의 context.xml에 설정 되어진 Connection객체를 빌려 오는 것 이다.
			String sql = " select rno,idx,fk_userid,name,msg,writedate,cip,status "+
					" from "+
					"    (select rownum as rno,idx,fk_userid,name,msg,writedate,cip,status "+
					"    from(\n"+
					"        select idx,fk_userid,name,msg,to_char(writedate,'yyyy-mm-dd hh24:mi:ss') as writedate,cip,status "+					"        from jsp_memo "+
					" 		 where status=1 "+
					"        order by idx desc "+
					"    )) where rno between ? and ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,(currentShowPageNo*sizePerPage)-(sizePerPage-1));
			pstmt.setInt(2,currentShowPageNo*sizePerPage);
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) {  
					memolist = new ArrayList<MemoVO>();
				}
				int idx = rs.getInt("IDX");
				String fk_userid = rs.getString("fk_userid");
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				String writedate = rs.getString("writedate");
				String cip = rs.getString("cip");
				int status = rs.getInt("status");
				MemoVO memovo = new MemoVO();
				memovo.setIdx(idx);
				memovo.setFk_userid(fk_userid);
				memovo.setName(name);
				memovo.setMsg(msg);
				memovo.setWritedate(writedate);
				memovo.setCip(cip);
				memovo.setStatus(status);
				memolist.add(memovo);
			}
		}
		finally {close();}
		return memolist;
	}
	@Override
	public List<MemoVO> getAllMemo(String fk_userid,int sizePerPage,int currentShowPageNo) throws SQLException {
		List<MemoVO> memolist = null;
		try {
			conn = ds.getConnection(); //dbcp객체 ds를 통해 톰캣의 context.xml에 설정 되어진 Connection객체를 빌려 오는 것 이다.
			String sql = " select rno,idx,fk_userid,name,msg,writedate,cip,status "+
					" from "+
					"    (select rownum as rno,idx,fk_userid,name,msg,writedate,cip,status "+
					"    from(\n"+
					"        select idx,fk_userid,name,msg,to_char(writedate,'yyyy-mm-dd hh24:mi:ss') as writedate,cip,status "+
					"        from jsp_memo "+
					" 		 where fk_userid = ? " +
					"        order by idx desc "+
					"    ))where rno between ? and ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,fk_userid);
			pstmt.setInt(2,(currentShowPageNo*sizePerPage)-(sizePerPage-1));
			pstmt.setInt(3,currentShowPageNo*sizePerPage);
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) {  
					memolist = new ArrayList<MemoVO>();
				}
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				String writedate = rs.getString("writedate");
				String cip = rs.getString("cip");
				int status = rs.getInt("status");
				MemoVO memovo = new MemoVO();
				memovo.setIdx(idx);
				memovo.setFk_userid(fk_userid);
				memovo.setName(name);
				memovo.setMsg(msg);
				memovo.setWritedate(writedate);
				memovo.setCip(cip);
				memovo.setStatus(status);
				memolist.add(memovo);
			}
		}
		finally {close();}
		return memolist;
	}
	@Override
	public int setMemoBlind(String idx) throws SQLException {
		conn = ds.getConnection();
		int result = 0;
		try {
			String sql = " update jsp_memo set status=0 "+
						 " where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			result = pstmt.executeUpdate();
		}
		finally {close();}
		return result;
	}
	@Override
	public int setMemoOpen(String idx) throws SQLException {
		conn = ds.getConnection();
		int result = 0;
		try {
			String sql = " update jsp_memo set status=1 "+
						 " where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			result = pstmt.executeUpdate();
		}
		finally {close();}
		return result;
	}
	@Override
	public int deleteMemo(String idx) throws SQLException{
		conn = ds.getConnection();
		conn.setAutoCommit(false);
		int result1 = 0, result2=0;
		try {
			//트랜잭션 처리를 해야 하므로 수동커밋으로 전환
			String sql = " insert into jsp_memo_delete(idx,userid,name,msg,writedate,cip,status) "+
					     " select idx,fk_userid,name,msg,writedate,cip,status from jsp_memo where idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			result1 = pstmt.executeUpdate();
			String sql2 = "delete from jsp_memo where idx = ? ";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, idx);
			result2 = pstmt.executeUpdate();
			if(result1*result2==1) conn.commit();
			else conn.rollback();
		}finally {close();}
		return result1*result2;
	}
	
}

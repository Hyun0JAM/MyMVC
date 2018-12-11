package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;
import jdbc.util.AES256;
import jdbc.util.SHA256;
import my.util.MyKey;

public class MemberDAO implements InterMemberDAO{
	private DataSource ds = null; 
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	AES256 aes = null;
	
	public MemberDAO() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");
			String key = MyKey.key; 
			aes = new AES256(key);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
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
	public MemberVO loginMember(String userid,String pwd) throws SQLException {
		MemberVO membervo = null;
		try {
			conn = ds.getConnection();
			
			String sql = " select idx,userid,name,coin,point,"
					   + " to_char(lastloginDate,'yyyy-mm-dd') as lastloginDate ,"
					   + " trunc(months_between(sysdate,lastPwdChangeDate)) as pwdchangegap, "
					   + " trunc(months_between(sysdate,lastLoginDate)) as lastlogingap "
					   + " from jsp_member "
					   + " where status=1 and userid=? and pwd=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, SHA256.encrypt(pwd));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int idx = rs.getInt("IDX");
				String name = rs.getString("NAME");
				int coin= rs.getInt("COIN");
				int point= rs.getInt("POINT");
				String lastloginDate= rs.getString("lastloginDate");
				int pwdchangegap = rs.getInt("pwdchangegap");
				int lastlgingap = rs.getInt("lastlogingap");
				// 암호를 변경한 날짜가 현재시각으로 부터 6개월이 지났으면true, 아니면 false
				membervo = new MemberVO();
				membervo.setIdx(idx);
				membervo.setUserid(userid);
				membervo.setName(name);
				membervo.setCoin(coin);
				membervo.setPoint(point);
				membervo.setLastloginDate(lastloginDate);
				if(lastlgingap>=12) membervo.setHumyun(true);
				else {
					if(pwdchangegap>=6) membervo.setRequirePwdChange(true);
					sql = " update jsp_member set lastLoginDate = sysdate where userid = ? ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userid);
					pstmt.executeUpdate();
				}
			}
		}finally {close();}
		return membervo;
	}
	
	@Override
	public boolean idDuplicateCheck(String userid) throws SQLException {
		conn = ds.getConnection();
		try {
			String sql = " select count(*) as cnt "
					   + " from jsp_member where userid = ? ";  
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			rs.next();
			int cnt =rs.getInt("cnt");
			if(cnt==1) return false;
			else return true;
		} finally {
			close();
		}
	}
	@Override
	public int registerMember(MemberVO membervo) throws SQLException {
		int result = 0;		
		try {
			conn = ds.getConnection();
			String sql = " insert into jsp_member(IDX, USERID, NAME, PWD, EMAIL, HP1, HP2, HP3, POST1, POST2, ADDR1, ADDR2, GENDER, BIRTHDAY, COIN, POINT, REGISTERDAY, STATUS) "
					   + " values(seq_jsp_member.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, default, default, default, default) ";  
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, membervo.getUserid());
			pstmt.setString(2, membervo.getName());
			pstmt.setString(3, SHA256.encrypt(membervo.getPwd())); 
			pstmt.setString(4, aes.encrypt(membervo.getEmail())); 
			pstmt.setString(5, membervo.getHp1());
			pstmt.setString(6, aes.encrypt(membervo.getHp2()));
			pstmt.setString(7, aes.encrypt(membervo.getHp3()));
			pstmt.setString(8, membervo.getPost1());
			pstmt.setString(9, membervo.getPost2());
			pstmt.setString(10, membervo.getAddr1());
			pstmt.setString(11, membervo.getAddr2());
			pstmt.setString(12, membervo.getGender());
			pstmt.setString(13, membervo.getBirthyyyy()+membervo.getBirthmm()+membervo.getBirthdd());
			
			result = pstmt.executeUpdate();
		
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}// end of registerMember(MemberVO membervo)--------
	
	@Override
	public int getTotalCount(String searchType, String searchWord, int period) throws SQLException {
		int count = 0;
		conn = ds.getConnection();
		try {
			String sql = "select count(*) as CNT "+
						 " from jsp_member "+
						 " where 1=1 and months_between(sysdate,lastLoginDate)<=12 ";
			if("email".equals(searchType)) searchWord = aes.encrypt(searchWord);
			if(period == -1) {
				sql += " and "+searchType+" like '%'|| ? ||'%' "; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
			}
			else {
				sql += " and "+searchType+" like '%'|| ? ||'%' "+ 
					   " and to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') - "+ 
					   " to_date(to_char(registerday, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, period);
			}
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("CNT");
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	@Override
	public int getAdminTotalCount(String searchType, String searchWord, int period) throws SQLException {
		int count = 0;
		conn = ds.getConnection();
		try {
			String sql = "select count(*) as CNT "+
						 " from jsp_member "+
						 " where 1=1 ";
			if("email".equals(searchType)) searchWord = aes.encrypt(searchWord);
			if(period == -1) {
				sql += " and "+searchType+" like '%'|| ? ||'%' "; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
			}
			else {
				sql += " and "+searchType+" like '%'|| ? ||'%' "+ 
					   " and to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') - "+ 
					   " to_date(to_char(registerday, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, period);
			}
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("CNT");
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}
	@Override
	public List<MemberVO> getAllMember(int sizePerPage, int currentShowPageNo, String searchType,
			String searchWord, int period) throws SQLException {
		conn = ds.getConnection();
		List<MemberVO> memberList=null;
		try {
			String sql = " select RNO,IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT,REGISTERDAY,STATUS,humyun "+
						 " from( "+
						 "    select rownum as RNO,IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT,REGISTERDAY,STATUS,humyun "+
						 "    from( "+
						 "        select IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT "+
						 "              ,to_char(REGISTERDAY,'yyyy-mm-dd') as REGISTERDAY,STATUS,months_between(sysdate,LASTLOGINDATE) as humyun  "+
						 "        from jsp_member "+
						 "        where status=1 and months_between(sysdate,lastLoginDate)<=12 ";
			if("email".equals(searchType)) searchWord = aes.encrypt(searchWord);
			if(period == -1) {
				sql +=   " and "+searchType+" like '%'|| ? ||'%' "+
						 "        order by idx desc "+
						 "    )V "+
						 " )T "+
						 " where T.rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, (currentShowPageNo*sizePerPage)-(sizePerPage-1));
				pstmt.setInt(3, currentShowPageNo*sizePerPage);
			}
			else {
				sql +=   " and "+searchType+" like '%'|| ? ||'%' "+ 
						 " 		  and to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') - "+ 
						 " 		  to_date(to_char(registerday, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= ? "+
						 "        order by idx desc "+
						 "    )V "+
						 " )T "+
						 " where T.rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, period);
				pstmt.setInt(3, (currentShowPageNo*sizePerPage)-(sizePerPage-1));
				pstmt.setInt(4, currentShowPageNo*sizePerPage);
			}
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) memberList = new ArrayList<MemberVO>();
				int idx = rs.getInt("IDX");
				String userid = rs.getString("USERID");
				String name = rs.getString("NAME");
				String pwd = rs.getString("PWD");
				String email = aes.decrypt(rs.getString("EMAIL")); 
				String hp1 = rs.getString("HP1");
				String hp2 = aes.decrypt(rs.getString("HP2")); 
				String hp3 = aes.decrypt(rs.getString("HP3")); 
				String post1 = rs.getString("POST1");
				String post2 = rs.getString("POST2");
				String addr1 = rs.getString("ADDR1");
				String addr2 = rs.getString("ADDR2");
				String gender= rs.getString("GENDER");
				String birthday = rs.getString("BIRTHDAY");
				int coin = rs.getInt("COIN");
				int point = rs.getInt("POINT");
				String registerday = rs.getString("REGISTERDAY");
				int status = rs.getInt("STATUS");
				MemberVO member = new MemberVO(idx, userid, name, pwd, email, hp1, hp2, hp3, post1, post2, addr1, addr2, gender, birthday.substring(0, 4), birthday.substring(4,7), birthday.substring(6), coin, point, registerday, status);
				if(rs.getInt("humyun")>=12) member.setHumyun(true);
				memberList.add(member);
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return memberList;
	}
	@Override
	public List<MemberVO> getAllMemberAdmin(int sizePerPage, int currentShowPageNo, String searchType,
			String searchWord, int period) throws SQLException {
		conn = ds.getConnection();
		List<MemberVO> memberList=null;
		try {
			String sql = " select RNO,IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT,REGISTERDAY,STATUS,humyun "+
						 " from( "+
						 "    select rownum as RNO,IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT,REGISTERDAY,STATUS,humyun "+
						 "    from( "+
						 "        select IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT "+
						 "              ,to_char(REGISTERDAY,'yyyy-mm-dd') as REGISTERDAY,STATUS,months_between(sysdate,LASTLOGINDATE) as humyun  "+
						 "        from jsp_member "+
						 "        where 1=1 ";
			if("email".equals(searchType)) searchWord = aes.encrypt(searchWord);
			if(period == -1) {
				sql +=   " and "+searchType+" like '%'|| ? ||'%' "+
						 "        order by idx desc "+
						 "    )V "+
						 " )T "+
						 " where T.rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, (currentShowPageNo*sizePerPage)-(sizePerPage-1));
				pstmt.setInt(3, currentShowPageNo*sizePerPage);
			}
			else {
				sql +=   " and "+searchType+" like '%'|| ? ||'%' "+ 
						 " 		  and to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') - "+ 
						 " 		  to_date(to_char(registerday, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= ? "+
						 "        order by idx desc "+
						 "    )V "+
						 " )T "+
						 " where T.rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, period);
				pstmt.setInt(3, (currentShowPageNo*sizePerPage)-(sizePerPage-1));
				pstmt.setInt(4, currentShowPageNo*sizePerPage);
			}
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) memberList = new ArrayList<MemberVO>();
				int idx = rs.getInt("IDX");
				String userid = rs.getString("USERID");
				String name = rs.getString("NAME");
				String pwd = rs.getString("PWD");
				String email = aes.decrypt(rs.getString("EMAIL")); 
				String hp1 = rs.getString("HP1");
				String hp2 = aes.decrypt(rs.getString("HP2")); 
				String hp3 = aes.decrypt(rs.getString("HP3")); 
				String post1 = rs.getString("POST1");
				String post2 = rs.getString("POST2");
				String addr1 = rs.getString("ADDR1");
				String addr2 = rs.getString("ADDR2");
				String gender= rs.getString("GENDER");
				String birthday = rs.getString("BIRTHDAY");
				int coin = rs.getInt("COIN");
				int point = rs.getInt("POINT");
				String registerday = rs.getString("REGISTERDAY");
				int status = rs.getInt("STATUS");
				MemberVO member = new MemberVO(idx, userid, name, pwd, email, hp1, hp2, hp3, post1, post2, addr1, addr2, gender, birthday.substring(0, 4), birthday.substring(4,7), birthday.substring(6), coin, point, registerday, status);
				if(rs.getInt("humyun")>=12) member.setHumyun(true);
				memberList.add(member);
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return memberList;
	}
	@Override
	public int enableMember(String idx) throws SQLException{
		conn = ds.getConnection();
		int result =0; 
		try {
			String sql = " update jsp_member set lastLoginDate=sysdate where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(idx));
			result = pstmt.executeUpdate();
		}finally {close();}
		return result;
	}
	
	@Override
	public int deleteMember(String idx) throws SQLException {
		conn = ds.getConnection();
		int result =0; 
		try {
			String sql = " update jsp_member set status=0 where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(idx));
			result = pstmt.executeUpdate();
		}finally {close();}
		return result;
	}
	@Override
	public MemberVO getMemberByIdx(int idx) throws SQLException {
		MemberVO member = null;
		conn = ds.getConnection();
		try {
			String sql = " select IDX,USERID,NAME,PWD,EMAIL,HP1,HP2,HP3,POST1,POST2,ADDR1,ADDR2,GENDER,BIRTHDAY,COIN,POINT,"
					   + " to_char(REGISTERDAY,'yyyy-mm-dd') as REGISTERDAY,STATUS,months_between(sysdate,LASTLOGINDATE) as humyun "
					   + " from jsp_member "
					   + " where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String userid = rs.getString("USERID");
				String name = rs.getString("NAME");
				String pwd = rs.getString("PWD");
				String email = aes.decrypt(rs.getString("EMAIL"));
				String hp1 = rs.getString("HP1");
				String hp2 = aes.decrypt(rs.getString("HP2")); 
				String hp3 = aes.decrypt(rs.getString("HP3")); 
				String post1 = rs.getString("POST1");
				String post2 = rs.getString("POST2");
				String addr1 = rs.getString("ADDR1");
				String addr2 = rs.getString("ADDR2");
				String gender= rs.getString("GENDER");
				String birthday = rs.getString("BIRTHDAY");
				int coin = rs.getInt("COIN");
				int point = rs.getInt("POINT");
				String registerday = rs.getString("REGISTERDAY");
				int status = rs.getInt("STATUS");
				member = new MemberVO(idx, userid, name, pwd, email, hp1, hp2, hp3, post1, post2, addr1, addr2, gender, birthday.substring(0, 4), birthday.substring(5,7), birthday.substring(6), coin, point, registerday, status);
				if(rs.getInt("humyun")>=12) member.setHumyun(true);
				
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {close();}
		return member;
	}
	@Override
	public int recoveryMember(String idx) throws SQLException {
		conn = ds.getConnection();
		int result =0; 
		try {
			String sql = " update jsp_member set status=1 where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(idx));
			result = pstmt.executeUpdate();
		}finally {close();}
		return result;
	}
	@Override
	public int changeMemberInfo(int idx,String name,String pwd,String email,String hp1,String hp2,String hp3,String post1,String post2,String addr1,String addr2) throws SQLException {
		int result =0;
		conn = ds.getConnection();
		try {
			String sql = " update jsp_member set lastPwdChangeDate = sysdate, name=?, pwd=?, email=?, "
					   + " hp1=?, hp2=?, hp3=?, post1=?, post2=?, addr1=?, addr2=? "
					   + " where idx=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, SHA256.encrypt(pwd));
			pstmt.setString(3, aes.encrypt(email));
			pstmt.setString(4, hp1);
			pstmt.setString(5, aes.encrypt(hp2));
			pstmt.setString(6, aes.encrypt(hp3));
			pstmt.setString(7, post1);
			pstmt.setString(8, post2);
			pstmt.setString(9, addr1);
			pstmt.setString(10, addr2);
			pstmt.setInt(11, idx);
			result = pstmt.executeUpdate();			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {close();}
		return result;
	}
	@Override
	public String getUserid(String name, String mobile) throws SQLException {
		String userid = null; 
		try {
			conn = ds.getConnection();
			// DBCP(DB Connection Pool) 객체 ds를 통해  
			// 톰캣의 context.xml 에 설정되어진 Connection 객체를 빌려오는 것이다.
			
			String sql = "select userid "
		              +	" from jsp_member "
					  + " where status = 1 and "
					  + " name = ? and "
					  + " trim(hp1) || trim(hp2) || trim(hp3) = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			String mobilenumber = mobile.substring(0, 3);
			if(mobile.length() == 10) {
				mobilenumber += aes.encrypt(mobile.substring(3, 6));
				mobilenumber += aes.encrypt(mobile.substring(6));
			}
			else if(mobile.length() == 11) {
				mobilenumber += aes.encrypt(mobile.substring(3, 7));
				mobilenumber += aes.encrypt(mobile.substring(7));	
			}
			pstmt.setString(2, mobilenumber);
			rs = pstmt.executeQuery();
			boolean isExists = rs.next();
			if(isExists) userid = rs.getString("userid");
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {close();}
		return userid;				
		
	}// end of String getUserid(String name, String mobile)-------------------

	@Override
	public String getEmail(String userid) throws SQLException {
		String email = null; 
		try {
			conn = ds.getConnection();
			String sql = "select email from jsp_member where userid=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			rs.next();
			email = aes.decrypt(rs.getString("email"));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {close();}
		return email;				
		
	}// end of String getUserid(String name, String mobile)-------------------
	
	@Override
	public int isUserExisets(String userid,String email) throws SQLException {
		int n = 0;
		try {
			conn = ds.getConnection();
			// DBCP(DB Connection Pool) 객체 ds를 통해  
			// 톰캣의 context.xml 에 설정되어진 Connection 객체를 빌려오는 것이다.
			
			String sql = "select count(*) as cnt "
		              +	" from jsp_member "
					  + " where status = 1 and "
					  + "       userid = ? and "
					  + "       email = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, aes.encrypt(email));
			rs = pstmt.executeQuery();
			rs.next();
			n = rs.getInt("cnt");
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {close();}			
		return n;
	}
	@Override
	public int updatePwdUser(String userid, String pwd) throws SQLException {
		int n = 0;
		try {
			conn = ds.getConnection();
			String sql = "update jsp_member set pwd=?,lastPwdChangeDate=sysdate "
					  + " where status = 1 and userid = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, SHA256.encrypt(pwd));
			pstmt.setString(2, userid);
			n = pstmt.executeUpdate();
		} finally {close();}			
		return n;
	}
	@Override
	public int coinAddUpdate(String idx, int coinmoney) throws SQLException {
		int n = 0;
		try {
			conn = ds.getConnection();
			String sql = "update jsp_member set coin=coin+?, point=point+? "
					  + " where status = 1 and idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coinmoney);
			pstmt.setInt(2, coinmoney/100);
			pstmt.setInt(3, Integer.parseInt(idx));
			n = pstmt.executeUpdate();
		} finally {close();}			
		return n;
	}
}

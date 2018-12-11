package member.model;

import java.sql.SQLException;
import java.util.List;

public interface InterMemberDAO {
	void close();
	MemberVO loginMember(String userid,String pwd) throws SQLException;  // �α��� �Ǿ����� ȸ�� ������ ����, �α��� ���н� null����
	int registerMember(MemberVO membervo) throws SQLException;
	boolean idDuplicateCheck(String userid) throws SQLException; // �ߺ����̵� ���θ� üũ�ϴ� �߻� �޼ҵ�
	List<MemberVO> getAllMember(int sizePerPage, int currentShowPageNo, String searchType,String searchWord, int period) throws SQLException;
	List<MemberVO> getAllMemberAdmin(int sizePerPage, int currentShowPageNo, String searchType,String searchWord, int period) throws SQLException;
	int getTotalCount(String searchType,String searchWord,int period) throws SQLException;
	int getAdminTotalCount(String searchType, String searchWord, int period) throws SQLException;
	int deleteMember(String idx) throws SQLException;
	int enableMember(String idx) throws SQLException;
	MemberVO getMemberByIdx(int idx) throws SQLException;
	int recoveryMember(String idx) throws SQLException;
	// *** ID 찾기를 해주는 추상 메소드 *** //
	String getUserid(String name, String mobile) throws SQLException;
	// userid와 email이 유효한지 검증해주는 메소드
	int isUserExisets(String userid, String email) throws SQLException;
	// 암호변경 메소드
	int updatePwdUser(String userid, String pwd) throws SQLException;
	int changeMemberInfo(int idx, String name, String pwd, String email, String hp1, String hp2, String hp3,
			String post1, String post2, String addr1, String addr2) throws SQLException;
	int coinAddUpdate(String idx, int coinmoney) throws SQLException;
	String getEmail(String userid) throws SQLException;  	
}

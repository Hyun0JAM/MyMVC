package memo.model;

import java.sql.SQLException;
import java.util.*;

public interface interMemoDAO {
	void close();
	int memoInsert(MemoVO memovo) throws SQLException;// 메모쓰기 추상메소드
	int getTotalCount() throws SQLException;
	int getTotalCount(String fk_userid) throws SQLException;
	//MemoVO getMemoByIdx(int idx) throws SQLException;
	List<MemoVO>getAllMemo() throws SQLException;// 해쉬 맵을 사용한 메모 전체 조회 추상 메소드
	List<MemoVO> getAllMemo(int sizePerPage, int currentShowPage) throws SQLException;
	List<MemoVO> getAllMemo(String userid,int sizePerPage, int currentShowPage) throws SQLException;
	int setMemoBlind(String idx) throws SQLException;
	int setMemoOpen(String idx) throws SQLException;
	int deleteMemo(String idx) throws SQLException;
}

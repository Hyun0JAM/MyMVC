package memo.model;

import java.sql.SQLException;
import java.util.*;

public interface interMemoDAO {
	void close();
	int memoInsert(MemoVO memovo) throws SQLException;// �޸𾲱� �߻�޼ҵ�
	int getTotalCount() throws SQLException;
	int getTotalCount(String fk_userid) throws SQLException;
	//MemoVO getMemoByIdx(int idx) throws SQLException;
	List<MemoVO>getAllMemo() throws SQLException;// �ؽ� ���� ����� �޸� ��ü ��ȸ �߻� �޼ҵ�
	List<MemoVO> getAllMemo(int sizePerPage, int currentShowPage) throws SQLException;
	List<MemoVO> getAllMemo(String userid,int sizePerPage, int currentShowPage) throws SQLException;
	int setMemoBlind(String idx) throws SQLException;
	int setMemoOpen(String idx) throws SQLException;
	int deleteMemo(String idx) throws SQLException;
}

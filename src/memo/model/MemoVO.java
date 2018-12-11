package memo.model;

public class MemoVO {
	private int	 idx;        // �۹�ȣ(�������� �Է�)
	private String fk_userid;  // ȸ�����̵�
	private String name;       // �ۼ����̸�
	private String msg;        // �޸𳻿�
	private String writedate;  // �ۼ�����
	private String cip;        // Ŭ���̾�Ʈ IP �ּ�
	private int status;        // �ۻ�������
	public MemoVO() {}
	// �⺻�����ڰ� �־�߸� �ڹٱ԰ݼ��� ���� �ڹٺ����� ���� �� �ִ�.
	public MemoVO(int idx, String fk_userid, String name, String msg, String writedate, String cip, int status) {
		super();
		this.idx = idx;
		this.fk_userid = fk_userid;
		this.name = name;
		this.msg = msg;
		this.writedate = writedate;
		this.cip = cip;
		this.status = status;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getWritedate() {
		return writedate;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public String getCip() {
		return cip;
	}
	public void setCip(String cip) {
		this.cip = cip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}

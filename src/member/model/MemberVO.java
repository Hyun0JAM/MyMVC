package member.model;

import java.util.Calendar;

/*
  VO(Value Object) 占쎌굢占쎈뮉  DTO(Data Transfer Object) 占쎄문占쎄쉐占쎈릭疫뀐옙 
*/

public class MemberVO {

	private int idx;            // 占쎌돳占쎌뜚甕곕뜇�깈(占쎈뻻占쏙옙占쎈뮞嚥∽옙 占쎈쑓占쎌뵠占쎄숲揶쏉옙 占쎈굶占쎈선占쎌궔占쎈뼄)
	private String userid;      // 占쎌돳占쎌뜚占쎈툡占쎌뵠占쎈탵
	private String name;        // 占쎌돳占쎌뜚筌륅옙
	private String pwd;         // �뜮袁⑨옙甕곕뜇�깈
	private String email;       // 占쎌뵠筌롫뗄�뵬
	private String hp1;         // 占쎌몧占쏙옙占쎈？ 甕곕뜇�깈
	private String hp2;   
	private String hp3;   
	private String post1;       // 占쎌뒭占쎈젶甕곕뜇�깈
	private String post2;  
	private String addr1;       // 雅뚯눘�꺖
	private String addr2;       // 占쎄맒占쎄쉭雅뚯눘�꺖
	
	private String gender;      // 占쎄쉐癰귨옙   (占쎄텚占쎌쁽 : 1 / 占쎈연占쎌쁽 : 2)
	private String birthyyyy;   // 占쎄문占쎈��
	private String birthmm;     // 占쎄문占쎌뜞
	private String birthdd;     // 占쎄문占쎌뵬
	
	private int coin;           // �굜遺우뵥占쎈만
	private int point;          // 占쎈７占쎌뵥占쎈뱜
	
	private String registerday; // 揶쏉옙占쎌뿯占쎌뵬占쎌쁽
	private int status;         // 占쎌돳占쎌뜚占쎄퉱占쎈닚占쎌��눧占�   1:占쎄텢占쎌뒠揶쏉옙占쎈뮟(揶쏉옙占쎌뿯餓ο옙) / 0:占쎄텢占쎌뒠�겫�뜄�뮟(占쎄퉱占쎈닚) 
	
	private String lastloginDate;
	private String lastPwdChangeDate;
	
	private boolean requirePwdChange = false;
	private boolean humyun = false;

	public boolean isHumyun() {
		return humyun;
	}
	public void setHumyun(boolean humyun) {
		this.humyun = humyun;
	}

	public MemberVO() { }
	
	public MemberVO(int idx, String userid, String name, String pwd, String email, String hp1, String hp2, String hp3,
			String post1, String post2, String addr1, String addr2, 
			String gender, String birthyyyy, String birthmm, String birthdd,
			int coin, int point,
			String registerday, int status) {
		this.idx = idx;
		this.userid = userid;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.hp1 = hp1;
		this.hp2 = hp2;
		this.hp3 = hp3;
		this.post1 = post1;
		this.post2 = post2;
		this.addr1 = addr1;
		this.addr2 = addr2;
		
		this.gender = gender;
		this.birthyyyy = birthyyyy;
		this.birthmm = birthmm;
		this.birthdd = birthdd;
		this.coin = coin;
		this.point = point;
				
		this.registerday = registerday;
		this.status = status;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHp1() {
		return hp1;
	}

	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}

	public String getHp2() {
		return hp2;
	}

	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}

	public String getHp3() {
		return hp3;
	}

	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}

	public String getPost1() {
		return post1;
	}

	public void setPost1(String post1) {
		this.post1 = post1;
	}

	public String getPost2() {
		return post2;
	}

	public void setPost2(String post2) {
		this.post2 = post2;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthyyyy() {
		return birthyyyy;
	}

	public void setBirthyyyy(String birthyyyy) {
		this.birthyyyy = birthyyyy;
	}
	
	public String getBirthmm() {
		return birthmm;
	}

	public void setBirthmm(String birthmm) {
		this.birthmm = birthmm;
	}
	
	public String getBirthdd() {
		return birthdd;
	}

	public void setBirthdd(String birthdd) {
		this.birthdd = birthdd;
	}
	
	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	
	public String getAllPost() {
		return post1+"-"+post2;
	}
	
	public String getAllAddr() {
		return addr1+" "+addr2;
	}
	public String getLastloginDate() {
		return lastloginDate;
	}

	public void setLastloginDate(String lastloginDate) {
		this.lastloginDate = lastloginDate;
	}

	public String getLastPwdChangeDate() {
		return lastPwdChangeDate;
	}

	public void setLastPwdChangeDate(String lastPwdChangeDate) {
		this.lastPwdChangeDate = lastPwdChangeDate;
	}
	public boolean isRequirePwdChange() {
		return requirePwdChange;
	}

	public void setRequirePwdChange(boolean requirePwdChange) {
		this.requirePwdChange = requirePwdChange;
	}
	public String getShowGender() {
		if("1".equals(gender))
			return "남";
		else 
			return "여";
	}
	
	
	public int getShowAge() {

		Calendar currentdate = Calendar.getInstance(); // 占쎌겱占쎌삺占쎄텊筌욎뮇占� 占쎈뻻揶쏄쑴�뱽 占쎈섯占쎈선占쎌궔占쎈뼄.
		int year = currentdate.get(Calendar.YEAR);
		
		return  year - Integer.parseInt(birthyyyy) + 1;
	}
	
}

package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberRegisterEndAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO memberdao = new MemberDAO();
		String name = req.getParameter("name");
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		String email = req.getParameter("email");
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");
		String post1 = req.getParameter("post1");
		String post2 = req.getParameter("post2");
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2");
		String gender = req.getParameter("gender");
		String birthyyyy = req.getParameter("birthyyyy");
		String birthmm = req.getParameter("birthmm");
		String birthdd = req.getParameter("birthdd");
		MemberVO member = new MemberVO();
		member.setName(name);
		member.setUserid(userid);
		member.setPwd(pwd);
		member.setEmail(email);
		member.setHp1(hp1);
		member.setHp2(hp2);
		member.setHp3(hp3);
		member.setPost1(post1);
		member.setPost2(post2);
		member.setAddr1(addr1);
		member.setAddr2(addr2);
		member.setGender(gender);
		member.setBirthyyyy(birthyyyy);
		member.setBirthmm(birthmm);
		member.setBirthdd(birthdd);
		int n = memberdao.registerMember(member);
		String method = req.getMethod();
		if("POST".equalsIgnoreCase(method)) {
			if(n==1) {
				req.setAttribute("msg", "가입성공!!");
				req.setAttribute("loc", "index.do");
			}
			else {
				req.setAttribute("msg", "가입 실패!!");
				req.setAttribute("loc", "javascript:history.back();");
			}
		}
		super.setViewPage("/WEB-INF/msg.jsp");
	}
}

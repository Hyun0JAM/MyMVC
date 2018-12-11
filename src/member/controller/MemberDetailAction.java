package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberDetailAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String idx = req.getParameter("idx");
		MemberDAO memberdao = new MemberDAO();
		MemberVO member = memberdao.getMemberByIdx(Integer.parseInt(idx));
		req.setAttribute("member", member);
		super.setViewPage("/WEB-INF/member/memberDetail.jsp");
	}
}

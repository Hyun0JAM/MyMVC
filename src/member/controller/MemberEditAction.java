package member.controller;

import javax.servlet.http.*;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberEditAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(!"POST".equals(req.getMethod())) {
			
			req.setAttribute("msg", "비정상적인 접근입니다.");
			req.setAttribute("loc", "javascript:history.back()");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			MemberDAO memberdao = new MemberDAO();
			String idx = req.getParameter("idx");
			MemberVO member = memberdao.getMemberByIdx(Integer.parseInt(idx));
			req.setAttribute("member", member);
			super.setViewPage("/WEB-INF/member/memberEdit.jsp");
		}	
	}
}

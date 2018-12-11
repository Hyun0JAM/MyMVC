package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;

public class MemberRegisterAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		MemberVO member = (MemberVO)session.getAttribute("member");
		if(member!=null) {
			req.setAttribute("msg", "이미 로그인 하셨습니다.");
			req.setAttribute("loc", "javascript:history.back()");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/member/memberform.jsp");
	}
}

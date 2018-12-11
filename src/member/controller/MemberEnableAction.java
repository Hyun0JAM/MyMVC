package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberEnableAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(!"POST".equals(req.getMethod())) {
			req.setAttribute("msg", "비정상적인 접근입니다..");
			req.setAttribute("loc", "javascript:history.back()");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		MemberDAO memberdao = new MemberDAO();
		String  str_idx = req.getParameter("idx");
		MemberVO member = memberdao.getMemberByIdx(Integer.parseInt(str_idx));
		if(!member.isHumyun()){
			req.setAttribute("msg", "비활성화 상태가 아닙니다.");
			req.setAttribute("loc", "javascript:history.back()");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			int n = memberdao.enableMember(str_idx);
			if(n==1) {
				req.setAttribute("msg", "활성화 되었습니다.");
				req.setAttribute("loc", "memberList.do");
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
		}
	}
}

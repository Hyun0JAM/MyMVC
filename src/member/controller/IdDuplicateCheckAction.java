package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.controller.AbstractController;
import member.model.MemberDAO;

public class IdDuplicateCheckAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getMethod();
		req.setAttribute("method", method);
		if("POST".equalsIgnoreCase(method)) {
			MemberDAO memberdao = new MemberDAO();
			String userid = req.getParameter("userid");
			boolean idcheck = memberdao.idDuplicateCheck(userid);
			if(idcheck) {
				req.setAttribute("userid", userid);
				req.setAttribute("bool", "true");
				super.setViewPage("/WEB-INF/member/idcheck.jsp");
			}
			else {
				req.setAttribute("userid", userid);
				req.setAttribute("bool", "false");
				super.setViewPage("/WEB-INF/member/idcheck.jsp");
			}
		}
		else {
			super.setViewPage("/WEB-INF/member/idcheck.jsp");
		}
	}
}

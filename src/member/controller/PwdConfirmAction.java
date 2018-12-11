package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;

public class PwdConfirmAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getMethod();
		req.setAttribute("method", method);
		String userid = req.getParameter("userid");
		req.setAttribute("userid", userid);
		if("POST".equalsIgnoreCase(method)) {	
			String pwd = req.getParameter("pwd");
			MemberDAO memberdao = new MemberDAO();
			int n = memberdao.updatePwdUser(userid,pwd);
			req.setAttribute("n", n);
		}
		super.setRedirect(false);// false여야만 url주소가 바뀌지 않는다.
		super.setViewPage("/WEB-INF/login/pwdConfirm.jsp");
	}

}

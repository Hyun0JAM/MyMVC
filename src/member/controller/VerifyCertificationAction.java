package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

public class VerifyCertificationAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String certificationcode = (String)session.getAttribute("certificationcode");
		String userid = req.getParameter("userid");
		String userCertificationCode = req.getParameter("userCertificationCode");
		
		String msg = "";
		String loc = "";
		System.out.println("userCertificationCode : "+userCertificationCode);
		System.out.println("certificationcode : "+certificationcode);
		if(certificationcode.equals(userCertificationCode)) {
			msg = "인증 성공 되었습니다.";
			loc = req.getContextPath()+"/pwdConfirm.do?userid="+userid;
		}
		else {
			msg = "인증에 실패 하셨습니다.";
			loc = req.getContextPath()+"/pwdFind.do";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		super.setViewPage("/WEB-INF/msg.jsp");
		session.removeAttribute("certificationcode");
	}
}

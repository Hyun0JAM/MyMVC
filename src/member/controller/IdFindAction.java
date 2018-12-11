package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;

public class IdFindAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) 
		throws Exception {
		
		String method = req.getMethod();
		// GET 또는 POST
		
		if("POST".equalsIgnoreCase(method)) {
			// 아이디 찾기 모달창에서 찾기 버튼을 클릭했을 경우 
			
			String name = req.getParameter("name");
			String mobile = req.getParameter("mobile");
			
			req.setAttribute("name", name);
			req.setAttribute("mobile", mobile);
			
			MemberDAO memberdao = new MemberDAO();
			
			String userid = memberdao.getUserid(name, mobile);
			
			if(userid != null) {
				req.setAttribute("userid", userid);
			}
			else {
				req.setAttribute("userid", "존재하지 않습니다.");
			}
		}
		
		req.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/idFind.jsp");
		
	}// end of void execute(HttpServletRequest req, HttpServletResponse res)--------------- 

}

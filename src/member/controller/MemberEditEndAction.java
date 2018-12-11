package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;

public class MemberEditEndAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO memberdao = new MemberDAO();
		String idx = req.getParameter("idx");
		String name = req.getParameter("name");
		String pwd = req.getParameter("pwd");
		String email = req.getParameter("email");
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");
		String post1 = req.getParameter("post1");
		String post2 = req.getParameter("post2");
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2");
		int n = memberdao.changeMemberInfo(Integer.parseInt(idx),name,pwd,email,hp1,hp2,hp3,post1,post2,addr1,addr2);
		if(n==1) {
			req.setAttribute("msg", "정보가 수정 되었습니다.");
			req.setAttribute("loc", "javascript:self.close();opener.location.reload();");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}

package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.ProductDAO;

public class MemberInfoAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO loginuser = super.getLoginUser(req);
		if(!"admin".equals(loginuser.getUserid())) {
			req.setAttribute("msg", "관리자만 접근 가능합니다.");
			req.setAttribute("loc", "index.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		ProductDAO productdao = new ProductDAO();
		String odrcode = req.getParameter("odrcode");
		MemberVO member = productdao.getOrderMemberOne(odrcode);
		req.setAttribute("member", member);
		super.setViewPage("/WEB-INF/myshop/memberInfo.jsp");
	}
}
